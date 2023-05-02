CREATE TABLE IF NOT EXISTS products
(
    id    BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(21) NOT NULL,
    image TEXT        NOT NULL,
    price INT         NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(31) NOT NULL,
    password VARCHAR(31) NOT NULL
);

DELETE
FROM users;

INSERT INTO users(email, password)
VALUES ('hjaehyun25@gmail.com', '비밀번호 입니당'),
       ('연어@잠온다.com', 'password');
