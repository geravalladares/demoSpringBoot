package demospringboot.configuration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class EnumValidator implements ConstraintValidator<EnumValidate, String> {

    private List<String> enumValueList = null;

    @Override
    public void initialize(EnumValidate constraintAnnotation) {
        enumValueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        Enum[] emumValues = enumClass.getEnumConstants();
        for (Enum enumVal : emumValues){
            enumValueList.add(enumVal.toString().toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return  value == null || enumValueList.contains(value.toUpperCase());
    }

}
