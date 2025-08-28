package cu.academy.person.dto;

import java.time.LocalDate;

public record PersonUpdateDto(
        String name,
        String lastName,
        String email,
        String phone,
        String gender,
        LocalDate birthday,
        String status,
        String clientAttractionInfo,
        String workplace,
        String photo,
        String aboutMe,
        Boolean isUser,
        Boolean isClient,
        String idNumber,
        String password
) {}
