package cu.academy.config.exam.question.mapper;



import cu.academy.config.exam.question.dto.ExamQuestionNavigationDto;
import cu.academy.config.exam.question.dto.ExamQuestionNavigationView;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConfigQuestionMapper {
    ExamQuestionNavigationDto toDto(ExamQuestionNavigationView view);
}
