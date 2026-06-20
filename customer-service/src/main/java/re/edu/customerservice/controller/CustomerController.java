package re.edu.customerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import re.edu.customerservice.dto.request.CustomerLoginDTO;
import re.edu.customerservice.dto.request.CustomerRequestDTO;
import re.edu.customerservice.dto.response.ApiResponse;
import re.edu.customerservice.dto.response.CustomerResponseDTO;
import re.edu.customerservice.service.ICustomerService;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> register(@RequestBody CustomerRequestDTO requestDTO) {
        CustomerResponseDTO data = customerService.registerCustomer(requestDTO);

        ApiResponse<CustomerResponseDTO> response = ApiResponse.success("Đăng ký tài khoản thành công", data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> getCustomerById(@PathVariable Long id) {
        CustomerResponseDTO data = customerService.getCustomerById(id);

        ApiResponse<CustomerResponseDTO> response = ApiResponse.success("Lấy thông tin khách hàng thành công", data);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerLoginDTO loginDTO) {
        CustomerResponseDTO data = customerService.login(loginDTO);

        if (data != null) {
            ApiResponse<CustomerResponseDTO> response = ApiResponse.success("Đăng nhập thành công", data);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("email or password incorrect");
        }
    }
}