package org.reda.ushop.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.reda.ushop.entities.AppRole;
import org.reda.ushop.entities.AppUser;
import org.reda.ushop.entities.RoleUserForm;
import org.reda.ushop.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
    public class AccountRestController {
        private  AccountService accountService;
        private RoleUserForm roleUserForm;
        public AccountRestController(AccountService accountService, RoleUserForm roleUserForm) {
            this.accountService = accountService;
            this.roleUserForm = roleUserForm;
        }
        @GetMapping(path="/users")
        @PostAuthorize("hasAuthority('ADMIN')")
        public List<AppUser> appUsers() {
            return accountService.listUsers();
        }
        @PostMapping(path="/users")

        public AppUser saveUser(@RequestBody AppUser appUser) {
            return accountService.addNewUser(appUser);
        }
        @PostMapping(path="/roles")
        @PostAuthorize("hasAuthority('ADMIN')")
        public AppRole saveRole(@RequestBody AppRole appRole) {
            return accountService.addNewRole(appRole);
        }
        @PostMapping(path="/addRoleToUser")
        @PostAuthorize("hasAuthority('ADMIN')")
        public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
            accountService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRoleName());
        }
        @DeleteMapping("/user/{username}")
        @PostAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<String> deleteUser(@PathVariable String username) {
            accountService.deleteUserByUsername(username);
                return ResponseEntity.ok("Utilisateur supprimé avec succès");
    }
        @GetMapping(path="/refreshToken")
        public void refreshToken(HttpServletRequest request, HttpServletResponse response)throws Exception {
            String authTocken = request.getHeader("Authorization");
            if (authTocken != null && authTocken.startsWith("Bearer ")){
                try {
                    String jwt = authTocken.substring(7);
                    Algorithm algorithm = Algorithm.HMAC256("mysecret123");
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(jwt);
                    String username = decodedJWT.getSubject();
                    AppUser appUser = accountService.loadUserByUsername(username);

                    String JwtAccessToken = JWT.create()
                            .withSubject(appUser.getUsername())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                            .withIssuer(request.getRequestURL().toString())
                            .withClaim("roles",appUser.getAppRoles().stream().map(au->au.getRoleName()).collect(Collectors.toList()))
                            .sign(algorithm);

                    Map<String, Object> IdToken = new HashMap<>();
                    IdToken.put("access_token", JwtAccessToken);
                    IdToken.put("refresh_token", jwt);
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), IdToken);
                }
                catch (Exception e) {
                    throw e;

                }


            }else {
                throw new RuntimeException("refresh token required");
            }
        }



}


