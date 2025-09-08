package cu.academy.authentication.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserResponseDto extends User {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String mobilePhone;
//    private List<AccionDto> acciones;


    public UserResponseDto(String username, String password, Collection<? extends GrantedAuthority> authorities,
                           Long id, String name, String lastName, String email,
                           String mobilePhone
//            , Boolean esVip, Boolean habilitado, EnumChoferEstado estado, Double geoLatitud, Double geoLongitud
    ) {
        super(username, password, authorities);
        this.id = id;
        this.name = name;
        this.lastName = lastName;
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


    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

}
