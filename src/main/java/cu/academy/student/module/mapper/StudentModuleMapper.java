package cu.academy.student.module.mapper;

import cu.academy.student.module.StudentModuleEntity;
import cu.academy.student.module.dto.StudentModuleSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentModuleMapper {
    StudentModuleEntity toEntity(StudentModuleSimpleDto dto);
    StudentModuleSimpleDto toDto(StudentModuleEntity course);
}
