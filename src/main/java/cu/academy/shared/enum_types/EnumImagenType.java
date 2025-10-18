package cu.academy.shared.enum_types;

public enum EnumImagenType {
    PERSON, CERTIFY,GASTOS,OPERATIONS, PAYMENT;


    @Override
    public String toString() {
        switch (this) {
            case PERSON:
                return "person";
            case CERTIFY:
                return "certify";
            case GASTOS:
                return "gastos";
            case OPERATIONS:
                return "operaciones";

            default: throw new IllegalArgumentException();
        }
    }

}
