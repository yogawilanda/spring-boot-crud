INSERT INTO roles (alias, name)
VALUES
    ('admin', 'Administrator'),
    ('user', 'Regular User')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO categories (name, slug, description)
VALUES
    ('Electronics', 'electronics', 'Electronic devices and accessories'),
    ('Books', 'books', 'Books and learning materials')
ON DUPLICATE KEY UPDATE description = VALUES(description);

INSERT INTO products (name, slug, description, price, status, category_id)
SELECT
    'Wireless Mouse',
    'wireless-mouse',
    'Demo product for API showcase',
    149000.00,
    'published',
    c.id
FROM categories c
WHERE c.slug = 'electronics'
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    description = VALUES(description),
    price = VALUES(price),
    status = VALUES(status),
    category_id = VALUES(category_id);

INSERT INTO products (name, slug, description, price, status, category_id)
SELECT
    'Spring Boot Starter Guide',
    'spring-boot-starter-guide',
    'Demo book product for API showcase',
    89000.00,
    'published',
    c.id
FROM categories c
WHERE c.slug = 'books'
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    description = VALUES(description),
    price = VALUES(price),
    status = VALUES(status),
    category_id = VALUES(category_id);
