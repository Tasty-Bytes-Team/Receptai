package lt.tastybytes.receptaiserver.validation;

public class TestValidator implements CustomValidationPredicate<Integer> {
    @Override
    public String getErrorMessage() {
        return "Some test validator error message";
    }

    @Override
    public boolean validate(Integer value) {
        return true;
    }
}
