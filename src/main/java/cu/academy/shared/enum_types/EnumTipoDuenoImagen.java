package cu.academy.shared.enum_types;

public enum EnumTipoDuenoImagen {
    PERSONA, DOCUMENTO, VIAJE,GASTOS,OPERACIONES,INCIDENCIAS,UTILES_HERRAMIENTAS;


    @Override
    public String toString() {
        switch (this) {
            case PERSONA:
                return "personas";
            case DOCUMENTO:
                return "documentos";
            case VIAJE:
                return "viajes";
            case GASTOS:
                return "gastos";
            case OPERACIONES:
                return "operaciones";
            case INCIDENCIAS:
                return "incidencias";
            case UTILES_HERRAMIENTAS:
                return "utiles_herramientas";

            default: throw new IllegalArgumentException();
        }
    }

}
