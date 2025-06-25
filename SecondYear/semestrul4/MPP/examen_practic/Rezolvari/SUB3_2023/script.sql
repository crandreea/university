create table users(
                      id integer primary key autoincrement,
                      username varchar(100) unique,
                      password varchar(100)
);


insert into users(username, password) values ('andreea', 'pass'),
                                             ('eric', 'pass');


create table words(
    id integer primary key autoincrement ,
    word varchar(100)
);

insert into words(word) values
                            ('uga'), ('buga'), ('cool'), ('frfr'), ('smecher'),
                            ('chitibus'), ('hello'), ('salut'), ('buna'), ('ceau');
create table configurations(
    id integer primary key autoincrement
);

insert into configurations default values ;

create table configurationwords(
    id integer primary key autoincrement ,
    configuration_id integer  references configurations(id),
    word_id integer  references words(id),
    word_number integer
);

create table games(
    id integer primary key autoincrement ,
    player_id integer references users(id),
    configuration_id integer references configurations(id),
    no_of_seconds integer,
    score integer
);

create table positions(
    id integer primary key autoincrement ,
    game_id integer references games(id),
    coordinateX integer,
    coordinateY integer,
    position_index integer

)