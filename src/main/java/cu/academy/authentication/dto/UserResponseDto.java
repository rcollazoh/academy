package cu.academy.authentication.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserResponseDto extends User {
    private Long id;
    private String surnames;
    private String email;
    private String mobilePhone;
//    private List<AccionDto> acciones;


    public UserResponseDto(String username, String password, Collection<? extends GrantedAuthority> authorities,
                           Long id, String surnames, String email,
                           String mobilePhone
//            , Boolean esVip, Boolean habilitado, EnumChoferEstado estado, Double geoLatitud, Double geoLongitud
    ) {
        super(username, password, authorities);
        this.id = id;
        this.surnames = surnames;
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


    public void setSurnames(String surnames) {
        this.surnames = surnames;
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

    public String getSurnames() {
        return surnames;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

}
