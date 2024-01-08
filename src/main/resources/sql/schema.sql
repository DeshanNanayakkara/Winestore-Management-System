create database hardware;

use hardware;

create table employee(
                         emp_id varchar(10) primary key not null,
                         name varchar(30),
                         contact varchar(30),
                         address varchar(30)
);
create table Tablee(
                       Tabale_id varchar(10) primary key not null,
                       states varchar(15)
);



insert into Tablee values ("C1-01","Available");
insert into Tablee values ("C1-02","Available");
insert into Tablee values ("C1-03","Unvailable");
insert into Tablee values ("C1-04","Available");
insert into Tablee values ("C1-05","Unvailable");
insert into Tablee values ("C1-06","Available");
insert into Tablee values ("C1-07","Unvailable");
insert into Tablee values ("C1-08","Available");
insert into Tablee values ("C1-09","Unvailable");
insert into Tablee values ("C1-10","Available");
insert into Tablee values ("C4-01","Unvailable");
insert into Tablee values ("C4-02","Available");
insert into Tablee values ("C4-03","Unvailable");
insert into Tablee values ("C4-04","Available");
insert into Tablee values ("C4-05","Unvailable");
insert into Tablee values ("C8-01","Available");
insert into Tablee values ("C8-02","Unvailable");


create table bite(
                     item_id varchar(10) primary key not null,
                     unit_price decimal(9,2),
                     description varchar(100)
);

create table user(
                     username varchar(10) primary key not null,
                     password varchar(30)
);

insert into user values ("00","00");

create table attendance(
                           emp_id varchar(10),
                           foreign key (emp_id) references Employee(emp_id) on delete cascade on update cascade,
                           date date,
                           time_in varchar(10),
                           time_out varchar(10),
                           attendance_id varchar(30) primary key not null
);

create table salary(
                       salary_id varchar(10) primary key not null,
                       emp_id varchar(10),
                       foreign key (emp_id) references Employee(emp_id) on delete cascade on update cascade,
                       payment_date date,
                       amount varchar(10)
);

create table customer(
                         cust_id varchar(10) primary key not null,
                         name varchar(30),
                         contact varchar(30)
);

create table orders(
                       order_id varchar(10) primary key not null,
                       price decimal(9,2),
                       date date,
                       cust_id varchar(10),
                       foreign key (cust_id) references customer(cust_id) on delete cascade on update cascade
);

create table item(
                     item_id varchar(10) primary key not null,
                     unit_price decimal(9,2),
                     stock_price decimal(9,2),
                     description varchar(100),
                     qtyOnHand int(5)
);

create table order_details(
                              order_id varchar(10),
                              foreign key (order_id) references orders(order_id) on delete cascade on update cascade,
                              item_id varchar(10),
                              foreign key (item_id) references item(item_id) on delete cascade on update cascade,
                              quantity int(5),
                              description varchar(50)
);

create table supplier(
                         sup_id varchar(10) primary key not null,
                         name varchar(30),
                         contact varchar(30),
                         address varchar(30)
);

create table supplier_orders(
                                sup_id varchar(10),
                                foreign key (sup_id) references supplier(sup_id) on delete cascade on update cascade,
                                quantity int(4),
                                item_id varchar(10),
                                foreign key (item_id) references item(item_id) on delete cascade on update cascade
);