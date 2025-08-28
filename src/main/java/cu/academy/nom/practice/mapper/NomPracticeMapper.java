package cu.academy.nom.practice.mapper;

import cu.academy.nom.area.NomAreaEntity;
import cu.academy.nom.area.dto.NomAreaDto;
import cu.academy.nom.practice.NomPracticeEntity;
import cu.academy.nom.practice.dto.NomPracticeDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NomPracticeMapper {

    NomPracticeEntity toEntity(NomPracticeDto dto);
    NomPracticeDto toDto(NomPracticeEntity person);
}
