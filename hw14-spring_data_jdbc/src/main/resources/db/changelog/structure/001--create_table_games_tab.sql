 create table if not exists games_tab (
    uuid UUID PRIMARY KEY NOT NULL,
    title VARCHAR(50) NOT NULL UNIQUE,
    genre VARCHAR(50)
 );