package cu.academy.config.classes.image.dto;

public record ClassImageNavigationDto(
        Long currentId,
        String title,
        String recourseUrl,
        Integer orderNum,
        Long previousId,
        Long nextId
) {}
