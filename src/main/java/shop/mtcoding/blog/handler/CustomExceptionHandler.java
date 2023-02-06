package shop.mtcoding.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.util.Script;

@RestControllerAdvice
public class CustomExceptionHandler {

    // NullPointException <- RuntimeException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> customException(CustomException e) {
        String responseBody = Script.back(e.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
