package hello.ds.loadbalancer.register.controller;

import hello.ds.loadbalancer.register.dto.RegisterDto;
import hello.ds.loadbalancer.register.service.RegisterNewServerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/register")
public class Register {
    @PostMapping
    public void register(@RequestBody RegisterDto registerDto, HttpServletRequest request) {
        log.info("request msg = cmd : {}, port : {}, protocol : {}, ip : {}", registerDto.getCmd(), registerDto.getPort(), registerDto.getProtocol(), request.getRemoteAddr());
        RegisterNewServerService registerNewServerService = new RegisterNewServerService();
        registerNewServerService.saveNewServer(registerDto, request);

    }
}
