package cu.academy.student.course.mapper;

import cu.academy.student.course.StudentCourseEntity;
import cu.academy.student.course.dto.StudentCourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentCourseMapper {
    StudentCourseEntity toEntity(StudentCourseDto dto);
    StudentCourseDto toDto(StudentCourseEntity course);
}
