CREATE TABLE app_user_app_roles (
                                    app_user_id BIGINT NOT NULL,
                                    app_roles_id BIGINT NOT NULL,
                                    PRIMARY KEY (app_user_id, app_roles_id),
                                    CONSTRAINT fk_user_role_user FOREIGN KEY (app_user_id) REFERENCES app_user(id),
                                    CONSTRAINT fk_user_role_role FOREIGN KEY (app_roles_id) REFERENCES app_role(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
