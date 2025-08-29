package cu.academy.config.course.mapper;

import cu.academy.config.course.ConfigCourseEntity;
import cu.academy.config.course.dto.ConfigCourseDto;
import cu.academy.nom.area.NomAreaEntity;
import cu.academy.nom.area.dto.NomAreaDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConfigCourseMapper {

    ConfigCourseEntity toEntity(ConfigCourseDto dto);
    ConfigCourseDto toDto(ConfigCourseEntity course);
}
