create table if not exists product (id int primary key, title varchar(128), description varchar(512), prices decimal(7,2), discount decimal (3,2), discounted_price decimal(7,2));