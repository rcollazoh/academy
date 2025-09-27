package cu.academy.config.exam.question.mapper;

import cu.academy.config.exam.question.ConfigQuestionEntity;
import cu.academy.config.exam.question.dto.ExamQuestionNavigationDto;
import cu.academy.config.exam.question.dto.ExamQuestionNavigationView;
import cu.academy.config.exam.question.dto.ExamQuestionOptionsDto;
import cu.academy.config.exam.question.dto.ExamQuestionOptionsView;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConfigQuestionMapper {
    ExamQuestionNavigationDto toDto(ExamQuestionNavigationView view);

    List<ExamQuestionOptionsDto> toDtoListView(List<ExamQuestionOptionsView> views);

    List<ExamQuestionOptionsDto> toDtoList(List<ConfigQuestionEntity> entities);

}
