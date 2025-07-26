CREATE TABLE app_role (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          role_name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
