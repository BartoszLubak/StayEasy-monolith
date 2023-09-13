INSERT INTO hotel_owner (type, id, first_name, last_name)
VALUES (0, 1, 'John', 'Doe');

INSERT INTO hotel_owner (type, id, first_name, last_name)
VALUES (1, 2, 'Alice', 'Smith');

INSERT INTO hotel_owner (type, id, first_name, last_name)
VALUES (2, 3, 'Bob', 'Johnson');


INSERT INTO hotel (stars, hotel_owner_id, id, city, country, name, number, street_name)
VALUES (4, 1, 1, 'Gdańsk', 'Polska', 'Grano', '5', 'Pszenna');

INSERT INTO hotel (stars, hotel_owner_id, id, city, country, name, number, street_name)
VALUES (5, 3, 4, 'Gdańsk', 'Polska', 'Królewski', '5', 'Pszenna');

INSERT INTO hotel (stars, hotel_owner_id, id, city, country, name, number, street_name)
VALUES (3, 2, 2, 'Warszawa', 'Polska', 'Centralny Hotel', '10', 'Krakowska');

INSERT INTO hotel (stars, hotel_owner_id, id, city, country, name, number, street_name)
VALUES (5, 3, 3, 'Kraków', 'Polska', 'Krakowska Rezydencja', '15', 'Słowackiego');


INSERT INTO room (availability, beds, cost, room_number, room_type, hotel_id, id)
VALUES (TRUE, 2, 150.00, 101, 1, 1, 1);

INSERT INTO room (availability, beds, cost, room_number, room_type, hotel_id, id)
VALUES (TRUE, 1, 100.00, 102, 0, 1, 2);

INSERT INTO room (availability, beds, cost, room_number, room_type, hotel_id, id)
VALUES (FALSE, 3, 200.00, 201, 2, 2, 3);


INSERT INTO reservation (check_in, check_out, id)
VALUES ('2023-09-10', '2023-09-15', 1);

INSERT INTO reservation (check_in, check_out, id)
VALUES ('2023-10-05', '2023-10-10', 2);

INSERT INTO reservation (check_in, check_out, id)
VALUES ('2023-11-20', '2023-11-25', 3);


INSERT INTO guest (child, type, id, reservation_id, first_name, last_name)
VALUES (FALSE, 0, 1, 1, 'Jan', 'Kowalski');

INSERT INTO guest (child, type, id, reservation_id, first_name, last_name)
VALUES (TRUE, 1, 2, 2, 'Anna', 'Nowak');

INSERT INTO guest (child, type, id, reservation_id, first_name, last_name)
VALUES (FALSE, 2, 3, 3, 'Marek', 'Wiśniewski');

