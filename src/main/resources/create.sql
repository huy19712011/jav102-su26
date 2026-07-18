-- Seed data, run automatically after Hibernate's drop-and-create schema generation
-- (jakarta.persistence.sql-load-script-source in persistence.xml).
-- NOTE: tables/columns come from the JPA entities, so this file holds INSERT only.
-- Hibernate runs this with the single-line extractor: one statement per line, no GO,
-- no multi-line statements. Column names match the entity field names (e.g. fullName).

-- Categories (from polycoffee.sql)
SET IDENTITY_INSERT categories ON;
INSERT INTO categories (id, name, active) VALUES (1, 'Cafe', 1);
INSERT INTO categories (id, name, active) VALUES (2, 'Tra', 1);
INSERT INTO categories (id, name, active) VALUES (8, 'Nuoc ep', 1);
INSERT INTO categories (id, name, active) VALUES (10, 'Sinh to', 1);
SET IDENTITY_INSERT categories OFF;

-- Users (role bit 1/0 mapped to the Role enum: 1 -> ADMIN, 0 -> CUSTOMER)
SET IDENTITY_INSERT users ON;
INSERT INTO users (id, email, password, fullName, phone, role, active) VALUES (1, 'admin@gmail.com', '123', 'Quan tri', '0919123123', 'ADMIN', 1);
INSERT INTO users (id, email, password, fullName, phone, role, active) VALUES (2, 'teonv@gmail.com', '123', 'Khach hang', '0907828123', 'CUSTOMER', 1);
SET IDENTITY_INSERT users OFF;

-- Drinks (category_id references categories seeded above; price is decimal)
SET IDENTITY_INSERT drinks ON;
INSERT INTO drinks (id, category_id, name, description, image, price, active) VALUES (1, 1, 'Ca phe den', 'Ca phe phin truyen thong', NULL, 25000, 1);
INSERT INTO drinks (id, category_id, name, description, image, price, active) VALUES (2, 1, 'Ca phe sua', 'Ca phe sua da', NULL, 30000, 1);
INSERT INTO drinks (id, category_id, name, description, image, price, active) VALUES (3, 1, 'Bac xiu', 'Nhieu sua, it ca phe', NULL, 35000, 1);
INSERT INTO drinks (id, category_id, name, description, image, price, active) VALUES (4, 2, 'Tra dao', 'Tra dao cam sa', NULL, 40000, 1);
INSERT INTO drinks (id, category_id, name, description, image, price, active) VALUES (5, 2, 'Tra vai', 'Tra vai hat sen', NULL, 40000, 1);
INSERT INTO drinks (id, category_id, name, description, image, price, active) VALUES (6, 8, 'Nuoc ep cam', 'Cam tuoi nguyen chat', NULL, 45000, 1);
INSERT INTO drinks (id, category_id, name, description, image, price, active) VALUES (7, 10, 'Sinh to bo', 'Sinh to bo sua tuoi', NULL, 50000, 1);
SET IDENTITY_INSERT drinks OFF;

-- Bills (user_id references users; status uses the BillStatus enum; total matches its line items)
SET IDENTITY_INSERT bills ON;
INSERT INTO bills (id, user_id, code, createdAt, total, status) VALUES (1, 2, 'HD001', '2026-07-15 09:15:00', 80000, 'PAID');
INSERT INTO bills (id, user_id, code, createdAt, total, status) VALUES (2, 2, 'HD002', '2026-07-16 14:20:00', 120000, 'PENDING');
SET IDENTITY_INSERT bills OFF;

-- Bill line items (price is the line total = quantity * drink price)
SET IDENTITY_INSERT bills_drinks ON;
INSERT INTO bills_drinks (id, bill_id, drink_id, quantity, price) VALUES (1, 1, 1, 2, 50000);
INSERT INTO bills_drinks (id, bill_id, drink_id, quantity, price) VALUES (2, 1, 2, 1, 30000);
INSERT INTO bills_drinks (id, bill_id, drink_id, quantity, price) VALUES (3, 2, 4, 3, 120000);
SET IDENTITY_INSERT bills_drinks OFF;
