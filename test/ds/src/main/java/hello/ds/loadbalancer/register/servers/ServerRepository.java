package hello.ds.loadbalancer.register.servers;

import java.util.HashSet;
import java.util.Set;

public class ServerRepository {

    private static final Set<Server> registeredServers = new HashSet<>();
    private static ServerRepository instance = new ServerRepository();
    private ServerRepository() {

    }

    public static ServerRepository getInstance() {
        return instance;
    }

    public Boolean save(Server server) {
       return registeredServers.add(server);
    }

    public Boolean remove(Server server) {
        return registeredServers.remove(server);
    }

    public Set<Server> findAll() {
        return registeredServers;
    }
}
