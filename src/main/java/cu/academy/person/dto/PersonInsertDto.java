package cu.academy.person.dto;

import java.time.LocalDate;

public record PersonInsertDto(String name,
                              String lastName,
                              String email,
                              String phone,
                              String password,
                              Boolean isUser,
                              Boolean isClient,
                              String gender,
                              LocalDate birthday,
                              String status,
                              String photo,
                              String aboutMe,
                              String id_number,
                              Integer areaId,
                              Integer practiceId
                              ) {
}
