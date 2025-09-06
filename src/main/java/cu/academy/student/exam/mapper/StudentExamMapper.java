package cu.academy.student.exam.mapper;

import cu.academy.student.exam.StudentExamEntity;
import cu.academy.student.exam.dto.StudentExamDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentExamMapper {
    StudentExamEntity toEntity(StudentExamDto dto);
    StudentExamDto toDto(StudentExamEntity course);
}
