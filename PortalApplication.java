package com.shaahid.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This is the START of the backend. Run this class to start the server on port 8080.
// IMPORTANT: it must stay in the root package (com.shaahid.portal) so Spring can find
// all the controllers inside the sub-packages (house, user, review ...).
@SpringBootApplication
public class PortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }
}
