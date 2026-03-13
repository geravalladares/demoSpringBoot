package demospringboot.app.web.model;

import demospringboot.app.domain.entity.Customer;
import demospringboot.util.Sex;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.function.Function;

import io.swagger.annotations.ApiModel;

@Getter
@ApiModel(description = "Customer model")
public class CustomerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Customer ID", required = true, example = "1")
    private long id;

    @ApiModelProperty(value = "Customer name", required = true, example = "John Doe")
    private String name;

    @ApiModelProperty(value = "Sex of the customer", required = true, example= "M")
    private Sex sex;

    @ApiModelProperty(value = "Birth date of the customer", required = true, example = "1990-01-01")
    private LocalDate birthDate;

    @ApiModelProperty(value = "Email address of the customer", required = false, example = "mail@mail.com")
    private String email;

    @ApiModelProperty(value = "Phone number of the customer", required = false, example = "+1234567890")
    private String phoneNumber;

    @ApiModelProperty(value = "Creation date of the customer record", required = true, example = "2024-01-01T12:00:00")
    private LocalDateTime createdAt;

    public static final Function<Customer, CustomerModel> FN_ENTITY_TO_MODEL = entity -> Objects.isNull(entity) ? null : new CustomerModel(entity);

    private CustomerModel(Customer entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.sex = entity.getSex();
        this.birthDate = entity.getBirthDate();
        this.email = entity.getEmail();
        this.phoneNumber = entity.getPhoneNumber();
        this.createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(entity.getCreatedAt()), ZoneId.systemDefault());
    }

    // TODO: Ocultar Datos sensibles
    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "sex'=" + sex +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +    //obfuscate phone number for security reasons
                ", createdAt=" + createdAt +
                '}';
    }

}
