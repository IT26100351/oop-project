package com.shaahid.portal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Unified web and API configuration for http://localhost:8080
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 1) CORS: allow all origins so any local setup or tool can connect
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

    // 2) Forward root "/" directly to "/index.html" so opening http://localhost:8080 loads the site
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    // 3) Serve uploaded images AND the frontend web pages
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Uploaded property photos in data/images/
        List<String> imageLocations = new ArrayList<>();
        File imgFolder1 = new File("data/images").getAbsoluteFile();
        File imgFolder2 = new File("backend/data/images").getAbsoluteFile();
        if (imgFolder1.exists()) {
            imageLocations.add("file:" + imgFolder1.getAbsolutePath().replace('\\', '/') + "/");
        }
        if (imgFolder2.exists()) {
            imageLocations.add("file:" + imgFolder2.getAbsolutePath().replace('\\', '/') + "/");
        }
        imageLocations.add("file:" + imgFolder1.getAbsolutePath().replace('\\', '/') + "/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations(imageLocations.toArray(new String[0]));

        // Frontend static files (HTML, CSS, JS)
        List<String> frontendLocations = new ArrayList<>();
        File front1 = new File("../frontend").getAbsoluteFile();
        File front2 = new File("frontend").getAbsoluteFile();
        if (front1.exists() && front1.isDirectory()) {
            frontendLocations.add("file:" + front1.getAbsolutePath().replace('\\', '/') + "/");
        }
        if (front2.exists() && front2.isDirectory()) {
            frontendLocations.add("file:" + front2.getAbsolutePath().replace('\\', '/') + "/");
        }
        frontendLocations.add("classpath:/static/");

        registry.addResourceHandler("/**")
                .addResourceLocations(frontendLocations.toArray(new String[0]))
                .setCachePeriod(0);
    }
}

