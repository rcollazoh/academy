package cu.academy.config.module.mapper;

import cu.academy.config.course.ConfigCourseEntity;
import cu.academy.config.course.dto.ConfigCourseDto;
import cu.academy.config.module.ConfigModuleEntity;
import cu.academy.config.module.dto.ConfigModuleDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConfigModuleMapper {
    ConfigModuleEntity toEntity(ConfigModuleDto dto);
    ConfigModuleDto toDto(ConfigModuleEntity course);
}
