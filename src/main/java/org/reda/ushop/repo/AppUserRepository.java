package org.reda.ushop.repo;

import org.reda.ushop.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    void deleteByUsername(String username);
}
