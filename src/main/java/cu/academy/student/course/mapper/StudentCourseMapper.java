package cu.academy.student.course.mapper;

import cu.academy.student.course.StudentCourseEntity;
import cu.academy.student.course.dto.StudentCourseDto;
import cu.academy.student.course.dto.StudentCourseForMyselfDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentCourseMapper {
    StudentCourseEntity toEntity(StudentCourseDto dto);
    @Mapping(source = "person.name", target = "personName")
    @Mapping(source = "person.lastName", target = "personLastName")
    @Mapping(source = "person.email", target = "personEmail")
    @Mapping(target = "status", expression = "java(EnumCourseStatus.translate(course.getStatus().name()))")
    StudentCourseDto toDto(StudentCourseEntity course);

    @Mapping(target = "status", expression = "java(EnumCourseStatus.translate(course.getStatus().name()))")
    @Mapping(source = "course.name", target = "configCourseName")
    StudentCourseForMyselfDto toDtoMyself(StudentCourseEntity course);
}
