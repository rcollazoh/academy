package cu.academy.student.classes.mapper;

import cu.academy.student.classes.StudentClassEntity;
import cu.academy.student.classes.dto.StudentClassDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentClassMapper {
    StudentClassEntity toEntity(StudentClassDto dto);
    StudentClassDto toDto(StudentClassEntity course);
}
