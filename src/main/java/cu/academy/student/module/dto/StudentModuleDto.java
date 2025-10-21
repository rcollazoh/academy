package cu.academy.student.module.dto;


import cu.academy.shared.enum_types.EnumModuleStatus;
import cu.academy.student.classes.dto.StudentClassDto;
import cu.academy.student.exam.dto.StudentExamDto;

import java.util.List;

public record StudentModuleDto(Long id,
                               Long confingModuleId,
                               EnumModuleStatus status,
                               String moduleName,
                               Integer orderNum,
                               List<StudentClassDto> classes,
                               StudentExamDto exam

) {}