package cu.academy.person.mapper;

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
public interface PersonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModify", ignore = true)
    @Mapping(target = "accountOperations", ignore = true)
    PersonEntity toEntity(PersonInsertDto dto);
    @Mapping(target = "idNumber", source = "idNumber")
    PersonEntity toEntity(PersonRegisterDTO dto);
    @Mapping(target = "idNumber", source = "idNumber")
    @Mapping(target = "areaId", source = "area.id")
    @Mapping(target = "practiceId", source = "practice.id")
    PersonProfileDto toProfileDto(PersonEntity person);
    @Mapping(target = "idNumber", source = "idNumber")
    void updateEntity(PersonUpdateDto dto, @MappingTarget PersonEntity entity);
}
