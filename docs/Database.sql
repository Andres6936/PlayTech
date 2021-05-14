create table PlayTech.Meal
(
    Identification int         not null,
    Starter        varchar(99) null,
    Entree         varchar(99) not null,
    Appetizer      varchar(99) null,
    Price          int         not null,
    constraint Meal_Identification_uindex
        unique (Identification)
);

alter table PlayTech.Meal
    add primary key (Identification);

create table PlayTech.DailyMenu
(
    First  int      not null,
    Second int      not null,
    Third  int      not null,
    Date   datetime not null,
    constraint DailyMenu_Meal_Identification_fk
        foreign key (First) references PlayTech.Meal (Identification),
    constraint DailyMenu_Meal_Identification_fk_2
        foreign key (Second) references PlayTech.Meal (Identification),
    constraint DailyMenu_Meal_Identification_fk_3
        foreign key (Third) references PlayTech.Meal (Identification)
);

create table PlayTech.`Table`
(
    Number
    int
    not
    null,
    constraint
    Table_Number_uindex
    unique
(
    Number
)
    );

alter table PlayTech.`Table`
    add primary key (Number);

create table PlayTech.Waiter
(
    Identification int         not null,
    FirstName      varchar(99) not null,
    LastName       varchar(99) null, `
    Table
    `
    int
    not
    null,
    constraint Waiter_Identification_uindex
        unique (Identification),
    constraint Waiter_Table_FK
        foreign key (Identification) references PlayTech.` Table` (Number)
);

alter table PlayTech.Waiter
    add primary key (Identification);

create table PlayTech.`Order`
(
    MealIdentification
    int
    not
    null,
    TableNumber
    int
    not
    null,
    Turn
    int
    not
    null,
    Date
    datetime
    not
    null,
    WaiterNumber
    int
    null,
    constraint
    Order_Meal_Identification_fk
    foreign
    key
(
    MealIdentification
) references PlayTech.Meal
(
    Identification
),
    constraint Order_Table_Number_fk
    foreign key
(
    TableNumber
) references PlayTech.`Table`
(
    Number
),
    constraint Order_Waiter_Identification_fk
    foreign key
(
    WaiterNumber
) references PlayTech.Waiter
(
    Identification
)
    );

