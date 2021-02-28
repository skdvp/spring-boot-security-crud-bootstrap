# create database preDB3120;

# use preDB3120;

-- Table: users
CREATE TABLE preDB3120.users
(
    id       INT          NOT NULL AUTO_INCREMENT,

    name     varchar(255),
    surname  varchar(255),
    age      int          NOT NULL,

    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);

-- Table: roles
CREATE TABLE preDB3120.roles
(
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,

    PRIMARY KEY (id)
);

-- Table for mapping user and roles: user_roles
CREATE TABLE preDB3120.user_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id),

    UNIQUE (user_id, role_id)
);

INSERT preDB3120.users
VALUES (null, 'Admin', 'Admins', 35, 'ADMIN', '$2a$10$DK2Yd6NWHU9cGcJqOjbpBuV1RSF51HfEncxiD7XXLNTVYfPiR/KMi'),
       (null, 'Test', 'Users', 23, 'test', '$2a$10$tkaaFW0TJ6UTwr9rbofhD.e3nnmxAmHWofy1U5AQnTh7ogbFAgSgK'),
       (null, 'test2role', 'test2role', 33, 'test2role', '$2a$10$MmdCwK3PrBUSdXs/1dcecOG9jDp28Rt0G502Bna9ZbMgWFSE6MhV.'),
       (null, 'test-user-del1', 'test-user-del1', 24, 'test-user-del1', '$2a$10$J85ZQtTQdDOtk6LWfNkS7uT2IlYTHzsGpDeOwq7VijEAzJHz8jX9K'),
       (null, 'test-user-del2', 'test-user-del2', 55, 'test-user-del2', '$2a$10$sZGU0iTQZ0NmtX4U43XDTuIYC4lG.bKR01huuhpvcBLMyh3CXKQom'),
       (null, 'test-admin-del1', 'test-admin-del1', 88, 'test-admin-del1', '$2a$10$d8Pz2k1HWEDs0bX48LCtBezAPt.FKMnRcIQpK4c4Z/W3zqIBJ7KkO'),
       (null, 'test-admin-del2', 'test-admin-del2', 26, 'test-admin-del2', '$2a$10$/jn6DvxouI/iySXYdxGkJe7GRj4wzfdPFyVcUA/F./2Oazfy23ql.');

INSERT preDB3120.roles
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT preDB3120.user_roles
VALUES (1, 2),
       (2, 1),
       (3, 2),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 2),
       (7, 2);
