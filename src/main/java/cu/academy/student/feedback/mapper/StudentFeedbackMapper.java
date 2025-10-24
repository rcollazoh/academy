package cu.academy.student.feedback.mapper;

import cu.academy.student.feedback.StudentFeedbackEntity;
import cu.academy.student.feedback.dto.StudentFeedbackDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentFeedbackMapper {
    StudentFeedbackEntity toEntity(StudentFeedbackDto dto);
    StudentFeedbackDto toDto(StudentFeedbackEntity entity);
}
