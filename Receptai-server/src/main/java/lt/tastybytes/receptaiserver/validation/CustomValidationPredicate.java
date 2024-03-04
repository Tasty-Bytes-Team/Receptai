package lt.tastybytes.receptaiserver.validation;

public interface CustomValidationPredicate<T> {
    boolean validate(T value);
}
