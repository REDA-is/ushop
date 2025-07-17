package org.reda.ushop;

import org.reda.ushop.entities.AppRole;
import org.reda.ushop.entities.AppUser;
import org.reda.ushop.shop.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(UshopApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CommandLineRunner start(AccountService accoutService) {
        return args -> {
            accoutService.addNewRole(new AppRole(null,"USER"));
            accoutService.addNewRole(new AppRole(null,"ADMIN"));
            accoutService.addNewRole(new AppRole(null,"CUSTOMER"));


            accoutService.addNewUser(new AppUser(null,"USER1","12324",new ArrayList<>()));
            accoutService.addNewUser(new AppUser(null,"ADMIN","12324",new ArrayList<>()));
            accoutService.addNewUser(new AppUser(null,"CUSTOMER","12324",new ArrayList<>()));


            accoutService.addRoleToUser("USER1","USER");
            accoutService.addRoleToUser("ADMIN","ADMIN");
            accoutService.addRoleToUser("ADMIN","USER");
            accoutService.addRoleToUser("CUSTOMER","CUSTOMER");
            accoutService.addRoleToUser("CUSTOMER","USER");

        };
    }

}
