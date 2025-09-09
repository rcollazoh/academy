package cu.academy.shared.enum_types;

public enum EnumImagenType {
    PERSON, DOCUMENT,GASTOS,OPERATIONS, PAYMENT;


    @Override
    public String toString() {
        switch (this) {
            case PERSON:
                return "person";
            case DOCUMENT:
                return "documents";
            case GASTOS:
                return "gastos";
            case OPERATIONS:
                return "operaciones";

            default: throw new IllegalArgumentException();
        }
    }

}
