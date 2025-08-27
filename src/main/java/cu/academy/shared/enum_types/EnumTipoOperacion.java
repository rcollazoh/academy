package cu.academy.shared.enum_types;

public enum EnumTipoOperacion {
    DEUDA("deuda"),
    DEPOSITO("depósito"),
    EXTRACCION("extracción"),
    TRANF_FONDO("transferencia de fondo"),
    TRANSFERENCIA("transferencia"),
    PAGO_EFECTIVO("pago en efectivo"),
    BONIFICACION("bonificación"),
    DESCUENTO_BONIFIC("descuento de bonificación"),
    REINTEGRO_DEUDA("reintegro de deuda");

    private final String operationText;

    EnumTipoOperacion(String operationText) {
        this.operationText = operationText;
    }

    public String getOperationText() {
        return operationText;
    }
}
