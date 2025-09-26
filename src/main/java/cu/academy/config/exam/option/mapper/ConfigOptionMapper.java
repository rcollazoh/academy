package cu.academy.config.exam.option.mapper;



import cu.academy.config.exam.option.ConfigOptionEntity;
import cu.academy.config.exam.option.dto.ConfigOptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConfigOptionMapper {
    ConfigOptionDto toDto(ConfigOptionEntity entity);
}
