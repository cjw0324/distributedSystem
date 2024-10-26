package hello.ds.loadbalancer.register.servers;

import lombok.Data;

@Data
public class Server {

    public Server(String ip, int port, String protocol) {
        this.ip = ip;
        this.port = port;
        this.protocol = protocol;
    }

    private String ip;
    private int port;
    private String protocol;
}
