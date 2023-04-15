package com.main.servermanagement.services.implementations;

import models.Server;
import com.main.servermanagement.repositories.ServerRepo;
import com.main.servermanagement.services.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

import static com.main.servermanagement.enumerations.Status.SERVER_DOWN;
import static com.main.servermanagement.enumerations.Status.SERVER_UP;
import static java.lang.Boolean.TRUE;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor // makes autowired constructor for final fields
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }


    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(
                address.isReachable(10_000)
                ? SERVER_UP
                : SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching servers by ID: {}", id);
        return null;
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {

        log.info("Deleting server: {}", id);
        serverRepo.deleteById(id);
        return TRUE;
    }

    private String setServerImageUrl() {  // PLACEHOLDER!
        String[] imageNames = { "server1.png", "server2.png", "server3.png", "server4.png" };

//        return ServletUriComponentBuilder.fromCurrentContextPath().path("/server/image/"+images[new Random().nextInt(4)]).toString();
        return imageNames[0];
    }

}
