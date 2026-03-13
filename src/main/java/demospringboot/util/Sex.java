package demospringboot.util;

public enum Sex {

    M("Masculino"),
    F("Femenino"),
    O("Otro");

    private String description;
    private Sex(String description) {this.description = description;}
    public String description() {return this.description;}
}
