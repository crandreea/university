create table users(
                      id integer primary key autoincrement,
                      username varchar(100) unique,
                      password varchar(100)
);


insert into users(username, password) values ('andreea', 'pass');


insert into users(username, password) values ('eric', 'pass');

create table configurations(
    id integer primary key autoincrement ,
    letters varchar(100),
    word1 varchar(100),
    word2 varchar(100),
    word3 varchar(100),
    word4 varchar(100)
);

insert into configurations(letters, word1, word2, word3, word4)
VALUES ('aer', 'are', 'aer', 'rea', 'era');

create table games(
    id integer primary key autoincrement ,
    player_id integer references users(id),
    configuration_id integer references configurations(id),
    startingTime timestamp,
    score integer,
    noOfGuessedWords integer
);

delete  from games;