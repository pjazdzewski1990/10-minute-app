-- For H2 Database
create table employee_roles (
  id bigserial not null primary key,
  name varchar(512) not null,
  technology varchar(512) not null,
  responsibilities varchar(512) not null,
  min_experience int not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
