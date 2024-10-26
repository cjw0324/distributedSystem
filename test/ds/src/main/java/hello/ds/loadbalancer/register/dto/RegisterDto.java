package hello.ds.loadbalancer.register.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RegisterDto {
    private String cmd;
    private int port;
    private String protocol;
}
