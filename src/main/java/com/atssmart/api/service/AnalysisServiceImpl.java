package com.atssmart.api.service;

import com.atssmart.api.dto.response.JobApplicationResponse;
import com.atssmart.api.exception.ResourceNotFoundException;
import com.atssmart.api.mapper.JobApplicationMapper;
import com.atssmart.api.model.JobApplicationEntity;
import com.atssmart.api.model.SkillEntity;
import com.atssmart.api.repository.JobApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for candidate application analysis.
 */
@Service
@AllArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {
    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper jobApplicationMapper;
    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public JobApplicationResponse analizeDifference(Long JobApplicationId) {
        JobApplicationEntity jobApplication = jobApplicationRepository.findById(JobApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Application", "id", JobApplicationId));

        Set<SkillEntity> jobOfferSkills = jobApplication.getJobOffer().getRequiredSkills();
        Set<SkillEntity> applicantSkills = jobApplication.getApplicant().getSkills();

        Set<SkillEntity> missing = new HashSet<>(jobOfferSkills);
        missing.removeAll(applicantSkills);
        jobApplication.setMissingSkills(missing);

        String prompt = buildPrompt(jobApplication, missing);
        try {
            String rawResponse = chatModel.call(prompt);
            AiResponse aiResponse = parseAiResponse(rawResponse);
            jobApplication.setMatchScore(aiResponse.matchScore());
            jobApplication.setAiFeedback(aiResponse.aiFeedback());
        } catch (Exception e) {
            jobApplication.setMatchScore(0);
            jobApplication.setAiFeedback("Error al realizar la evaluación por IA: " + e.getMessage());
        }

        return jobApplicationMapper.toResponse(jobApplicationRepository.save(jobApplication));
    }

    private String buildPrompt(JobApplicationEntity app, Set<SkillEntity> missingSkills) {
        String requiredSkillsStr = app.getJobOffer().getRequiredSkills().stream()
                .map(SkillEntity::getName)
                .collect(Collectors.joining(", "));
        String candidateSkillsStr = app.getApplicant().getSkills().stream()
                .map(SkillEntity::getName)
                .collect(Collectors.joining(", "));
        String missingSkillsStr = missingSkills.stream()
                .map(SkillEntity::getName)
                .collect(Collectors.joining(", "));
        
        String cvText = "No se ha subido un CV en formato PDF.";
        String cvPath = app.getCvLink();
        if (cvPath == null || cvPath.isEmpty()) {
            cvPath = app.getApplicant().getCvLink();
        }

        if (cvPath != null && cvPath.startsWith("uploads/cvs/")) {
            try {
                cvText = com.atssmart.api.util.PdfUtils.extractTextFromPdf(cvPath);
            } catch (Exception e) {
                cvText = "Error al leer el archivo PDF: " + e.getMessage();
            }
        }

        return """
                Eres un asistente de Inteligencia Artificial experto en reclutamiento y selección para ATS Smart.
                Analiza la compatibilidad del candidato para la oferta de empleo provista utilizando tanto sus habilidades de base de datos como el texto extraído de su currículum PDF.
                
                OFERTA DE EMPLEO:
                - Título: %s
                - Descripción: %s
                - Habilidades Requeridas: [%s]
                
                CANDIDATO:
                - Nombre: %s
                - Seniority: %s
                - Habilidades que posee (Base de datos): [%s]
                
                BRECHA DE HABILIDADES (Habilidades requeridas que el candidato no tiene asociadas en BD):
                - [%s]
                
                TEXTO EXTRAÍDO DEL CURRÍCULUM PDF DEL CANDIDATO:
                ---
                %s
                ---
                
                INSTRUCCIONES DE RESPUESTA Y VERIFICACIÓN DE IDENTIDAD:
                1. Compara el nombre del candidato indicado en la sección "CANDIDATO" (Nombre: %s) con el nombre que figura al inicio del texto extraído de su currículum PDF.
                2. Si los nombres son sustancialmente diferentes (por ejemplo, pertenecen a personas completamente distintas y no son variaciones del mismo nombre), debes incluir OBLIGATORIAMENTE al inicio del campo "aiFeedback" la siguiente advertencia exacta: "[ALERTA: DISCREPANCIA DE IDENTIDAD - El currículum pertenece a otra persona: <Nombre detectado en PDF>]". En este caso, penaliza drásticamente el "matchScore" asignándole un máximo de 10 puntos.
                3. Debes responder estrictamente con un objeto JSON válido que contenga exactamente estos dos campos:
                   - "matchScore": Un número entero del 0 al 100 que indique el porcentaje de compatibilidad de habilidades técnicas, seniority y experiencia deducida del currículum (aplicando la penalización si hay discrepancia de nombre).
                   - "aiFeedback": Un comentario breve en español (máximo 400 caracteres) explicando el motivo técnico del score asignado, fortalezas del candidato y lo que le falta (incluyendo la alerta al inicio si corresponde).
                
                No agregues formato markdown, no uses ```json, no incluyas introducciones ni explicaciones fuera del JSON. Envía únicamente el JSON puro.
                """.formatted(
                app.getJobOffer().getTitle(),
                app.getJobOffer().getDescription(),
                requiredSkillsStr.isEmpty() ? "Ninguna" : requiredSkillsStr,
                app.getApplicant().getFullName(),
                app.getApplicant().getSeniority() != null ? app.getApplicant().getSeniority().name() : "No especificado",
                candidateSkillsStr.isEmpty() ? "Ninguna" : candidateSkillsStr,
                missingSkillsStr.isEmpty() ? "Ninguna, posee todas las habilidades" : missingSkillsStr,
                cvText,
                app.getApplicant().getFullName()
        );
    }

    private AiResponse parseAiResponse(String rawResponse) throws Exception {
        String cleaned = rawResponse.trim();
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3);
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }
        cleaned = cleaned.trim();
        return objectMapper.readValue(cleaned, AiResponse.class);
    }

    private record AiResponse(Integer matchScore, String aiFeedback) {
    }
}

