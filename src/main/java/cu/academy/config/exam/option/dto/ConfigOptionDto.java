package cu.academy.config.exam.option.dto;

public record ConfigOptionDto(
        Long id,
        String text,
        boolean isCorrect
) {}
