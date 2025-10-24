package cu.academy.student.feedback.module.dto;


public record StudentFeedbackModuleDto(Long id,
                                       Long feedbackId,
                                       Long configModuleId,
                                       String evaluation,
                                       String question

) {}