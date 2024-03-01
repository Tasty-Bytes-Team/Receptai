package lt.tastybytes.receptaiserver.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lt.tastybytes.receptaiserver.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto("User with specified details already exists"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var builder = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            builder.append('"' + fieldName + '"' +  " field " + message + "\n");
        });
        return new ResponseEntity<>(new ErrorResponseDto(builder.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex,
            WebRequest request
    ) {
        ex.printStackTrace();
        //HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingHandler", WebRequest.SCOPE_REQUEST);
        //System.out.println(ex.getHttpInputMessage());
        return new ResponseEntity<>(new ErrorResponseDto(ex.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>(new ErrorResponseDto("Authentication token expired"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<Object> handleGenericJWTException(JwtException ex) {
        System.out.println("Unhandled JWT exception");
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto("Invalid authentication token"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        System.out.println("Global error handler called");
        ex.printStackTrace();
        return new ResponseEntity<>(
                new ErrorResponseDto("An unhandled exception has occurred, if you're a developer, check the logs"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}