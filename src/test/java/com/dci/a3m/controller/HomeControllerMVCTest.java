package com.dci.a3m.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerMVCTest {

    @InjectMocks
    private HomeControllerMVC homeControllerMVC;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHome() {
        String view = homeControllerMVC.home();
        assertEquals("redirect:/login-success", view);
    }

    @Test
    void testHomeHome() {
        String view = homeControllerMVC.homehome();
        assertEquals("redirect:/login-success", view);
    }
}

