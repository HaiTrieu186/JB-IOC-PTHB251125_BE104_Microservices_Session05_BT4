package re.edu.customerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import re.edu.customerservice.dto.response.ApiResponseError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // LỖI KHÔNG TÌM THẤY DỮ LIỆU
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponseError response = ApiResponseError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 3. LỖI HỆ THỐNG
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseError> handleGeneralException(Exception ex) {
        ApiResponseError response = ApiResponseError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("Đã xảy ra sự cố trên máy chủ: " + ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
