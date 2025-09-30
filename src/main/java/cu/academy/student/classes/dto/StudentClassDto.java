package cu.academy.student.classes.dto;


public record StudentClassDto(Long id,
                              Boolean viewed,
                              Integer configClassId,
                              String title,
                              String type) {
}