package cu.academy.shared.enum_types;


import jakarta.validation.constraints.NotNull;

public enum EnumCategoria {
    PRECIO, AYUDANTE, REFUERZO, SEGUNDA_OPERADORA, JEFA_TURNO, OPERADORA_PCPAL, FONDO, ADMINISTRATIVO, ADMINISTRADOR, INVITADO, INFORMATICO,VISITA,HORARIO,SUPER_ADMIN,DIR_ADMINISTRATIVO,DIR_ADMINISTRADOR ;

    public static boolean noNecesitaChequeoTurnoParaLogin(@NotNull EnumCategoria enumCategoria) {
        return enumCategoria.equals(EnumCategoria.FONDO) ||
                enumCategoria.equals(EnumCategoria.ADMINISTRATIVO) ||
                enumCategoria.equals(EnumCategoria.ADMINISTRADOR) ||
                enumCategoria.equals(EnumCategoria.SUPER_ADMIN) ||
                enumCategoria.equals(EnumCategoria.DIR_ADMINISTRATIVO) ||
                enumCategoria.equals(EnumCategoria.DIR_ADMINISTRADOR) ||
                enumCategoria.equals(EnumCategoria.INFORMATICO) ||
                enumCategoria.equals(EnumCategoria.HORARIO);
    }

}
