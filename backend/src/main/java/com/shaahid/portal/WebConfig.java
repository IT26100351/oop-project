package com.shaahid.portal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ============================================================================
 * UNIFIED WEB & RESOURCE CONFIGURATION
 * Group: SE1020 Group WD04
 * ============================================================================
 * 
 * Configures the Spring MVC layer to:
 * 1. Enable full CORS for REST API communication.
 * 2. Forward root URL requests ("/") directly to "index.html".
 * 3. Serve frontend HTML/CSS/JS files and uploaded property photos from disk.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORS Configuration: Allows cross-origin requests from any local development server or tool.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

    /**
     * Root View Controller: Forwards http://localhost:8080/ to http://localhost:8080/index.html.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    /**
     * Static Resource Handlers: Maps file paths so the single Spring Boot server serves:
     * - Uploaded property photos at /images/**
     * - Frontend web assets (HTML, CSS, JS) at /**
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1. Uploaded property images
        List<String> imageLocations = new ArrayList<>();
        File img1 = new File("data/images").getAbsoluteFile();
        File img2 = new File("backend/data/images").getAbsoluteFile();
        File img3 = new File("../data/images").getAbsoluteFile();
        if (img1.exists()) {
            imageLocations.add("file:" + img1.getAbsolutePath().replace('\\', '/') + "/");
        }
        if (img2.exists()) {
            imageLocations.add("file:" + img2.getAbsolutePath().replace('\\', '/') + "/");
        }
        if (img3.exists()) {
            imageLocations.add("file:" + img3.getAbsolutePath().replace('\\', '/') + "/");
        }
        imageLocations.add("file:" + img1.getAbsolutePath().replace('\\', '/') + "/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations(imageLocations.toArray(new String[0]));

        // 2. Frontend web application (HTML, CSS, JS)
        List<String> frontendLocations = new ArrayList<>();
        File front1 = new File("../frontend").getAbsoluteFile();
        File front2 = new File("frontend").getAbsoluteFile();
        File front3 = new File("backend/frontend").getAbsoluteFile();
        if (front1.exists() && front1.isDirectory()) {
            frontendLocations.add("file:" + front1.getAbsolutePath().replace('\\', '/') + "/");
        }
        if (front2.exists() && front2.isDirectory()) {
            frontendLocations.add("file:" + front2.getAbsolutePath().replace('\\', '/') + "/");
        }
        if (front3.exists() && front3.isDirectory()) {
            frontendLocations.add("file:" + front3.getAbsolutePath().replace('\\', '/') + "/");
        }
        frontendLocations.add("classpath:/static/");

        registry.addResourceHandler("/**")
                .addResourceLocations(frontendLocations.toArray(new String[0]))
                .setCachePeriod(0);
    }
}
