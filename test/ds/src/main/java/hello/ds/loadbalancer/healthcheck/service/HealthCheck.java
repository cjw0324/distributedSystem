package hello.ds.loadbalancer.healthcheck.service;

import hello.ds.loadbalancer.register.servers.Server;
import hello.ds.loadbalancer.register.servers.ServerRepository;
public class HealthCheck {

    ServerRepository serverRepository = ServerRepository.getInstance();
    public boolean sendMessage() {
        Server server = serverRepository.findAll().iterator().next();
        System.out.println(serverRepository.findAll().toString());
        System.out.println(server.toString());
        return true;
    }

}
