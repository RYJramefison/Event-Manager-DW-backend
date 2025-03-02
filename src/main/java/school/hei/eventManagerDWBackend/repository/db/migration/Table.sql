CREATE TABLE if not exists "user" (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    mot_de_passe TEXT NOT NULL,
    date_inscription TIMESTAMP DEFAULT NOW()
);


CREATE TABLE if not exists Admin (
        id SERIAL PRIMARY KEY,
        user_id INT,
        CONSTRAINT fk_user_to_admin FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);


CREATE TABLE if not exists Organisateur (
        id SERIAL PRIMARY KEY,
        user_id INT,
        entreprise VARCHAR(255) NOT NULL,
        CONSTRAINT fk_user_to_organisateur FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);


CREATE TABLE if not exists Client (
        id SERIAL PRIMARY KEY,
        user_id INT,
        CONSTRAINT fk_user_to_client FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);


CREATE TABLE if not exists Event (
                id SERIAL PRIMARY KEY,
                organisateur_id INT,
                titre VARCHAR(255) NOT NULL,
                description TEXT,
                date_event TIMESTAMP NOT NULL,
                lieu VARCHAR(255) NOT NULL,
                statut VARCHAR(50) CHECK (statut IN ('brouillon', 'publie', 'annule')) DEFAULT 'brouillon',
                CONSTRAINT fk_organisateur_to_event FOREIGN KEY (organisateur_id) REFERENCES organisateur(id) ON DELETE CASCADE
);


CREATE TABLE if not exists Ticket_Type (
                id SERIAL PRIMARY KEY,
                event_id INT,
                nom VARCHAR(255) NOT NULL,
                prix DECIMAL(10,2) NOT NULL,
                quantite_disponible INT NOT NULL CHECK (quantite_disponible >= 0),
                CONSTRAINT fk_event_to_ticket_type FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE
);


CREATE TABLE if not exists Reservation (
                id SERIAL PRIMARY KEY,
                client_id INT,
                event_id INT,
                date_reservation TIMESTAMP DEFAULT NOW(),
                statut VARCHAR(50) CHECK (statut IN ('confirme', 'annule')) DEFAULT 'confirme',
                CONSTRAINT fk_client_to_reservation FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE,
                CONSTRAINT fk_event_to_reservation FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE
);


CREATE TABLE if not exists Ticket (
                id SERIAL PRIMARY KEY,
                reservation_id INT,
                ticket_type_id INT,
                code_ticket VARCHAR(20) UNIQUE NOT NULL,
                CONSTRAINT fk_reservation_to_ticket FOREIGN KEY (reservation_id) REFERENCES reservation(id) ON DELETE CASCADE,
                CONSTRAINT fk_ticket_type_to_ticket FOREIGN KEY (ticket_type_id) REFERENCES ticket_type(id) ON DELETE CASCADE
);


CREATE TABLE if not exists Paiement (
                id SERIAL PRIMARY KEY,
                reservation_id INT,
                montant DECIMAL(10,2) NOT NULL,
                methode VARCHAR(50) CHECK (methode IN ('carte', 'paypal', 'mobile_money')),
                statut VARCHAR(50) CHECK (statut IN ('en attente', 'paye', 'echoue')) DEFAULT 'en attente',
                date_paiement TIMESTAMP DEFAULT NOW(),
                CONSTRAINT fk_reservation_to_paiement FOREIGN KEY (reservation_id) REFERENCES reservation(id) ON DELETE CASCADE
);
