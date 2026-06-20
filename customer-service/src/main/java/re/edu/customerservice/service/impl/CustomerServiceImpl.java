package re.edu.customerservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import re.edu.customerservice.dto.request.CustomerLoginDTO;
import re.edu.customerservice.dto.request.CustomerRequestDTO;
import re.edu.customerservice.dto.response.CustomerResponseDTO;
import re.edu.customerservice.entity.Customer;
import re.edu.customerservice.exception.ResourceNotFoundException;
import re.edu.customerservice.repository.CustomerRepository;
import re.edu.customerservice.service.ICustomerService;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Optional;

@Setter
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerRepository;

    // Hàm mã hóa mật khẩu đơn giản (SHA-256)
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi mã hóa mật khẩu", e);
        }
    }

    @Override
    public CustomerResponseDTO registerCustomer(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(hashPassword(dto.getPassword())); // Mã hóa trước khi lưu

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerResponseDTO.builder()
                .id(savedCustomer.getId())
                .fullName(savedCustomer.getFullName())
                .email(savedCustomer.getEmail())
                .build();
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng với id: " + id));

        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public CustomerResponseDTO login(CustomerLoginDTO dto) {
        Optional<Customer> customerOpt = customerRepository.findByEmail(dto.getEmail());

        // Kiểm tra xem email có tồn tại không và mật khẩu mã hóa có khớp không
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            String hashedInputPassword = hashPassword(dto.getPassword());

            if (customer.getPassword().equals(hashedInputPassword)) {
                return CustomerResponseDTO.builder()
                        .id(customer.getId())
                        .fullName(customer.getFullName())
                        .email(customer.getEmail())
                        .build();
            }
        }
        // Nếu sai, trả về null
        return null;
    }
}
