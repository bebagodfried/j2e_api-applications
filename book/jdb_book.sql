use jdb_book;

create table books (
id int primary key auto_increment,
title varchar(255) not null,
content varchar(255) not null,
author varchar(255)
);

commit;
