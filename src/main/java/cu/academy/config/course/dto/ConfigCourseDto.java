package cu.academy.config.course.dto;

import java.math.BigDecimal;

public record ConfigCourseDto(Long id,
                              String name,
                              String description,
                              Integer durationDays,
                              BigDecimal price) {
}
