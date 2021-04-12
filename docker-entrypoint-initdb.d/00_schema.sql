create table cats (
    id bigserial primary key,
    name text not null unique,
    image_url text not null,
    rating numeric default 0
);

create table users (
    id bigserial primary key,
    name text not null unique
);

create table m2m_cats_users (
    c_id bigserial not null,
    u_id bigserial not null,
    primary key (c_id, u_id)
);
