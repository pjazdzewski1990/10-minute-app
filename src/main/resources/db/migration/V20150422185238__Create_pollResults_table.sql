-- For H2 Database
create table poll_results (
  id bigserial not null primary key,
  name varchar(512) not null,
  email varchar(512) not null,
  description varchar(512) not null,
  role bigint not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
