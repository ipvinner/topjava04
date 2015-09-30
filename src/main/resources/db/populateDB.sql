DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;


INSERT INTO users (name, email, password) VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password) VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES ('ROLE_USER', 100000);
INSERT INTO user_roles (role, user_id) VALUES ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id) VALUES ('2015-06-24 09:00:00', 'завтрак', 400, 100000);
INSERT INTO meals (datetime, description, calories, user_id) VALUES ('2015-06-24 12:00:00', 'обед', 400, 100000);
INSERT INTO meals (datetime, description, calories, user_id) VALUES ('2015-06-24 22:00:00', 'ужин', 100, 100000);

INSERT INTO meals (datetime, description, calories, user_id) VALUES ('2015-06-25 09:00:00', 'завтрак', 200, 100000);
INSERT INTO meals (datetime, description, calories, user_id) VALUES ('2015-06-25 13:00:00', 'обед', 700, 100000);
INSERT INTO meals (datetime, description, calories, user_id) VALUES ('2015-06-25 19:00:00', 'ужин', 400, 100000);
