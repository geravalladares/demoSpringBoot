package demospringboot.app.web.model;

import demospringboot.app.domain.entity.Customer;
import demospringboot.configuration.EnumValidate;
import demospringboot.util.Sex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;

@Getter
@ApiModel(description = "Model for creating a new customer")
@AllArgsConstructor
public class CustomerCreateModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Customer name", required = true, example = "John Doe")
    @NotBlank(message = "Name is required")
    private String name;

    @ApiModelProperty(value = "ID for Sex of the customer", required = true, example = "M", allowableValues = "M, F, O")
    @EnumValidate(enumClass = Sex.class, message = "Invalid Sex value.")
    private String sex;

    @ApiModelProperty(value = "Birth date of the customer", required = true, example = "1990-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @ApiModelProperty(value = "Email address of the customer", required = true, example = "mail@mail.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @ApiModelProperty(value = "Phone number of the customer", required = true, example = "+1234567890")
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phoneNumber;

    public static final Function<CustomerCreateModel, Customer> FN_MODEL_TO_ENTITY = model -> Objects.isNull(model) ? null :
            new Customer(
                    model.getName(),
                    Sex.valueOf(model.getSex()),
                    model.getBirthDate(),
                    model.getEmail(),
                    model.getPhoneNumber()
            );

    @Override
    public String toString() {
        return "CustomerCreateModel{" +
                "name='" + name + '\'' +
                ", sex " + sex +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +    // obfuscateText
                '}';
    }

}
