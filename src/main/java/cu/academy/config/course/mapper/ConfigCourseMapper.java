package cu.academy.config.course.mapper;

import cu.academy.config.course.ConfigCourseEntity;
import cu.academy.config.course.dto.ConfigCourseDto;
import cu.academy.nom.area.NomAreaEntity;
import cu.academy.nom.area.dto.NomAreaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConfigCourseMapper {

    @Mapping(source = "area.name", target = "area")
    @Mapping(source = "practice.name", target = "practice")
    ConfigCourseDto toDto(ConfigCourseEntity course);
}
