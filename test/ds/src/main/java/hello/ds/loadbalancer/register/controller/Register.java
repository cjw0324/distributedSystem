package hello.ds.loadbalancer.register.controller;

import hello.ds.loadbalancer.register.dto.RegisterDto;
import hello.ds.loadbalancer.register.servers.Server;
import hello.ds.loadbalancer.register.servers.ServerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/register")
public class Register {
    ServerRepository serverRepository = ServerRepository.getInstance();
    @PostMapping
    public void register(@RequestBody RegisterDto registerDto, HttpServletRequest request) {

        log.info("request msg = cmd : {}, port : {}, protocol : {}, ip : {}", registerDto.getCmd(), registerDto.getPort(), registerDto.getProtocol(), request.getRemoteAddr());

        Boolean saveCheck = serverRepository.save(new Server(request.getRemoteAddr(), registerDto.getPort(), registerDto.getProtocol()));

        if (saveCheck) {
            log.info("save success cmd : {}, port : {}, protocol : {}, ip : {}", registerDto.getCmd(), registerDto.getPort(), registerDto.getProtocol(), request.getRemoteAddr());
        } else {
            log.info("save failed");
        }

    }
}
