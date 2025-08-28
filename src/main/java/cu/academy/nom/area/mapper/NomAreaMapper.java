package cu.academy.nom.area.mapper;

import cu.academy.nom.area.NomAreaEntity;
import cu.academy.nom.area.dto.NomAreaDto;
import cu.academy.person.PersonEntity;
import cu.academy.person.dto.PersonInsertDto;
import cu.academy.person.dto.PersonProfileDto;
import cu.academy.person.dto.PersonRegisterDTO;
import cu.academy.person.dto.PersonUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NomAreaMapper {

    NomAreaEntity toEntity(NomAreaDto dto);
    NomAreaDto toDto(NomAreaEntity person);
}
