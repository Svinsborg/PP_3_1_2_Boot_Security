CREATE TABLE `users`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `first_name` varchar(45) NOT NULL,
    `last_name`  varchar(45) NOT NULL,
    `password`   varchar(64) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `first_name` (`first_name`)
);

CREATE TABLE `roles`
(
    `role_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`    varchar(45) NOT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE `users_roles`
(
    `user_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,

    FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
        ON DELETE CASCADE
);

INSERT INTO `users` (`first_name`, `last_name`, `password`) VALUES ('Admin', 'mail', '111');
INSERT INTO `users` (`first_name`, `last_name`, `password`) VALUES ('User', 'yandex', '111');

INSERT INTO `roles` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `roles` (`name`) VALUES ('ROLE_USER');


INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (1, 2);
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (2, 2);