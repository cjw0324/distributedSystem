package hello.ds.loadbalancer.healthcheck.HealthCheckMessageDto;

import lombok.Data;

@Data
public class MessageDto {
    public MessageDto(String cmd) {
        this.cmd = cmd;
    }

    private String cmd;
}
