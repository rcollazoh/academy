package cu.academy.authentication.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LogoutRequest {
    private Long idUsuario;
    @Min(value = 1, message = "El campo identificador de la sesión debe ser un entero mayor que 0.")
    @NotNull(message = "El campo identificador de la sesión es un campo requerido.")
    private Integer idSesion;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Integer idSesion) {
        this.idSesion = idSesion;
    }
}
