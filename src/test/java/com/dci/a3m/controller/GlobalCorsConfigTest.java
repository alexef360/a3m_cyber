package com.dci.a3m.controller;

import com.dci.a3m.config.GlobalCorsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistration;

import static org.mockito.Mockito.*;

public class GlobalCorsConfigTest {

    @Mock
    private CorsRegistry corsRegistry;

    @Mock
    private CorsRegistration corsRegistration;

    @InjectMocks
    private GlobalCorsConfig globalCorsConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(corsRegistry.addMapping(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedOrigins(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods(anyString(), anyString(), anyString(), anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowCredentials(anyBoolean())).thenReturn(corsRegistration);
    }

    @Test
    void testCorsConfigurer() {
        // Arrange
        WebMvcConfigurer configurer = globalCorsConfig.corsConfigurer();

        // Act
        configurer.addCorsMappings(corsRegistry);

        // Assert
        verify(corsRegistry).addMapping("/api/**");
        verify(corsRegistration).allowedOrigins("http://localhost:3000");
        verify(corsRegistration).allowedMethods("GET", "POST", "PUT", "DELETE");
        verify(corsRegistration).allowedHeaders("*");
        verify(corsRegistration).allowCredentials(true);
    }
}
