Create
Database Mandomedia;

Use
Mandomedia;

create table Employee
(
    DUI            int auto_increment
        primary key,
    Name           varchar(256) not null,
    Department     varchar(256) null,
    Salary         float        not null,
    Marital_Status enum ('Free', 'Married', 'Widowed', 'Separated', 'Unmarried') default 'Free' null,
    NIT            int null
);

Insert INTO Employee (Name, Department, Salary, NIT)
VALUES ('Juan', 'Sales', 2300000, 458);

Insert INTO Employee (Name, Department, Salary, NIT)
VALUES ('Carlos', 'Administration', 4700000, 477);

Insert INTO Employee (Name, Department, Salary, NIT)
VALUES ('Jose', 'Services', 1800000, 466);

SELECT *
FROM Employee;
