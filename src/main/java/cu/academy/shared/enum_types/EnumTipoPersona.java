package cu.academy.shared.enum_types;

public enum EnumTipoPersona {
    USER, CLIENT;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toUpperCase();
    }
}
