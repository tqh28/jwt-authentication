
drop database if exists authentication;

create database authentication;
use authentication;

create table user (
	id int not null auto_increment,
    username varchar(50) unique not null,
    password text not null,
    email varchar(100) unique,
    firstname varchar(100),
    lastname varchar(100),
    dob date,
    roles int not null,
    active boolean not null default true,
    primary key(id)
);

-- insert users
--    admin/admin with both roles: ROLE_USER & ROLE_ADMIN
--    user1/user1 with role: ROLE_USER
--    user2/user2 with role: ROLE_USER
insert into user (username, password, roles) values
    ('admin', '$2a$10$mNnPVz3Z2kFdesKhIyBjjOWoD7Fpy1Ad8qfADHHA8fa8VD31jTjnu', 3),
    ('user1', '$2a$10$9/X3PN281mbqw7rDpaUeFezyxIZtfXRivJAM/H9HYgpg9nQyzcsCS', 2),
    ('user2', '$2a$10$y084f/w4MCXcVqaVKLy9nusFdrM8ZjydjdSeWbyy85yVEP9RO/bbO', 2);

select * from user;
