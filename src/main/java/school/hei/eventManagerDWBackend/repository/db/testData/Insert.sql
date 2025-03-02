INSERT INTO "user" (nom, email, mot_de_passe) VALUES
        ('Alice Dupont', 'alice.dupont@gmail.com', 'motdepasse1'),
        ('Bob Martin', 'bob.martin@gmail.com', 'motdepasse2'),
        ('Claire Lefevre', 'claire.lefevre@gmail.com', 'motdepasse3');

INSERT INTO Admin (user_id) VALUES
                (1),
                (2);

INSERT INTO Organisateur (user_id, entreprise) VALUES
                (1, 'Opera song'),
                (2, 'Festivals & Co.');

INSERT INTO Client (user_id) VALUES
                (2),
                (3);

INSERT INTO Event (organisateur_id, titre, description, date_event, lieu, statut) VALUES
                    (1, 'Concert de Jazz', 'Un concert de jazz exceptionnel.', '2025-06-15 20:00:00', 'Salle de Concert', 'publie'),
                    (2, 'Festival de Musique', 'Un festival de musique variee.', '2025-07-20 10:00:00', 'Parc Central', 'brouillon');

-- Insertion dans la table Ticket_Type
INSERT INTO Ticket_Type (event_id, nom, prix, quantite_disponible) VALUES
                    (1, 'Billet Standard', 30.00, 100),
                    (1, 'Billet VIP', 100.00, 50),
                    (2, 'Billet Journee', 20.00, 200);

-- Insertion dans la table Reservation
INSERT INTO Reservation (client_id, event_id, date_reservation, statut) VALUES
                    (1, 1, NOW(), 'confirme'),
                    (2, 2, NOW(), 'confirme');

-- Insertion dans la table Ticket
INSERT INTO Ticket (reservation_id, ticket_type_id, code_ticket) VALUES
                    (1, 1, 'TICKET001'),
                    (1, 2, 'TICKET002'),
                    (2, 3, 'TICKET003');

-- Insertion dans la table Paiement
INSERT INTO Paiement (reservation_id, montant, methode, statut) VALUES
                    (1, 130.00, 'carte', 'paye'),
                    (2, 20.00, 'paypal', 'en attente');
