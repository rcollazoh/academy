package cu.academy.student.exam.dto;


import cu.academy.shared.enum_types.EnumExamStatus;

public record StudentExamDto(Long id,
                             EnumExamStatus status,
                             Long configExamId,
                             String title,
                             String recourseUrl) {
}