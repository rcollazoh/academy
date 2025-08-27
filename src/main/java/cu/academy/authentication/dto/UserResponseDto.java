package cu.academy.authentication.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserResponseDto extends User {
    private Long id;
    private String nombreUsuario;
    private String nombreApellidos;
    private String email;
    private String mobilePhone;
//    private List<AccionDto> acciones;


    public UserResponseDto(String username, String password, Collection<? extends GrantedAuthority> authorities,
                           Long id, String nombreUsuario, String nombreApellidos, String email,
                           String mobilePhone
//            , Boolean esVip, Boolean habilitado, EnumChoferEstado estado, Double geoLatitud, Double geoLongitud
    ) {
        super(username, password, authorities);
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombreApellidos = nombreApellidos;
        this.email = email;
        this.mobilePhone = mobilePhone;
    }
//
//    public List<AccionDto> getAcciones() {
//        return acciones;
//    }
//
//    public void setAcciones(List<AccionDto> acciones) {
//        this.acciones = acciones;
//    }


    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombreApellidos() {
        return nombreApellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

}
