CREATE TABLE if not exists "user" (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) UNIQUE NOT NULL,
   password TEXT NOT NULL,
   registration_date TIMESTAMP DEFAULT NOW()
);


CREATE TABLE if not exists Admin (
        id SERIAL PRIMARY KEY,
        user_id INT,
        CONSTRAINT fk_user_to_admin FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);


CREATE TABLE if not exists organizer (
        id SERIAL PRIMARY KEY,
        user_id INT,
        company VARCHAR(255) NOT NULL,
        CONSTRAINT fk_user_to_organizer FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);


CREATE TABLE if not exists customer (
        id SERIAL PRIMARY KEY,
        user_id INT,
        CONSTRAINT fk_user_to_customer FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);


CREATE TABLE if not exists Event (
                id SERIAL PRIMARY KEY,
                Organizer_id INT,
                title VARCHAR(255) NOT NULL,
                description TEXT,
                event_date TIMESTAMP NOT NULL,
                location VARCHAR(255) NOT NULL,
                statut VARCHAR(50) CHECK (statut IN ('draft', 'published', 'canceled')) DEFAULT 'draft',
                CONSTRAINT fk_organizer_to_event FOREIGN KEY (organizer_id) REFERENCES organizer(id) ON DELETE CASCADE
);


CREATE TABLE if not exists TicketType (
                id SERIAL PRIMARY KEY,
                event_id INT,
                name VARCHAR(255) NOT NULL,
                price DECIMAL(10,2) NOT NULL,
                available_quantity INT NOT NULL CHECK (available_quantity >= 0),
                CONSTRAINT fk_event_to_ticketType FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE
);


CREATE TABLE if not exists Reservation (
                id SERIAL PRIMARY KEY,
                customer_id INT,
                event_id INT,
                reservation_date TIMESTAMP DEFAULT NOW(),
                statut VARCHAR(50) CHECK (statut IN ('confirmed', 'canceled')) DEFAULT 'confirmed',
                CONSTRAINT fk_customer_to_reservation FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE,
                CONSTRAINT fk_event_to_reservation FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE
);


CREATE TABLE if not exists Ticket (
                id SERIAL PRIMARY KEY,
                reservation_id INT,
                ticket_type_id INT,
                ticket_code VARCHAR(20) UNIQUE NOT NULL,
                CONSTRAINT fk_reservation_to_ticket FOREIGN KEY (reservation_id) REFERENCES reservation(id) ON DELETE CASCADE,
                CONSTRAINT fk_ticket_type_to_ticket FOREIGN KEY (ticket_type_id) REFERENCES ticketType(id) ON DELETE CASCADE
);


CREATE TABLE if not exists Payment (
                id SERIAL PRIMARY KEY,
                reservation_id INT,
                amount DECIMAL(10,2) NOT NULL,
                method VARCHAR(50) CHECK (method IN ('card', 'paypal', 'mobile_money')),
                statut VARCHAR(50) CHECK (statut IN ('pending', 'paid', 'failed')) DEFAULT 'pending',
                payment_date TIMESTAMP DEFAULT NOW(),
                CONSTRAINT fk_reservation_to_payment FOREIGN KEY (reservation_id) REFERENCES reservation(id) ON DELETE CASCADE
);
