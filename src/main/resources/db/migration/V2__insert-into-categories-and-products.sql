INSERT INTO CATEGORY (category_name) VALUES
                                         ('Electronics'),
                                         ('Books'),
                                         ('Apparel'),
                                         ('Home & Kitchen'),
                                         ('Sports & Outdoors'),
                                         ('Beauty & Health'),
                                         ('Kids & Baby');

INSERT INTO PRODUCT (name, description, price, category_id, stock, creation_date, last_update_date) VALUES
                                                                                                        ('Dell XPS 15 Laptop', 'Powerful laptop for professionals with OLED display.', 1899.99, 1, 30, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Samsung Galaxy S24 Smartphone', 'Flagship smartphone with advanced camera features and AI.', 1199.00, 1, 75, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Sony WH-1000XM5 Wireless Headphones', 'Premium headphones with industry-leading noise cancellation.', 349.99, 1, 150, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('PlayStation 5 Gaming Console', 'The latest generation gaming console from Sony.', 499.99, 1, 40, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Dune by Frank Herbert', 'A classic science fiction novel.', 22.50, 2, 180, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('The Master and Margarita by M. Bulgakov', 'A classic novel of Russian literature.', 18.00, 2, 250, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Clean Code by Robert C. Martin', 'A handbook of agile software craftsmanship.', 35.99, 2, 100, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Levi''s Men''s Denim Jacket', 'Classic denim jacket, blue wash.', 89.99, 3, 90, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Women''s Oversized Cotton T-Shirt', 'Soft and comfortable 100% cotton t-shirt, white.', 29.99, 3, 200, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Adidas Track Pants', 'Comfortable pants for training and casual wear.', 55.00, 3, 120, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('DeLonghi Dedica Espresso Machine', 'Compact espresso machine for home use.', 249.00, 4, 60, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Tefal Ingenio Cookware Set', 'Versatile cookware set with detachable handle.', 150.00, 4, 80, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Philips Hue Smart Bulb', 'Color and brightness adjustable bulb, smartphone controlled.', 25.99, 4, 300, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Cube Aim Mountain Bike', 'Reliable mountain bike for recreational use.', 599.00, 5, 25, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('3-Person Camping Tent', 'Lightweight and durable tent for camping trips.', 120.00, 5, 40, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Oral-B Electric Toothbrush', 'Electric toothbrush with 3D cleaning technology.', 79.99, 6, 110, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('The Ordinary Skincare Set', 'Basic set for daily skincare routine.', 45.00, 6, 95, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('LEGO City Police Station Set', 'LEGO building set for a police station.', 65.00, 7, 130, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ('Bugaboo Fox Stroller', 'Versatile stroller for comfortable walks.', 999.00, 7, 15, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());