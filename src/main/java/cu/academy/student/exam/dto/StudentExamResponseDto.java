package cu.academy.student.exam.dto;


import cu.academy.shared.enum_types.EnumExamStatus;

public record StudentExamResponseDto(
                                     EnumExamStatus examStatus,
                                     EnumExamStatus courseStatus) {
}