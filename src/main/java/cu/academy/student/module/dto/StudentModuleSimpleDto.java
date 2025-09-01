package cu.academy.student.module.dto;


import java.time.LocalDate;
public record StudentModuleSimpleDto(Long id,
                                     String status,
                                     LocalDate fechaExam,
                                     Integer intentos
) {}