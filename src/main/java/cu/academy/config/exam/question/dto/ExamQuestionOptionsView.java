package cu.academy.config.exam.question.dto;

import cu.academy.config.exam.option.ConfigOptionEntity;

import java.util.Set;

public interface ExamQuestionOptionsView {
    Integer getId();
    String getText();
    Integer getOrderNum();
    Set<ConfigOptionEntity> getConfigOptions();
}
