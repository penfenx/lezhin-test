create table contents
(
ID            int auto_increment
primary key,
contents_name varchar(255)         null,
author        varchar(255)         null,
coin          int                  null,
open_date     datetime             null,
is_adult      tinyint(1) default 0 not null
);

create table review
(
user_id     int                      not null,
contents_id int                      not null,
rating      enum ('LIKE', 'DISLIKE') null,
comment     varchar(255)             null,
primary key (user_id, contents_id),
index idx_contents_id (contents_id),
index idx_user_id (user_id),
index idx_rating (rating)
);

create table user
(
ID            int auto_increment
primary key,
user_name     varchar(255)                          null,
user_email    varchar(255)                          null,
gender        enum ('MALE', 'FEMALE', 'NON_BINARY') null,
type          enum ('NORMAL', 'ADULT')              null,
register_date datetime                              null,
index idx_register_date (register_date)
);

create table view_log
(
log_id      int auto_increment
primary key,
user_id     int      null,
contents_id int      null,
view_date   datetime null,
index idx_user_id (user_id),
index idx_contents_id (contents_id)
);