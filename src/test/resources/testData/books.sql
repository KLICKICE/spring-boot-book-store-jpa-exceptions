DELETE FROM cart_items;
DELETE FROM books_categories;
DELETE FROM books;
DELETE FROM categories;

INSERT INTO categories (id, name) VALUES (1, 'Fiction');
INSERT INTO categories (id, name) VALUES (2, 'Fantasy');

INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (100, 'Test Book', 'Test Author', '978-1-23-456789-0', 199.99, 'Description', 'cover.jpg');

INSERT INTO books_categories (book_id, category_id) VALUES (100, 1);
