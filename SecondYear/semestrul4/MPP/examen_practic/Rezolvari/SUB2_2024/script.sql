create table users(
                      id integer primary key autoincrement,
                      username varchar(100) unique,
                      password varchar(100)
);


insert into users(username, password) values ('andreea', 'pass'),
                                             ('eric', 'pass');



create table games(
                      id integer primary key autoincrement ,
                      player_id integer references users(id),
                      no_of_seconds integer,
                      score integer
);

create table positions
(
    id          integer primary key autoincrement,
    game_id     integer references games (id),
    coordinateX integer,
    coordinateY integer,
    is_revealed integer,
    is_trap     integer,
    shot_order  integer
)


