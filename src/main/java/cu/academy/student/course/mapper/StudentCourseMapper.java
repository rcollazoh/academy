package cu.academy.student.course.mapper;

import cu.academy.student.course.StudentCourseEntity;
import cu.academy.student.course.dto.StudentCourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentCourseMapper {
    StudentCourseEntity toEntity(StudentCourseDto dto);
    @Mapping(source = "person.name", target = "personName")
    @Mapping(source = "person.lastName", target = "personLastName")
    @Mapping(source = "person.email", target = "personEmail")
    StudentCourseDto toDto(StudentCourseEntity course);
}
