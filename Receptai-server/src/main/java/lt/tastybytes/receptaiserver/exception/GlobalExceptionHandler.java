package lt.tastybytes.receptaiserver.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lt.tastybytes.receptaiserver.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto("User with specified details already exists"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({EntryAlreadyExistsException.class, RuntimeValidationException.class})
    public ResponseEntity<ErrorResponseDto> handleCustomExceptions(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MissingRightsException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingRightsException(MissingRightsException ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HandlerMethodValidationException.class)
    protected ResponseEntity<ErrorResponseDto> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        ex.printStackTrace();
        var builder = new StringBuilder();
        for (var result: ex.getAllValidationResults()) {
            for (var e: result.getResolvableErrors()) {
                builder.append(e.getDefaultMessage()).append("\n");
            }
        }
        return new ResponseEntity<>(new ErrorResponseDto(builder.toString().strip()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var builder = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String message = error.getDefaultMessage();
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                builder.append('"').append(fieldName).append('"').append(" field ").append(message).append("\n");
            } else {
                builder.append(message).append("\n");
            }
        });
        return new ResponseEntity<>(new ErrorResponseDto(builder.toString().strip()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex,
            WebRequest request
    ) {
        ex.printStackTrace();
        //HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingHandler", WebRequest.SCOPE_REQUEST);
        //System.out.println(ex.getHttpInputMessage());
        return new ResponseEntity<>(new ErrorResponseDto(ex.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ErrorResponseDto> handleExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>(new ErrorResponseDto("Authentication token expired"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponseDto> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<ErrorResponseDto> handleGenericJWTException(JwtException ex) {
        System.out.println("Unhandled JWT exception");
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto("Invalid authentication token"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        System.out.println("Global error handler called");
        ex.printStackTrace();
        return new ResponseEntity<>(
                new ErrorResponseDto("An unhandled exception has occurred, if you're a developer, check the logs"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}