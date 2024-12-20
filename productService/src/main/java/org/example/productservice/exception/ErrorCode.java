package org.example.productservice.exception;

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

    NOT_FOUND_STATUS_CODE(3001, "Status code not found", HttpStatus.NOT_FOUND),

    AUTHOR_NOT_FOUND(3010, "Author not found", HttpStatus.NOT_FOUND),

    PUBLISHER_NOT_FOUND(3020, "Publisher not found", HttpStatus.NOT_FOUND),

    LIST_BOOK_EMPTY(3030, "Must have at least 1 book to add", HttpStatus.BAD_REQUEST),

    QUANTITY_TOO_SMALL(3040, "Quantity must be greater than or equal to 1", HttpStatus.BAD_REQUEST),
    QUANTITY_TOO_LARGE(3040, "Quantity must be less than or equal to 30", HttpStatus.BAD_REQUEST),
    QUANTITY_NOT_ENOUGH(3042, "Quantity greater than available", HttpStatus.BAD_REQUEST),
    ADD_TO_CART_FAILED(3043, "Could not add product to cart", HttpStatus.BAD_REQUEST),

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
