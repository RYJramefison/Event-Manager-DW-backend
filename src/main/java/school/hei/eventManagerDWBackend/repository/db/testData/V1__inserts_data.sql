-- ===========================
-- 1. Insert Statements for the "User" Table
-- ===========================

INSERT INTO "User" (name, email, password) VALUES
                                               ('Alice Johnson', 'alice.johnson@example.com', 'password123'),
                                               ('Bob Smith', 'bob.smith@example.com', 'password123'),
                                               ('Charlie Brown', 'charlie.brown@example.com', 'password123'),
                                               ('Diana Prince', 'diana.prince@example.com', 'password123'),
                                               ('Ethan Hunt', 'ethan.hunt@example.com', 'password123');

-- ===========================
-- 2. Insert Statements for the "Admin" Table
-- ===========================

INSERT INTO Admin (user_id, admin_name) VALUES
                                            (1, 'Alice Admin'),
                                            (2, 'Bob Admin'),
                                            (3, 'Charlie Admin'),
                                            (4, 'Diana Admin'),
                                            (5, 'Ethan Admin');

-- ===========================
-- 3. Insert Statements for the "Organizer" Table
-- ===========================

INSERT INTO Organizer (user_id, company) VALUES
                                             (1, 'Alice Events Co.'),
                                             (2, 'Bobs Event Planning'),
(3, 'Charlie Productions'),
(4, 'Dianas Events'),
                                             (5, 'Ethans Organizing');

-- ===========================
-- 4. Insert Statements for the "Client" Table
-- ===========================

INSERT INTO Client (user_id) VALUES
(1),
(2),
(3),
(4),
(5);

-- ===========================
-- 5. Insert Statements for the "Event" Table
-- ===========================

INSERT INTO Event (organizer_id, title, description, event_date, location, status) VALUES
(1, 'Summer Music Festival', 'An outdoor music festival featuring various artists.', '2025-06-15 18:00:00', 'Central Park', 'PUBLISHED'),
(2, 'Tech Conference 2025', 'A conference for tech enthusiasts and professionals.', '2025-08-20 09:00:00', 'Convention Center', 'PUBLISHED'),
(3, 'Art Gallery Opening', 'Opening night for a new art gallery.', '2025-05-10 19:00:00', 'Downtown Art District', 'PUBLISHED'),
(4, 'Food Truck Rally', 'A gathering of the best food trucks in the city.', '2025-07-25 11:00:00', 'City Square', 'DRAFT'),
(5, 'Charity Run', 'A charity run to raise funds for local organizations.', '2025-09-15 07:00:00', 'City Park', 'DRAFT');

-- ===========================
-- 6. Insert Statements for the "TicketType" Table
-- ===========================

INSERT INTO TicketType (event_id, name, price, available_quantity) VALUES
(1, 'VIP', 150.00, 50),
(1, 'Standard', 75.00, 200),
(2, 'Early Bird', 100.00, 100),
(2, 'Standard', 150.00, 150),
(3, 'General Admission', 20.00, 300);

-- ===========================
-- 7. Insert Statements for the "Reservation" Table
-- ===========================

INSERT INTO Reservation (client_id, event_id, reservation_date, status) VALUES
(1, 1, NOW(), 'CONFIRMED'),
(2, 2, NOW(), 'CONFIRMED'),
(3, 3, NOW(), 'CANCELED'),
(4, 4, NOW(), 'CONFIRMED'),
(5, 5, NOW(), 'CONFIRMED');

-- ===========================
-- 8. Insert Statements for the "Ticket" Table
-- ===========================

INSERT INTO Ticket (reservation_id, ticket_type_id, ticket_code) VALUES
(1, 1, 'TICKET001'),
(2, 3, 'TICKET002'),
(4, 2, 'TICKET003'),
(5, 4, 'TICKET004'),
(1, 2, 'TICKET005');

-- ===========================
-- 9. Insert Statements for the "Payment" Table
-- ===========================

INSERT INTO Payment (reservation_id, amount, method, status) VALUES
(1, 150.00, 'CARD', 'PAID'),
(2, 100.00, 'PAYPAL', 'PENDING'),
(3, 20.00, 'MOBILE_MONEY', 'FAILED'),
(4, 75.00, 'CARD', 'PAID'),
(5, 150.00, 'PAYPAL', 'PENDING');