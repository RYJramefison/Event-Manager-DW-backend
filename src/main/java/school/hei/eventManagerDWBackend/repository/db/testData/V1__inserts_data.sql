-- ===========================
-- 1. Insert Statements for the "User" Table
-- ===========================

INSERT INTO "User" (name, email, password, user_type) VALUES
                                               ('Alice Johnson', 'alice.johnson@example.com', 'password123', 'client'),
                                               ('Bob Smith', 'bob.smith@example.com', 'password123', 'admin'),
                                               ('Charlie Brown', 'charlie.brown@example.com', 'password123', 'organizer'),
                                               ('Diana Prince', 'diana.prince@example.com', 'password123', 'client'),
                                               ('Ethan Hunt', 'ethan.hunt@example.com', 'password123', 'client');

-- ===========================
-- 2. Insert Statements for the "Admin" Table
-- ===========================

INSERT INTO Admin (user_id, admin_name) VALUES
                                            (2, 'Bob Admin');

-- ===========================
-- 3. Insert Statements for the "Organizer" Table
-- ===========================

INSERT INTO Organizer (user_id, company) VALUES
                                             (3, 'Bobs Event Planning');

-- ===========================
-- 4. Insert Statements for the "Client" Table
-- ===========================

INSERT INTO Client (user_id) VALUES
(1),
(4),
(5);

-- ===========================
-- 5. Insert Statements for the "Event" Table
-- ===========================

INSERT INTO Event (organizer_id, title, description, event_date, location, status) VALUES
(1, 'Art Gallery Opening', 'Opening night for a new art gallery.', '2025-05-10 19:00:00', 'Downtown Art District', 'PUBLISHED');

-- ===========================
-- 6. Insert Statements for the "TicketType" Table
-- ===========================

INSERT INTO TicketType (event_id, name, price, available_quantity) VALUES
(1, 'VIP', 150.00, 50),
(1, 'Standard', 75.00, 200),
(1, 'Early Bird', 100.00, 100),
(1, 'Standard', 150.00, 150),
(1, 'General Admission', 20.00, 300);

-- ===========================
-- 7. Insert Statements for the "Reservation" Table
-- ===========================

INSERT INTO Reservation (client_id, event_id, reservation_date, status) VALUES
(1, 1, NOW(), 'CONFIRMED'),
(2, 1, NOW(), 'CONFIRMED'),
(3, 1, NOW(), 'CANCELED');

-- ===========================
-- 8. Insert Statements for the "Ticket" Table
-- ===========================

INSERT INTO Ticket (reservation_id, ticket_type_id, ticket_code) VALUES
(1, 1, 'TICKET001'),
(2, 3, 'TICKET002'),
(3, 2, 'TICKET003');

-- ===========================
-- 9. Insert Statements for the "Payment" Table
-- ===========================

INSERT INTO Payment (reservation_id, amount, method, status) VALUES
(1, 150.00, 'CARD', 'PAID'),
(2, 100.00, 'PAYPAL', 'PENDING'),
(3, 20.00, 'MOBILE_MONEY', 'FAILED');