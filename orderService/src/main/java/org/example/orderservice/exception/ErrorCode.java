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

    PRODUCT_QUANTITY_IS_TOO_SMALL(4010, "There must be at least 1 product in the order", HttpStatus.BAD_REQUEST),
    PRODUCT_QUANTITY_IS_TOO_LARGE(4011, "Product quantity must be less than 10", HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND(4012, "Book not found", HttpStatus.NOT_FOUND),
    INADEQUATE_QUANTITY(4013, "Product ? only has quantity: ?", HttpStatus.BAD_REQUEST),
    BOOK_NOT_RELEASED(4014, "Book ? not released yet", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_FOUND(4015, "Receiving address not supported", HttpStatus.NOT_FOUND),
    PAYMENT_METHOD_NOT_FOUND(4016, "Receiving payment method not supported", HttpStatus.NOT_FOUND),


    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
