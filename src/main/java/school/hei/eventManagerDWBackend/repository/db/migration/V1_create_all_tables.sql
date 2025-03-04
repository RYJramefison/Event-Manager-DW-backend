-- ===========================
-- 1. "User"s Table
-- ===========================

CREATE TABLE if not exists "User"
(
    id                SERIAL PRIMARY KEY,
    name              VARCHAR(255)        NOT NULL,
    email             VARCHAR(255) UNIQUE NOT NULL,
    password          TEXT                NOT NULL,
    registration_date TIMESTAMP DEFAULT NOW()
);

-- ===========================
-- 2. Separation (these three inherit from "User" to simplify management)
-- ===========================

CREATE TABLE if not exists Admin
(
    id         SERIAL PRIMARY KEY,
    user_id    INT UNIQUE   NOT NULL REFERENCES "User" (id) ON DELETE CASCADE,
    admin_name VARCHAR(100) NOT NULL
);

CREATE TABLE if not exists Organizer
(
    id      SERIAL PRIMARY KEY,
    user_id INT UNIQUE   NOT NULL REFERENCES "User" (id) ON DELETE CASCADE,
    company VARCHAR(255) NOT NULL
);

CREATE TABLE if not exists Client
(
    id      SERIAL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL REFERENCES "User" (id) ON DELETE CASCADE
);

-- ===========================
-- 3. Events Table
-- ===========================

CREATE TABLE if not exists Event
(
    id           SERIAL PRIMARY KEY,
    organizer_id INT          NOT NULL REFERENCES Organizer (id) ON DELETE CASCADE,
    title        VARCHAR(255) NOT NULL,
    description  TEXT,
    event_date   TIMESTAMP    NOT NULL,
    location     VARCHAR(255) NOT NULL,
    status       VARCHAR(50) CHECK (status IN ('draft', 'published', 'canceled')) DEFAULT 'draft'
);


-- ===========================
-- 4. Ticket Management
-- ===========================

-- Ticket types for an event (VIP, Standard, Early Bird, .)
CREATE TABLE if not exists TicketType
(
    id                 SERIAL PRIMARY KEY,
    event_id           INT            NOT NULL REFERENCES Event (id) ON DELETE CASCADE,
    name               VARCHAR(255)   NOT NULL,
    price              DECIMAL(10, 2) NOT NULL,
    available_quantity INT            NOT NULL CHECK (available_quantity >= 0)
);

-- ===========================
-- 5. Ticket Reservations
-- ===========================

CREATE TABLE if not exists Reservation
(
    id               SERIAL PRIMARY KEY,
    client_id        INT NOT NULL REFERENCES Client (id) ON DELETE CASCADE,
    event_id         INT NOT NULL REFERENCES Event (id) ON DELETE CASCADE,
    reservation_date TIMESTAMP                                               DEFAULT NOW(),
    status           VARCHAR(50) CHECK (status IN ('confirmed', 'canceled')) DEFAULT 'confirmed'
);

-- Ticket purchased by a client
CREATE TABLE if not exists Ticket
(
    id             SERIAL PRIMARY KEY,
    reservation_id INT                NOT NULL REFERENCES Reservation (id) ON DELETE CASCADE,
    ticket_type_id INT                NOT NULL REFERENCES TicketType (id) ON DELETE CASCADE,
    ticket_code    VARCHAR(20) UNIQUE NOT NULL
);

-- ===========================
-- 6. Payments
-- ===========================

CREATE TABLE if not exists Payment
(
    id             SERIAL PRIMARY KEY,
    reservation_id INT            NOT NULL REFERENCES Reservation (id) ON DELETE CASCADE,
    amount         DECIMAL(10, 2) NOT NULL,
    method         VARCHAR(50) CHECK (method IN ('card', 'paypal', 'mobile_money')),
    status         VARCHAR(50) CHECK (status IN ('pending', 'paid', 'failed')) DEFAULT 'pending',
    payment_date   TIMESTAMP                                                   DEFAULT NOW()
);
