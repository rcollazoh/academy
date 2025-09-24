package cu.academy.config.classes.image.dto;

public interface ClassImageNavigationView {
    Long getCurrentId();
    String getTitle();
    String getRecourseUrl();
    Integer getOrderNum();
    Long getPreviousId();
    Long getNextId();
}
