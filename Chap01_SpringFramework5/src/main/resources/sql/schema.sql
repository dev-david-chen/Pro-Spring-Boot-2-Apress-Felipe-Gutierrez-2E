CREATE TABLE IF NOT EXISTS todo (
  id varchar(100) not null,
  description varchar(255) not null,
  created timestamp,
  modified timestamp,
  completed boolean
);


