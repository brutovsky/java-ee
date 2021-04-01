CREATE TABLE IF NOT EXISTS permissions(
                                          id         SERIAL PRIMARY KEY,
                                          permission VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS roles(
                                    id         SERIAL PRIMARY KEY,
                                    role VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS role_permissions (
                                                role_id INT NOT NULL,
                                                permission_id INT NOT NULL,
                                                CONSTRAINT fk_role_permission_role
                                                    FOREIGN KEY(role_id)
                                                        REFERENCES roles(id),
                                                CONSTRAINT fk_role_permission_permission
                                                    FOREIGN KEY(permission_id)
                                                        REFERENCES permissions(id)
);

CREATE TABLE IF NOT EXISTS users(
                                    id       SERIAL PRIMARY KEY,
                                    login    VARCHAR NOT NULL UNIQUE,
                                    password VARCHAR NOT NULL,
                                    role_id  INT NOT NULL,
                                    CONSTRAINT fk_role
                                        FOREIGN KEY(role_id)
                                            REFERENCES roles(id)
);

insert into roles (role) values
('ADMIN'),
('DEFAULT');

insert into permissions (permission) values
('VIEW_ADMIN'),
('VIEW_CATALOG');

insert into role_permissions (role_id, permission_id) values
((select id from roles where role = 'ADMIN'), (select id from permissions where permission = 'VIEW_ADMIN')),
((select id from roles where role = 'ADMIN'), (select id from permissions where permission = 'VIEW_CATALOG')),
((select id from roles where role = 'DEFAULT'), (select id from permissions where permission = 'VIEW_CATALOG'));

insert into users (login, password, role_id) values
('admin', 'password', (select id from roles where role = 'ADMIN')),
('user', 'password', (select id from roles where role = 'DEFAULT'));

CREATE TABLE IF NOT EXISTS books(
                                    id SERIAL PRIMARY KEY,
                                    author VARCHAR NOT NULL,
                                    isbn VARCHAR NOT NULL UNIQUE,
                                    publication_year INTEGER NOT NULL,
                                    title VARCHAR NOT NULL
);

INSERT INTO books VALUES (default, 'F. Scott Fitzgerald','978-3-66-148410-0',1925,'Great Gatsby');
INSERT INTO books VALUES (default, 'Frank Herbert', '978-3-16-148410-0', 1965,'Dune');
INSERT INTO books VALUES (default, 'J.R.R.Tolkien','999-3-16-148410-0',1968,'The Lord Of The Rings');
INSERT INTO books VALUES (default, 'William Golding','978-7-16-148410-3',1954,'Lord Of The Flies');
INSERT INTO books VALUES (default, 'Ken Kesey','955-3-78-148410-0',1962,'One Flew Over the Cuckoo`s Nest');
INSERT INTO books VALUES (default, 'Douglas Adams','978-3-42-148410-0',1979,'The Hitchhiker`s Guide to the Galaxy');
INSERT INTO books VALUES (default, 'George Orwell','999-5-55-148410-0',1949,'1984');
INSERT INTO books VALUES (default, 'Fyodor Dostoyevsky','666-7-16-148410-3',1872,'Demons');
INSERT INTO books VALUES (default, 'Anthony Burgess','955-3-78-333330-0',1986,'A Clockwork Orange');
INSERT INTO books VALUES (default, 'Philip K. Dick','987-3-42-148410-0',1968,'Do Androids Dream of Electric Sheep?');

CREATE TABLE IF NOT EXISTS user_fav_book(
                                            user_id INT NOT NULL,
                                            book_id INT NOT NULL,
                                            CONSTRAINT fk_user_id
                                                FOREIGN KEY(user_id)
                                                    REFERENCES users(id),
                                            CONSTRAINT fk_book_id
                                                FOREIGN KEY(book_id)
                                                    REFERENCES books(id)
);

insert into user_fav_book (user_id, book_id) values
((select id from users where login = 'user'), (select id from books where title = 'Great Gatsby')),
((select id from users where login = 'user'), (select id from books where title = 'Dune'));