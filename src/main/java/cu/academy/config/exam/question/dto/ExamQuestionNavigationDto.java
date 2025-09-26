package cu.academy.config.exam.question.dto;

import cu.academy.config.exam.option.dto.ConfigOptionDto;
import cu.academy.trace.dto.TraceCreationDto;

import java.util.List;

public record ExamQuestionNavigationDto(
        Long currentId,
        String title,
        Integer orderNum,
        Long previousId,
        Long nextId,
        List<ConfigOptionDto> optionDto
) {

    public ExamQuestionNavigationDto withOptionDto(List<ConfigOptionDto> paramOptionDto) {
        return new ExamQuestionNavigationDto(
                currentId, title, orderNum, previousId, nextId, paramOptionDto
        );
    }

}
