package org.reda.ushop.services;

import org.reda.ushop.entities.AppRole;
import org.reda.ushop.entities.AppUser;
import org.reda.ushop.repo.AppRoleRepository;
import org.reda.ushop.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private PasswordEncoder passwordEncoder;
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    public AccountServiceImpl(PasswordEncoder passwordEncoder, AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }

    @Override
    public AppUser addNewUser(AppUser appUser) {
        String pw = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
        AppRole userRole = appRoleRepository.findByRoleName("USER");
        if (userRole == null) {
            userRole = appRoleRepository.save(new AppRole(null, "USER"));
        }
        appUser.getAppRoles().add(userRole);
        return appUserRepository.save(appUser);
    }


    @Override
    public AppRole addNewRole(AppRole appRole) {
        return  appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);


    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username) ;
    }

    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }
    @Override
    public void deleteUserByUsername(String username) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user != null) {
            appUserRepository.delete(user);
        } else {
            throw new RuntimeException("User not found with username: " + username);
        }
    }

}

