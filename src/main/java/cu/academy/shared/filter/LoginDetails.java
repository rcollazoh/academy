package cu.academy.shared.filter;


import cu.academy.shared.enum_types.EnumTipoPersona;

public class LoginDetails {
    EnumTipoPersona personType;
    String token;
    String idCliente;

    public LoginDetails(EnumTipoPersona tipoPersona,
                        String token, String idCliente) {
        this.personType = tipoPersona;
        this.token = token;
        this.idCliente = idCliente;
    }
    public EnumTipoPersona getPersonType() {
        return personType;
    }
    public void setPersonType(EnumTipoPersona personType) {
        this.personType = personType;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
