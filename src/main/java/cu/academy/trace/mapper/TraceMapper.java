package cu.academy.trace.mapper;

import cu.academy.authentication.dto.UserResponseDto;
import cu.academy.person.PersonEntity;
import cu.academy.trace.TraceEntity;
import cu.academy.trace.dto.TraceCreationDto;
import cu.academy.trace.dto.TraceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TraceMapper {
    TraceDto toDto(TraceEntity entity);
    TraceEntity toEntity(TraceCreationDto dto);

    TraceCreationDto fromUserResponseDto(UserResponseDto dto);
    TraceCreationDto fromUserPerson(PersonEntity dto);
}
