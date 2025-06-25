create table users(
                      id integer primary key autoincrement,
                      username varchar(100) unique,
                      password varchar(100)
);


insert into users(username, password) values ('andreea', 'pass');