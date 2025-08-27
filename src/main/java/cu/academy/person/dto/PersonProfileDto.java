package cu.academy.person.dto;

import java.time.LocalDate;

public record PersonProfileDto(Long id,
                               String name,
                               String lastName,
                               String email,
                               String phone,
                               Boolean isUser,
                               Boolean isClient,
                               String status,
                               LocalDate birthday,
                               String workplace,
                               String photo) {
}
