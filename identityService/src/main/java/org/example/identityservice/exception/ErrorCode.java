package org.example.identityservice.exception;

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
    
    PERMISSION_EXISTED(1010, "Permissions exist", HttpStatus.BAD_REQUEST),
    PERMISSION_NOTFOUND(1011, "Permission is not exist", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1015, "Role exist", HttpStatus.BAD_REQUEST),
    ROLE_NOTFOUND(1016, "Role is not exist", HttpStatus.BAD_REQUEST),

    INVALID_EMAIL(1020, "Invalid email", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER(1021, "Invalid phone number", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1022, "Invalid password", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1023, "Email existed", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME_PASSWORD(1024, "Invalid username or password", HttpStatus.BAD_REQUEST),
    ACCOUNT_LOCKED(1025, "Account has been locked", HttpStatus.FORBIDDEN),
    ACCOUNT_PENDING_VERIFY(1026, "Account pending verification", HttpStatus.FORBIDDEN),
    USER_NOT_EXIST(1027, "User not exist", HttpStatus.NOT_FOUND),


    INVALID_PAGE_NUMBER(1030, "Invalid page number", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1041, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    NO_ACCESS(1042, "This account does not have access", HttpStatus.FORBIDDEN),
    BODY_PARSE_FAIL(1042, "Body parse fail", HttpStatus.BAD_REQUEST),


    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
