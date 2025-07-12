-- Create Users table
CREATE TABLE IF NOT EXISTS Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)     NOT NULL,
    email VARCHAR(150)    NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    points INT            NOT NULL DEFAULT 0,
    role ENUM('USER','ADMIN') NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create Items table
CREATE TABLE IF NOT EXISTS Items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT   NOT NULL,
    title VARCHAR(150)    NOT NULL,
    description TEXT      NULL,
    category ENUM('Men','Women','Kids','Unisex') NOT NULL,
    type VARCHAR(50)      NOT NULL,
    size ENUM('XS','S','M','L','XL','XXL') NOT NULL,
    condition ENUM('New','Like New','Gently Used','Used','Old but Usable') NOT NULL,
    points_cost INT       NOT NULL DEFAULT 0,
    status ENUM('Pending','Approved','Swapped','Rejected') NOT NULL DEFAULT 'Pending',
    created_at TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Store up to 5 images per item
CREATE TABLE IF NOT EXISTS ItemImages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT NOT NULL,
    image_path VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES Items(id) ON DELETE CASCADE
);

-- Record point-based “swap” requests
CREATE TABLE IF NOT EXISTS Swaps (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT    NOT NULL,
    requester_id INT NOT NULL,
    status ENUM('Requested','Approved','Rejected','Expired') NOT NULL DEFAULT 'Requested',
    requested_at TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id)     REFERENCES Items(id)      ON DELETE CASCADE,
    FOREIGN KEY (requester_id) REFERENCES Users(id)      ON DELETE CASCADE
);

-- Indexes to speed up searches/filters
CREATE INDEX idx_items_category   ON Items(category);
CREATE INDEX idx_items_type       ON Items(type);
CREATE INDEX idx_items_size       ON Items(size);
CREATE INDEX idx_items_condition  ON Items(condition);
