package cu.academy.student.feedback.module.mapper;

import cu.academy.student.feedback.module.StudentFeedbackModuleEntity;
import cu.academy.student.feedback.module.dto.StudentFeedbackModuleDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentFeedbackModuleMapper {
    StudentFeedbackModuleEntity toEntity(StudentFeedbackModuleDto dto);
    StudentFeedbackModuleDto toDto(StudentFeedbackModuleEntity entity);
}
