package org.reda.ushop.entities;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Data
public class RoleUserForm {
        String username;
        String roleName;

}
