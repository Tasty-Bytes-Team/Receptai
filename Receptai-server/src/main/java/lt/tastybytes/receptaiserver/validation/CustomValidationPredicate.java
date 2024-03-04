package lt.tastybytes.receptaiserver.validation;

public interface CustomValidationPredicate<T> {
    String getErrorMessage();
    boolean validate(T value);
}
