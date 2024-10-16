package cjw.loadbalancer.repository;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ServerRepository {

    private static int count = 1;
    private static final Set<String> registeredServers = new HashSet<>(); // "127.0.0.1:tcp:81" ->
//    private static final Set<List<String>> registeredServers1 = new HashSet<>(); // 바람직할지도
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

    public void sequence(){
        count++;
    }
    public int getCount(){
        return count;
    }

}
