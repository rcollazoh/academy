package cu.academy.person.dto;

public record PersonRegisterDTO(
        Long id,
        String name,
        String lastName,
        String email,
        String phone,
        String idNumber,
        Long areaId,
        Long practiceId,
        String password
) {
}
