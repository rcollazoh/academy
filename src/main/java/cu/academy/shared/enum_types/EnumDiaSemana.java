package cu.academy.shared.enum_types;

import java.time.DayOfWeek;

public enum EnumDiaSemana {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO;

    public static EnumDiaSemana getDayOfWeekInSpanish(DayOfWeek diaSemEnglish) {
        switch (diaSemEnglish) {
            case MONDAY:
                return LUNES;
            case TUESDAY:
                return MARTES;
            case WEDNESDAY:
                return MIERCOLES;
            case THURSDAY:
                return JUEVES;
            case FRIDAY:
                return VIERNES;
            case SATURDAY:
                return SABADO;
            case SUNDAY:
                return DOMINGO;
            default:
                return null;
        }
    }
}
