DELETE FROM cart_items;
DELETE FROM books_categories;
DELETE FROM books;
DELETE FROM categories;

INSERT INTO categories (id, name, description)
VALUES (200, 'CategoryToDelete', 'To be deleted');
