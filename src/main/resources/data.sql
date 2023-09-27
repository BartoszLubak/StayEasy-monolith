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


INSERT INTO room (room_capacity, cost, room_number, room_type, hotel_id, id)
VALUES (2, 150.00, 101, 1, 1, 1);

INSERT INTO room (room_capacity, cost, room_number, room_type, hotel_id, id)
VALUES (1, 100.00, 102, 0, 1, 2);

INSERT INTO room (room_capacity, cost, room_number, room_type, hotel_id, id)
VALUES (3, 200.00, 201, 2, 2, 3);


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

INSERT INTO reservation_room (room_id, reservation_id)
    VALUE (1, 3);

INSERT INTO reservation_room (room_id, reservation_id)
    VALUE (2, 2);

INSERT INTO reservation_room (room_id, reservation_id)
    VALUE (3, 1);

INSERT INTO extra (id, cost, description)
    VALUE (1, 22, 'Baby Crib');

INSERT INTO extra (id, cost, description)
    VALUE (2, 55, 'Upgrade mini bar');

INSERT INTO extra (id, cost, description)
    VALUE (3, 11, 'Free electric bike');

INSERT INTO extra (id, cost, description)
    VALUE (4, 116, 'Breakfast');

INSERT INTO extra (id, cost, description)
    VALUE (5, 70, 'Parking spot');

INSERT INTO extra (id, cost, description)
    VALUE (6, 64, 'Free pool');

INSERT INTO extras_room (room_id, extras_id)
    VALUE (1, 1);

INSERT INTO extras_room (room_id, extras_id)
    VALUE (1, 6);

INSERT INTO extras_room (room_id, extras_id)
    VALUE (2, 1);

INSERT INTO extras_room (room_id, extras_id)
    VALUE (2, 2);

INSERT INTO extras_room (room_id, extras_id)
    VALUE (2, 3);

INSERT INTO extras_room (room_id, extras_id)
    VALUE (2, 4);

INSERT INTO extras_room (room_id, extras_id)
    VALUE (2, 5);

INSERT INTO extras_room (room_id, extras_id)
    VALUE (2, 6);

INSERT INTO extras_room (room_id, extras_id)
    VALUE (3, 3);

INSERT INTO extras_room (room_id, extras_id)
    VALUE (3, 4);

INSERT INTO extras_room (room_id, extras_id)
    VALUE (3, 5);
