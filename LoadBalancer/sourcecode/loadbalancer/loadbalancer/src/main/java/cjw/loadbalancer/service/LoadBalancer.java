package cjw.loadbalancer.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LoadBalancer {
    private final List<String> apiServers = List.of("http://localhost:8081", "http://localhost:8082");
    private final List<String> tcpServers = List.of("localhost:9001", "localhost:9002");
    private final AtomicInteger apiIndex = new AtomicInteger(0);
    private final AtomicInteger tcpIndex = new AtomicInteger(0);

    // 라운드 로빈 방식으로 API 서버 선택
    public String getApiServer() {
        int index = apiIndex.getAndIncrement() % apiServers.size();
        return apiServers.get(index);
    }

    // 라운드 로빈 방식으로 TCP 서버 선택
    public String getTcpServer() {
        int index = tcpIndex.getAndIncrement() % tcpServers.size();
        return tcpServers.get(index);
    }
}
