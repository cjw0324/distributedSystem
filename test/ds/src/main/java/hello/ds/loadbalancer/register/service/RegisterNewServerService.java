package hello.ds.loadbalancer.register.service;

import hello.ds.loadbalancer.register.dto.RegisterDto;
import hello.ds.loadbalancer.register.servers.Server;
import hello.ds.loadbalancer.register.servers.ServerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegisterNewServerService {
    ServerRepository serverRepository = ServerRepository.getInstance();
    public void saveNewServer(RegisterDto registerDto, HttpServletRequest request) {
        Boolean saveCheck = serverRepository.save(new Server(request.getRemoteAddr(), registerDto.getPort(), registerDto.getProtocol()));

        if (saveCheck) {
            log.info("save success cmd : {}, port : {}, protocol : {}, ip : {}", registerDto.getCmd(), registerDto.getPort(), registerDto.getProtocol(), request.getRemoteAddr());
        } else {
            log.info("save failed");
        }
    }

}
