package cu.academy.authentication.dto;


import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.shared.utils.Check;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    @NotBlank(message = "El nombre de usuario no debe estar vacío.")
    @Size(min = 2, max = 30, message = "El nombre de usuario debe tener de 2 a 30 caracteres.")
    @NotNull(message = "El nombre de usuario es un campo requerido.")
    private String username;
    @NotBlank(message = "La contraseña no debe estar vacía.")
    @Size(min = 2, max = 30, message = "La contraseña debe tener de 2 a 30 caracteres.")
    @NotNull(message = "La contraseña es un campo requerido.")
    private String password;
    private String tipoPersona;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws ArgumentException {
        this.password = Check.argumentNotNull(password, Translator.toLocale(TranslatorCode.NO_CONTRASENNA));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws ArgumentException {
        this.username = Check.argumentNotNull(username, Translator.toLocale(TranslatorCode.NO_NOMBRE_DE_USUARIO));
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }
}
