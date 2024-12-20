package org.example.orderservice.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public enum ErrorCode {
    INVALID_DATA(1001, "Invalid data, check again", HttpStatus.BAD_REQUEST),
    INVALID_NAME(1002, "Invalid field name", HttpStatus.BAD_REQUEST),
    DATA_BLANK(1003, "Field not blank: ", HttpStatus.BAD_REQUEST),
    NOTFOUND_ENDPOINT(1004, "URL not found", HttpStatus.NOT_FOUND),
    CUSTOM_MESSAGE(1005, "Error", HttpStatus.BAD_REQUEST),
    LEAD_AND_TRAILING_SPACES(1006, "Contain leading and trailing spaces", HttpStatus.BAD_REQUEST),
    NOTFOUND_ID(1007, "Not found id", HttpStatus.NOT_FOUND),
    NOTFOUND_DATA(1008, "Not found data", HttpStatus.NOT_FOUND),
    UPDATE_FAIL(1009, "Data update error", HttpStatus.BAD_REQUEST),
    FILTER_EMPTY(1010, "Not found data filter", HttpStatus.BAD_REQUEST),
    DATA_EXIST(1011, "Duplicate data", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1041, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    NO_ACCESS(1042, "This account does not have access", HttpStatus.FORBIDDEN),
    BODY_PARSE_FAIL(1042, "Body parse fail", HttpStatus.BAD_REQUEST),

    INVALID_PAGE_NUMBER(1030, "Invalid page number", HttpStatus.BAD_REQUEST),

    INVALID_AMOUNT(5010, "Amount too small", HttpStatus.BAD_REQUEST),

    UPDATE_STATUS_FAIL(5020, "Update status error", HttpStatus.INTERNAL_SERVER_ERROR),

    VALIDATE_INFO_PAYMENT_ERROR(5030, "Payment failed", HttpStatus.BAD_REQUEST),
    PAYMENT_SUCCESS_BUT_UPDATE_STATUS_FAIL(5031, "Transaction was successful but status update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_CALL_TO_ORDER_CLIENT(5032, "An error occurred, we will process it within 1 hour at the latest", HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_VERIFIED(5033, "Transaction has been confirmed", HttpStatus.BAD_REQUEST),
    TRANSACTION_ERROR(5034, "Payment failed", HttpStatus.BAD_REQUEST),

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
