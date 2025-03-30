-- View for the table Admin
CREATE VIEW admin_user_view AS
SELECT a.id   AS admin_id,
       u.name AS admin_name,
       u.email,
       u.password,
       u.registration_date,
       u.user_type
FROM admin a
         INNER JOIN "User" u ON a.user_id = u.id;


-- View for table Client
CREATE VIEW client_user_view AS
SELECT c.id   AS client_id,
       u.name AS client_name,
       u.email,
       u.password,
       u.registration_date,
       u.user_type
FROM client c
         INNER JOIN "User" u ON c.user_id = u.id;

-- View for the table Organizer
CREATE VIEW organizer_user_view AS
SELECT o.id   AS organizer_id,
       u.name AS organizer_name,
       u.email,
       u.password,
       u.registration_date,
       u.user_type,
       o.company
FROM organizer o
         JOIN "User" u ON o.user_id = u.id;