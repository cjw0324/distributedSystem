package cjw.loadbalancer.repository;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class ServerRepository {
    private static final Set<String> registeredServers = new HashSet<>();
    private static final ServerRepository instance = new ServerRepository();
    public static ServerRepository getInstance(){
        return instance;
    }
    private ServerRepository() {

    }
    public Boolean save(String idAndPort){
        if (registeredServers.add(idAndPort)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean remove(String idAndPort){
        if (registeredServers.remove(idAndPort)) {
            return true;
        } else {
            return false;
        }
    }

    public Set<String> findAll() {
        return new HashSet<>(registeredServers);
    }

}
