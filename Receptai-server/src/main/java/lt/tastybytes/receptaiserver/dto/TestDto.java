package lt.tastybytes.receptaiserver.dto;

import lt.tastybytes.receptaiserver.validation.CustomValidation;
import lt.tastybytes.receptaiserver.validation.TestValidator;

import java.util.List;

public record TestDto(
        @CustomValidation(value = TestValidator.class)
        List<Integer> list
) { }
