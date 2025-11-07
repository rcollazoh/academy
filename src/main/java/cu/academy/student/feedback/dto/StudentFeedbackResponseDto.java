package cu.academy.student.feedback.dto;

import cu.academy.shared.enum_types.EnumExamStatus;

public record StudentFeedbackResponseDto(
        EnumExamStatus courseStatus) {
}