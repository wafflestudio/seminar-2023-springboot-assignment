drop table if exists albums cascade;
drop table if exists artists cascade;
drop table if exists playlist_groups cascade;
drop table if exists playlist_songs cascade;
drop table if exists playlists cascade;
drop table if exists song_artists cascade;
drop table if exists songs cascade;
drop table if exists users cascade;
drop table if exists playlist_likes cascade;

create table albums (
    id bigint auto_increment,
    title varchar(255),
    image varchar(200),
    artist_id bigint,
    primary key (id)
);
create table artists (
    id bigint auto_increment,
    name varchar(255),
    primary key (id),
    unique(name)
);
create table playlist_groups (
    id bigint auto_increment,
    open boolean not null,
    title varchar(255),
    primary key (id)
);
create table playlist_songs (
    id bigint auto_increment,
    playlist_id bigint,
    song_id bigint,
    primary key (id)
);
create table playlists (
    id bigint auto_increment,
    title varchar(255),
    subtitle varchar(255),
    image varchar(200),
    group_id bigint,
    primary key (id)
);
create table song_artists (
    id bigint auto_increment,
    artist_id bigint,
    song_id bigint,
    primary key (id)
);
create table songs (
    id bigint auto_increment,
    title varchar(255),
    duration int not null,
    album_id bigint,
    primary key (id)
);
create table users (
    id bigint auto_increment,
    username varchar(100),
    password varchar(20),
    image varchar(255),
    primary key (id)
);
create table playlist_likes (
    id bigint auto_increment,
    playlist_id bigint not null,
    user_id bigint not null,
    primary key (id)
);
