package cu.academy.config.module.dto;

import java.math.BigDecimal;

public record ConfigModuleDto(Long id,
                              String name,
                              String description,
                              Integer orderNum) {
}
