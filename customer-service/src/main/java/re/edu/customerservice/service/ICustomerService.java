package re.edu.customerservice.service;

import re.edu.customerservice.dto.request.CustomerLoginDTO;
import re.edu.customerservice.dto.request.CustomerRequestDTO;
import re.edu.customerservice.dto.response.CustomerResponseDTO;

public interface ICustomerService {
    CustomerResponseDTO registerCustomer(CustomerRequestDTO dto);
    CustomerResponseDTO getCustomerById(Long id);
    CustomerResponseDTO login(CustomerLoginDTO dto);
}