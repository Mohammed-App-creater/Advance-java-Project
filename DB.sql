
use Oline_Shopping_Platform_war_exploded;

-- Table to store product information
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    image_url VARCHAR(500),
    price DECIMAL(10, 2) NOT NULL,
    discount DECIMAL(5, 2) DEFAULT 0.00,
    category_id INT NOT NULL,
    stock INT DEFAULT 0,
    seller_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- Table to store user information (customers, sellers, managers)
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role ENUM('customer', 'seller', 'manager') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table to store order information
CREATE TABLE Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    shipping_address TEXT,
    status ENUM('pending', 'shipped', 'delivered') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Table to store order items (products within an order)
CREATE TABLE Order_Items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
);

-- Table to store payment information for orders
CREATE TABLE Payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    bank_name VARCHAR(255) NOT NULL, -- Store the bank name (e.g., Bank A, Bank B, etc.)
    payment_status ENUM('pending', 'completed', 'canceled') DEFAULT 'pending',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE
);


-- Table to store shipping information for orders
CREATE TABLE Shipping (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    tracking_number VARCHAR(100),
    shipping_status ENUM('shipped', 'in_transit', 'delivered') DEFAULT 'shipped',
    shipping_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    delivery_date TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE
);

-- Table to store inventory information (quantity available for each product from each seller)
CREATE TABLE Inventory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    seller_id INT,
    quantity_available INT NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE categories (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Ratings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    customer_id INT,
    rating DECIMAL(3, 2) NOT NULL,
    review_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Cart (
    id INT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for each cart item
    user_id INT NOT NULL,              -- References the user who owns the cart
    product_id INT NOT NULL,           -- References the product in the cart
    quantity INT NOT NULL,             -- Quantity of the product in the cart
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- When the product was added to the cart
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Last update timestamp
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE, -- Link to Users table
    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE -- Link to Products table
);

CREATE TABLE Shipping_Addresses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);






select * from users;
SELECT * FROM categories;
SELECT * FROM products;
SELECT * FROM categories WHERE name = 'Electronics';


SELECT p.product_id, p.name, p.description, p.price, p.discount, p.stock, p.image_url, c.name AS category_name
FROM Products p
JOIN categories c ON p.category_id = c.id
WHERE c.name = 'Smartphones'
LIMIT 16 OFFSET 0 ;

SELECT p.product_id, p.name, p.description, p.price, p.discount, p.stock, p.image_url, c.name AS category_name
FROM Products p
JOIN categories c ON p.category_id = c.id
ORDER BY c.name -- To sort by category name (optional)
LIMIT 16 OFFSET 0;


-- Insert additional categories (for the new products)
INSERT INTO categories (name) VALUES 
('Smartphones'), 
('Handbags'), 
('Skincare'), 
('Toys');  

-- Insert additional users (for new products)
INSERT INTO Users (username, password, email, role) VALUES
('customer3', 'password123', 'customer3@example.com', 'customer'),
('seller3', 'password123', 'seller3@example.com', 'seller');  -- New seller for these products

-- Insert additional products
INSERT INTO products (name, description, image_url, price, discount, category_id, stock, seller_id) VALUES
('iPhone 4', 'Apple iPhone 4 with 8GB storage and 3.5-inch display. A classic smartphone with iOS features.', 
'https://cdn.pixabay.com/photo/2013/07/12/18/39/smartphone-153650_1280.png', 
99.99, 0.00, (SELECT id FROM categories WHERE name = 'Smartphones'), 50, 
(SELECT id FROM Users WHERE email = 'seller5@example.com')),  -- iPhone 4 product

('iPhone 16', 'Latest iPhone 16 with advanced features and stunning display.', 
'https://www.apple.com/newsroom/images/2024/09/apple-introduces-iphone-16-and-iphone-16-plus/article/Apple-iPhone-16-hero-240909_inline.jpg.large.jpg', 
1099.99, 10.00, (SELECT id FROM categories WHERE name = 'Smartphones'), 30, 
(SELECT id FROM Users WHERE email = 'seller5@example.com')),  -- iPhone 16 product

('Face Yogi Weekender Duffle', 'Stylish and spacious weekender duffle bag for women.', 
'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSS0VakmkSnsui_X4mL_XKn9_ZJF4VBZ90gYutQIWrElhQqcc4u94Dt_aYJMkLZvCm-dW2KKVCNJ80gEKAkPfMDJNlaIfFEBhTJcUYX88biShOk_ezaBo2RaSOA20nn1u-1kLj_vQ&usqp=CAc', 
149.99, 15.00, (SELECT id FROM categories WHERE name = 'Handbags'), 25, 
(SELECT id FROM Users WHERE email = 'seller5@example.com')),  -- Handbag product

