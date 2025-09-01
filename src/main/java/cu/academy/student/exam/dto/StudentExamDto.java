package cu.academy.student.exam.dto;


public record StudentExamDto(Long id,
                             Boolean viewed,
                             String status,
                             Long configExamId,
                             String title,
                             String recourseUrl) {
}