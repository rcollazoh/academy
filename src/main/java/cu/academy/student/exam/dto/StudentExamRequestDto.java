package cu.academy.student.exam.dto;


import cu.academy.shared.enum_types.EnumExamStatus;

public record StudentExamRequestDto(Long questionId,
                                    Long optionId,
                                    boolean isCorrect
) {
}