('Balancing Spray Toner', 'Refreshing toner for balanced skin, suitable for all skin types.', 
'https://b1780171.smushcdn.com/1780171/wp-content/uploads/2024/03/FYM_2020_eCom_ShopUp_SkinCare_Toner_Hero_V1_1080x1080.jpg?lossy=0&strip=1&webp=0', 
29.99, 5.00, (SELECT id FROM categories WHERE name = 'Skincare'), 40, 
(SELECT id FROM Users WHERE email = 'seller5@example.com')),  -- Skincare product

('Teddy Bear', 'Soft and cuddly teddy bear for kids.', 
'https://www.obdesigns.com.au/cdn/shop/files/SP_HoneyBear_1_800x.jpg?v=1684487044', 
19.99, 0.00, (SELECT id FROM categories WHERE name = 'Toys'), 100, 
(SELECT id FROM Users WHERE email = 'seller5@example.com'));  -- Toy product

-- Insert additional inventory for the new products
INSERT INTO Inventory (product_id, seller_id, quantity_available) VALUES
((SELECT product_id FROM products WHERE name = 'iPhone 4'), (SELECT id FROM Users WHERE email = 'seller5@example.com'), 50),
((SELECT product_id FROM products WHERE name = 'iPhone 16'), (SELECT id FROM Users WHERE email = 'seller5@example.com'), 30),
((SELECT product_id FROM products WHERE name = 'Face Yogi Weekender Duffle'), (SELECT id FROM Users WHERE email = 'seller5@example.com'), 25),
((SELECT product_id FROM products WHERE name = 'Balancing Spray Toner'), (SELECT id FROM Users WHERE email = 'seller5@example.com'), 40),
((SELECT product_id FROM products WHERE name = 'Teddy Bear'), (SELECT id FROM Users WHERE email = 'seller5@example.com'), 100);

-- Insert additional orders
INSERT INTO Orders (customer_id, total_amount, shipping_address, status) VALUES
(1, 1199.98, '123 Oak St, Cityville, Country', 'pending'),
(1, 169.99, '456 Pine St, Townsville, Country', 'shipped');

-- Insert additional order items
INSERT INTO Order_Items (order_id, product_id, quantity, price) VALUES
((SELECT id FROM Orders WHERE customer_id = 1), (SELECT product_id FROM products WHERE name = 'iPhone 16'), 1, 1099.99),
((SELECT id FROM Orders WHERE customer_id = 3), (SELECT product_id FROM products WHERE name = 'Face Yogi Weekender Duffle'), 1, 149.99),
((SELECT id FROM Orders WHERE customer_id = 5), (SELECT product_id FROM products WHERE name = 'Teddy Bear'), 2, 19.99);

-- Insert additional payments
INSERT INTO Payments (order_id, bank_name, payment_status, amount) VALUES
((SELECT id FROM Orders WHERE customer_id = 3), 'Bank A', 'pending', 1199.98),
((SELECT id FROM Orders WHERE customer_id = 4), 'Bank B', 'completed', 169.99);

-- Insert shipping information for orders
INSERT INTO Shipping (order_id, tracking_number, shipping_status) 
VALUES
((SELECT id FROM Orders WHERE customer_id = 1 LIMIT 1), 'TRACK123789', 'shipped'), 
((SELECT id FROM Orders WHERE customer_id = 5 LIMIT 1), 'TRACK987654', 'shipped');



-- Insert additional ratings
INSERT INTO Ratings (product_id, customer_id, rating, review_text) VALUES
((SELECT product_id FROM products WHERE name = 'iPhone 16'), 1, 5.0, 'Amazing new features, totally worth the upgrade!'),
((SELECT product_id FROM products WHERE name = 'Face Yogi Weekender Duffle'), 5, 4.5, 'Great size and style for weekend trips.'),
((SELECT product_id FROM products WHERE name = 'Teddy Bear'), 5, 5.0, 'My kids love it, very soft and cuddly!');

-- Insert additional cart items
INSERT INTO Cart (user_id, product_id, quantity) VALUES
(5, (SELECT product_id FROM products WHERE name = 'iPhone 4'), 1),
(6, (SELECT product_id FROM products WHERE name = 'Balancing Spray Toner'), 1);

-- Insert additional shipping addresses
INSERT INTO Shipping_Addresses (user_id, address_line1, address_line2, city, state, postal_code, country) VALUES
(5, '123 Oak St', NULL, 'Cityville', 'Stateville', '67890', 'Country'),
(6, '456 Pine St', 'Apt 3', 'Townsville', 'Countryville', '54321', 'Country');




