INSERT INTO "user" (name, email, password) VALUES
        ('Alice Dupont', 'alice.dupont@gmail.com', 'motdepasse1'),
        ('Bob Martin', 'bob.martin@gmail.com', 'motdepasse2'),
        ('Claire Lefevre', 'claire.lefevre@gmail.com', 'motdepasse3');

INSERT INTO Admin (user_id) VALUES
                (1),
                (2);

INSERT INTO organizer (user_id, company) VALUES
                (1, 'Opera song'),
                (2, 'Festivals & Co.');

INSERT INTO customer (user_id) VALUES
                (2),
                (3);

INSERT INTO Event (organizer_id, title, description, event_date, location, statut) VALUES
                    (1, 'Concert de Jazz', 'Un concert de jazz exceptionnel.', '2025-06-15 20:00:00', 'Salle de Concert', 'published'),
                    (2, 'Festival de Musique', 'Un festival de musique variee.', '2025-07-20 10:00:00', 'Parc Central', 'draft');

-- Insertion dans la table Ticket_Type
INSERT INTO TicketType (event_id, name, price, available_quantity) VALUES
                    (1, 'Billet Standard', 30.00, 100),
                    (1, 'Billet VIP', 100.00, 50),
                    (2, 'Billet Journee', 20.00, 200);

-- Insertion dans la table Reservation
INSERT INTO Reservation (customer_id, event_id, reservation_date, statut) VALUES
                    (1, 1, NOW(), 'confirmed'),
                    (2, 2, NOW(), 'confirmed');

-- Insertion dans la table Ticket
INSERT INTO Ticket (reservation_id, ticket_type_id, ticket_code) VALUES
                    (1, 1, 'TICKET001'),
                    (1, 2, 'TICKET002'),
                    (2, 3, 'TICKET003');

-- Insertion dans la table Paiement
INSERT INTO Payment (reservation_id, amount, method, statut) VALUES
                    (1, 130.00, 'card', 'paid'),
                    (2, 20.00, 'paypal', 'pending');
