package cu.academy.config.exam.question.dto;


import cu.academy.config.exam.option.dto.ConfigOptionDto;

import java.util.List;


public record ExamQuestionOptionsDto(
        Integer id,
        String text,
        Integer orderNum,
        List<ConfigOptionDto> configOptions
) {}


