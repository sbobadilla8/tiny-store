CREATE TABLE category (
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

CREATE TABLE artist (
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(100) UNIQUE,
    profilePhoto TEXT
);

CREATE TABLE product (
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(50),
    price    NUMERIC(5, 2),
    imageUrl TEXT,
    category BIGINT UNSIGNED,
    artist   BIGINT UNSIGNED,
    stock    INTEGER,
    status   ENUM('ACTIVE', 'INACTIVE')
    UNIQUE (name, artist),
    FOREIGN KEY (category) REFERENCES category (id),
    FOREIGN KEY (artist) REFERENCES artist (id)
);

CREATE TABLE users (
    id       SERIAL,
    username VARCHAR(25),
    password CHAR(128),
    email    VARCHAR(100),
    type     ENUM ('CLIENT', 'MANAGER')
);

CREATE TABLE cartHeader (
    id     SERIAL PRIMARY KEY,
    user   BIGINT UNSIGNED,
    date   DATETIME,
    status ENUM ('PENDING', 'CONFIRMED', 'FULFILLED'),
    FOREIGN KEY (user) REFERENCES users (id)
);

CREATE TABLE cartDetail (
    id       SERIAL,
    header   BIGINT UNSIGNED,
    product  BIGINT UNSIGNED,
    quantity INTEGER,
    FOREIGN KEY (header) REFERENCES cartHeader (id),
    FOREIGN KEY (product) REFERENCES product (id)
);

-- Initial data for testing
INSERT INTO category(name)
VALUES ('Metal'),
       ('Rock');

INSERT INTO artist(name, profilePhoto)
VALUES ('System of a Down', 'https://i.iheart.com/v3/catalog/artist/89458?ops=fit(480%2C480)%2Crun(%22circle%22)'),
       ('Mägo de Oz', 'https://portal.andina.pe/EDPfotografia2/Thumbnail/2010/09/14/000135917W.jpg'),
       ('Lindemann', 'https://music.metason.net/image?fn=A-2494458.jpeg&sc=_63');

INSERT INTO product(name, price, imageUrl, category, artist, stock)
VALUES ('Toxicity', 19.99, 'https://upload.wikimedia.org/wikipedia/en/6/64/SystemofaDownToxicityalbumcover.jpg', 1, 1,
        50),
       ('Ilussia', 19.99, 'https://i.scdn.co/image/ab67616d0000b273d633a13281228832f4ffbf73', 2, 2, 40);

INSERT INTO users(username, password, email, type)
VALUES ('admin', SHA2('admin', 512), 'admin@mail.com', 'MANAGER'),
       ('user1', SHA2('user1', 512), 'user1@mail.com', 'CLIENT');

