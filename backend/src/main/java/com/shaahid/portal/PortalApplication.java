package com.shaahid.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================================================
 * HOMEFINDER - SLIIT SE1020 OOP GROUP PROJECT
 * Group: WD04
 * Main Spring Boot Application Entry Point
 * ============================================================================
 * 
 * Group Members & Assigned Modules:
 * 1. Shaahid  (Member #1) - User Management & Authentication (com.shaahid.portal.user)
 * 2. Amaya    (Member #2) - House Property Listings (com.shaahid.portal.house)
 * 3. Hishadi  (Member #3) - Apartment Property Listings (com.shaahid.portal.apartment)
 * 4. Senuka   (Member #4) - Land Property Listings (com.shaahid.portal.land)
 * 5. Thasreef (Member #5) - Customer Reviews & Ratings (com.shaahid.portal.review)
 * 6. Dineth   (Member #6) - Owner Management & Photo Uploads (com.shaahid.portal.owner, image)
 * 
 * Shared Components:
 * - Property, Address, FileHelper (com.shaahid.portal.common)
 * 
 * Unified Server:
 * - Listens on port 8080 (http://localhost:8080)
 * - Automatically hosts both the REST API endpoints (/api/*) and the HTML/CSS/JS frontend!
 */
@SpringBootApplication
public class PortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
        System.out.println("====================================================================");
        System.out.println("  HomeFinder Backend & Frontend Unified Server Running!");
        System.out.println("  Access the application at: http://localhost:8080");
        System.out.println("====================================================================");
    }
}
