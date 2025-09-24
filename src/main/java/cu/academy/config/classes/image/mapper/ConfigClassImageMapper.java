package cu.academy.config.classes.image.mapper;


import cu.academy.config.classes.image.dto.ClassImageNavigationDto;
import cu.academy.config.classes.image.dto.ClassImageNavigationView;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConfigClassImageMapper {
    ClassImageNavigationDto toDto(ClassImageNavigationView view);
}
