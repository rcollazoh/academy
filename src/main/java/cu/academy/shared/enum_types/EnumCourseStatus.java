package cu.academy.shared.enum_types;

public enum EnumCourseStatus {
    NEW("Nuevo"),
    PENDING("Pendiente"),
    ACTIVATED("Activado"),
    REJECTED("Rechazado"),
    APPROVED("Aprobado"),
    NOT_APPROVED("No aprobado");

    private final String description;

    EnumCourseStatus(String descripcion) {
        this.description = descripcion;
    }

    public String getDescription() {
        return description;
    }

    public static String translate(String status) {
        if (status == null) return null;
        return switch (status.toUpperCase()) {
            case "NEW" -> NEW.description;
            case "PENDING" -> PENDING.description;
            case "ACTIVATED" -> ACTIVATED.description;
            case "REJECTED" -> REJECTED.description;
            case "APPROVED" -> APPROVED.description;
            case "NOT_APPROVED" -> NOT_APPROVED.description;
            default -> status; // fallback si no coincide
        };
    }
}
