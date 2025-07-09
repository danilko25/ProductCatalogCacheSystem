DELETE FROM PRODUCT;
DELETE FROM CATEGORY;

ALTER TABLE CATEGORY ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE PRODUCT ALTER COLUMN ID RESTART WITH 1;

INSERT INTO CATEGORY (id, category_name) VALUES
                                             (100, 'Test Electronics'),
                                             (101, 'Test Books'),
                                             (102, 'Test Clothes');

INSERT INTO PRODUCT (id, name, description, price, category_id, stock, creation_date, last_update_date) VALUES
                                                                                                            (1000, 'Test Laptop Alpha', 'A basic test laptop for functional testing.', 500.00, 100, 10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                            (1001, 'Test Phone Beta', 'A simple test phone for UI validation.', 200.00, 100, 25, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                            (1002, 'Test Headphones Gamma', 'Basic test headphones, often out of stock.', 50.00, 100, 50, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                            (1003, 'Test Out Of Stock Item', 'This product has zero stock.', 100.00, 100, 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

                                                                                                            (1004, 'Test Novel One', 'A short novel for reading list tests.', 10.00, 101, 30, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                            (1005, 'Test Cookbook Two', 'A cooking book for search tests.', 12.50, 101, 20, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

                                                                                                            (1006, 'Test T-Shirt XL', 'Plain test t-shirt, large size.', 15.00, 102, 40, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                            (1007, 'Test Jeans Slim', 'Simple test jeans, slim fit.', 30.00, 102, 15, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());