package org.reda.ushop.web;

import lombok.Data;
import org.reda.ushop.entities.AppRole;
import org.reda.ushop.entities.AppUser;
import org.reda.ushop.entities.RoleUserForm;
import org.reda.ushop.shop.services.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


    @RestController
    public class AccountRestController {
        private  AccountService accountService;
        private RoleUserForm roleUserForm;
        public AccountRestController(AccountService accountService, RoleUserForm roleUserForm) {
            this.accountService = accountService;
            this.roleUserForm = roleUserForm;
        }
        @GetMapping(path="/users")
        public List<AppUser> appUsers() {
            return accountService.listUsers();
        }
        @PostMapping(path="/users")
        public AppUser saveUser(@RequestBody AppUser appUser) {
            return accountService.addNewUser(appUser);
        }
        @PostMapping(path="/roles")
        public AppRole saveRole(@RequestBody AppRole appRole) {
            return accountService.addNewRole(appRole);
        }
        @PostMapping(path="/addRoleToUser")
           public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
            accountService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRoleName());
        }


    }


