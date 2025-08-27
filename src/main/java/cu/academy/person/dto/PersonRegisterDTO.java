package cu.academy.person.dto;

public record PersonRegisterDTO (
     Long id,
     String name,
     String lastName,
     String email,
     String phone,
    Boolean isUser,
    Boolean isClient,
    String id_number,
    Integer areaId,
    Integer practiceId,
    String password
){}
