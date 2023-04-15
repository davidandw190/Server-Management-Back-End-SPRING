package com.main.servermanagement.repositories;

import models.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepo extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);
    // Tells JPA to select by..


}
