package com.lucio.appointments.adapter.in.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MeController {

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('USER_READ')")
    public Map<String, Object> me(Authentication authentication) {
        return Map.of(
                "principal", authentication.getName(),
                "authorities", authentication.getAuthorities()
        );
    }
}
