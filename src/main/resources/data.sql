INSERT INTO hotel_owner (type, id, first_name, last_name)
VALUES (0, 1, 'John', 'Doe');

INSERT INTO hotel_owner (type, id, first_name, last_name)
VALUES (1, 2, 'Alice', 'Smith');

INSERT INTO hotel_owner (type, id, first_name, last_name)
VALUES (2, 3, 'Bob', 'Johnson');


INSERT INTO hotel (stars, hotel_owner_id, id, city, country, name, number, street_name)
VALUES (4, 1, 1, 'Gdańsk', 'Polska', 'Grano Hotel', '5', 'Pszenna');

INSERT INTO hotel (stars, hotel_owner_id, id, city, country, name, number, street_name)
VALUES (3, 2, 2, 'Warszawa', 'Polska', 'Centralny Hotel', '10', 'Krakowska');

INSERT INTO hotel (stars, hotel_owner_id, id, city, country, name, number, street_name)
VALUES (5, 3, 3, 'Kraków', 'Polska', 'Krakowska Rezydencja', '15', 'Słowackiego');


INSERT INTO room (availability, beds, cost, guests, room_number, room_type, hotel_id, id)
VALUES (TRUE, 2, 150.00, 2, 101, 1, 1, 1);

INSERT INTO room (availability, beds, cost, guests, room_number, room_type, hotel_id, id)
VALUES (TRUE, 1, 100.00, 1, 102, 0, 1, 2);

INSERT INTO room (availability, beds, cost, guests, room_number, room_type, hotel_id, id)
VALUES (FALSE, 3, 200.00, 3, 201, 2, 2, 3);


INSERT INTO reservation (check_in, check_out, room_id, uuid)
VALUES ('2023-09-10', '2023-09-15', 1, 'c9e0e56d-7ebf-4e57-a318-4d0e9de66c57');

INSERT INTO reservation (check_in, check_out, room_id, uuid)
VALUES ('2023-10-05', '2023-10-10', 2, '0b8d15a4-07d1-430b-99d5-924f0427c405');

INSERT INTO reservation (check_in, check_out, room_id, uuid)
VALUES ('2023-11-20', '2023-11-25', 3, '9f08d3b1-2df0-4b8e-b09f-4f792c5b7b65');


INSERT INTO guest (child, type, id, reservation_id, first_name, last_name)
VALUES (FALSE, 0, 1, 'c9e0e56d-7ebf-4e57-a318-4d0e9de66c57', 'Jan', 'Kowalski');

INSERT INTO guest (child, type, id, reservation_id, first_name, last_name)
VALUES (TRUE, 1, 2, '0b8d15a4-07d1-430b-99d5-924f0427c405', 'Anna', 'Nowak');

INSERT INTO guest (child, type, id, reservation_id, first_name, last_name)
VALUES (FALSE, 2, 3, '9f08d3b1-2df0-4b8e-b09f-4f792c5b7b65', 'Marek', 'Wiśniewski');


