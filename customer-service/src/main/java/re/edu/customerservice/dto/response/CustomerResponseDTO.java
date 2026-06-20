package re.edu.customerservice.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerResponseDTO {
    private Long id;
    private String fullName;
    private String email;
}