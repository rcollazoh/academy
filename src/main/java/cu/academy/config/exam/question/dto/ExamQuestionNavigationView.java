package cu.academy.config.exam.question.dto;

public interface ExamQuestionNavigationView {
    Long getCurrentId();
    String getTitle();
    Integer getOrderNum();
    Long getPreviousId();
    Long getNextId();
}
