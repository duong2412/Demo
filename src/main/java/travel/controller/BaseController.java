package travel.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import travel.exception.ValidateException;
import travel.model.response.DataResponse;
import travel.util.ErrorCode;

@RestControllerAdvice
public class BaseController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String code = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.getMessage(code);

        DataResponse errResponse = new DataResponse();
        errResponse.setCode(code);
        errResponse.setMessage(errorCode.message());

        return new ResponseEntity<>(errResponse, errorCode.status());
    }

    @ExceptionHandler(ValidateException.class)
    protected ResponseEntity<Object> handleValidateException(ValidateException ex) {
        ErrorCode errorCode = ErrorCode.getMessage(ex.getMessage());

        DataResponse errResponse = new DataResponse();
        errResponse.setCode(errorCode.code());
        errResponse.setMessage(errorCode.message());

        return new ResponseEntity<>(errResponse, errorCode.status());
    }

}
