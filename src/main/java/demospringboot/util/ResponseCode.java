package demospringboot.util;

public enum ResponseCode {

    AT_TS_001("Cliente registrado exitosamente"),
    AT_TS_002("Cliente consultado exitosamente"),
    AT_TS_003("Cliente no existente"),
    AT_TS_004("El Cliente ya existe"),
    AT_TS_005("El Cliente debe ser mayor de edad"),

    AT_TS_101("Usuario consultado exitosamente"),
    AT_TS_102("Usuario no existente"),

    AT_TS_201("Dato invalido"),
    AT_TS_202("Daro requerido"),

    AT_TS_999("Error no controlado");

    private String message;
    ResponseCode(String message) {this.message = message;}
    public String message() {return message;}

}
