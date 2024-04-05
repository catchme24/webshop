create sequence id_for_customers start 1 increment 1;
create sequence id_for_orders start 1 increment 1;
create sequence id_for_products start 1 increment 1;
create sequence id_for_order_products start 1 increment 1;
create sequence id_for_delivery_list start 1 increment 1;

create table customers (
                           customer_id INTEGER PRIMARY KEY DEFAULT nextval('id_for_customers'),
                           first_name VARCHAR NOT NULL,
                           last_name VARCHAR NOT NULL,
                           phone_number VARCHAR NOT NULL,
                           city VARCHAR NOT NULL,
                           street VARCHAR NOT NULL,
                           house INTEGER NOT NULL,
                           appartament INTEGER NOT NULL,
                           username VARCHAR NOT NULL,
                           password VARCHAR NOT NULL,
                           role VARCHAR NOT NULL
);

create table orders (
                        order_id INTEGER PRIMARY KEY DEFAULT nextval('id_for_orders'),
                        customer_id INTEGER NOT NULL,
                        date_get DATE,
                        FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);

create table products (
                          product_id INTEGER PRIMARY KEY DEFAULT nextval('id_for_products'),
                          product_name VARCHAR NOT NULL,
                          description VARCHAR NOT NULL,
                          price NUMERIC NOT NULL,
                          image_url varchar NOT NULL
);

create table order_products (
                                order_product_id INTEGER PRIMARY KEY DEFAULT nextval('id_for_order_products'),
                                product_id INTEGER NOT NULL,
                                order_id INTEGER NOT NULL,
                                quantity INTEGER NOT NULL,
                                FOREIGN KEY (product_id) REFERENCES products (product_id),
                                FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

create table delivery_list (
                               delivery_id INTEGER PRIMARY KEY DEFAULT nextval('id_for_delivery_list'),
                               date_arrived DATE NOT NULL,
                               payment_method VARCHAR NOT NULL,
                               order_id INTEGER NOT NULL,
                               FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

INSERT INTO products(product_name, description, price, image_url)
VALUES ('Карась',
        'Отличная рыба',
        100,
        'https://avatars.mds.yandex.net/i?id=1a7aecaa37e7a61bbb92bc499be5829a1d1b54fe-8217704-images-thumbs&n=13'),
       ('Лещ',
        'Вкусная рыба',
        200,
        'https://pofoto.club/uploads/posts/2022-07/1659036584_57-pofoto-club-p-karp-rechnaya-riba-69.jpg'),
       ('Подлещик',
        'Очень вкусная рыба',
        300,
        'https://avatars.mds.yandex.net/i?id=7711ff3c0ea4d18ff71c9b95af321e7324a356fd-10878270-images-thumbs&n=13'),
       ('Акула',
        'Очень редкая и уникальная рыба',
        1000,
        'https://cliparting.com/wp-content/uploads/2018/03/cartoon-shark-2018-18.png'),
       ('Щука',
        'Хорошая рыбка',
        350,
        'https://ic.pics.livejournal.com/iskosmosa/63238113/55577/55577_900.jpg'),
       ('Судак',
        'Классическая рыба',
        250,
        'https://bober-med.ru/wp-content/uploads/2022/09/gos.jpg');

INSERT INTO customers(first_name, last_name, phone_number, city,street, house, appartament, username, password, role)
VALUES  ( 'Иван', 'Иванов', '555444', 'Москва', 'Баныкина', '12', '121', 'admin', '$2a$10$9Tx/f94Cp1npqP5R4fHBFuKOZzPSiR9Wslx4Yvi9H5QpRSEBMDSD2', 'ADMIN'),
        ( 'Андрей', 'Шарфов', '123123', 'Новосибирск', 'Юбилейная', '17', '54', 'user', '$2a$10$IJQ4fzlG3Ix1bCi7if7toe61YAR/iGnpRe6PqfO4FzcM8l1oOZUJi', 'USER');

INSERT INTO orders(customer_id)
VALUES  ( (SELECT customer_id FROM customers WHERE first_name LIKE 'Иван')),
        ( (SELECT customer_id FROM customers WHERE first_name LIKE 'Андрей'));