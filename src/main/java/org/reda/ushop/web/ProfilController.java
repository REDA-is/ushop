package org.reda.ushop.web;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class ProfilController {

    @GetMapping("/me")
    public Map<String, Object> me(Authentication auth) {
        String username = auth.getName();
        List<String> roles = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Map.of(
                "username", username,
                "roles", roles
        );
    }
}

