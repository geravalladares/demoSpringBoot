package demospringboot.remoto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("numero")
    private String number;

    @JsonProperty("clave")
    private String username;

    private String status;

    @JsonProperty("activo")
    private String active;

    @JsonProperty("eMail")
    private String email;

    @JsonProperty("nombre")
    private String name;

}
