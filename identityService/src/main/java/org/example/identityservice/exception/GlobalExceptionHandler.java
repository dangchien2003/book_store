package org.example.identityservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.dto.response.ApiResponse;
import org.example.identityservice.utils.ENumUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<Object>> handlingException(Exception e) {
        log.error("error: ", e);
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        return setResponse(errorCode);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Object>> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        return setResponse(errorCode);
    }


    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    ResponseEntity<ApiResponse<Object>> handlingHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return setResponse(ErrorCode.BODY_PARSE_FAIL);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ApiResponse<Object>> handlingHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return setResponse(ErrorCode.NOTFOUND_ENDPOINT);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    ResponseEntity<ApiResponse<Object>> handlingMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return setResponse(ErrorCode.INVALID_DATA);
    }

    @ExceptionHandler(value = HandlerMethodValidationException.class)
    ResponseEntity<ApiResponse<Object>> handlingHandlerMethodValidationException(HandlerMethodValidationException e) {
        try {
            ParameterValidationResult parameterValidationResult =
                    e.getAllValidationResults().get(0); // get first ParameterValidationResult

            String message = parameterValidationResult
                    .getResolvableErrors().get(0)
                    .getDefaultMessage(); // get first message

            ErrorCode errorCode = ENumUtils.getType(ErrorCode.class, message);
            return setResponse(errorCode);
        } catch (AppException appException) {
            return setResponse(ErrorCode.INVALID_DATA);
        }
    }


    @ExceptionHandler(value = NoResourceFoundException.class)
    ResponseEntity<ApiResponse<Object>> handlingNoResourceFoundException(NoResourceFoundException e) {
        return setResponse(ErrorCode.NOTFOUND_ENDPOINT);
    }


    @ExceptionHandler(value = AuthorizationDeniedException.class)
    ResponseEntity<ApiResponse<Object>> handlingAuthorizationDeniedException(AuthorizationDeniedException e) {
        return setResponse(ErrorCode.NO_ACCESS);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String firstErrorMessage = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(ErrorCode.CUSTOM_MESSAGE.getMessage());

        try {
            ErrorCode errorCode = ENumUtils.getType(ErrorCode.class, firstErrorMessage);

            if (ErrorCode.DATA_BLANK == errorCode) {
                List<String> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                        .filter(fieldError -> Objects.equals(fieldError.getDefaultMessage(), firstErrorMessage))
                        .map(FieldError::getField)
                        .toList();

                return ResponseEntity
                        .status(ErrorCode.DATA_BLANK.getHttpStatusCode())
                        .body(ApiResponse.builder()
                                .code(ErrorCode.DATA_BLANK.getCode())
                                .message(ErrorCode.DATA_BLANK.getMessage() + String.join(", ", fieldErrors))
                                .build());
            }

            return setResponse(errorCode);
        } catch (AppException appException) {
            return ResponseEntity.
                    status(ErrorCode.CUSTOM_MESSAGE.getHttpStatusCode())
                    .body(ApiResponse.builder()
                            .code(ErrorCode.CUSTOM_MESSAGE.getCode())
                            .message(firstErrorMessage)
                            .build());
        }
    }

    ResponseEntity<ApiResponse<Object>> setResponse(ErrorCode errorCode) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();
        return ResponseEntity.
                status(errorCode.getHttpStatusCode())
                .body(apiResponse);
    }
}
