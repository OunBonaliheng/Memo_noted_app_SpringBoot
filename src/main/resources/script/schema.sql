CREATE TABLE note_tb (
                         noted_id SERIAL PRIMARY KEY,
                         title TEXT,
                         note_content TEXT,
                         note_description TEXT,
                         creation_date TIMESTAMP,
                         select_color VARCHAR(50),
                         user_id INT,
                         FOREIGN KEY (user_id) REFERENCES user_tb (user_id) ON UPDATE CASCADE ON DELETE CASCADE

);
CREATE TABLE files(
                      file_id SERIAL PRIMARY KEY ,
                      noted_id INT,
                      receiveFiles VARCHAR(255),
                      FOREIGN KEY (noted_id) REFERENCES note_tb (noted_id) ON UPDATE CASCADE ON DELETE CASCADE

);

SELECT * FROM note_tb INNER JOIN files f on note_tb.noted_id = f.noted_id WHERE f.noted_id=26;


CREATE TABLE tags_tb (

                         tag_id SERIAL PRIMARY KEY,
                         tag_name VARCHAR(255)  NOT NULL,
                         user_id INT,
                         FOREIGN KEY (user_id) REFERENCES user_tb (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);


SELECT *
FROM note_tb AS n
         INNER JOIN tag_note AS tn ON n.noted_id = tn.noted_id
         INNER JOIN tags_tb AS t ON tn.tag_id = t.tag_id ;

CREATE TABLE tag_note (
                          Id SERIAL PRIMARY KEY,
                          noted_id INT,
                          tag_id INT,
                          FOREIGN KEY (noted_id) REFERENCES note_tb (noted_id) ON UPDATE CASCADE ON DELETE CASCADE,
                          FOREIGN KEY (tag_id) REFERENCES tags_tb (tag_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');

CREATE TABLE user_tb (
                         user_id SERIAL PRIMARY KEY,
                         userName VARCHAR(255),
                         email VARCHAR(255) UNIQUE NOT NULL,
                         Password VARCHAR(255) NOT NULL
--                          gender gender_enum NOT NULL,
--                          profile_image VARCHAR(255)
);
CREATE TABLE otp_tb(
                       otp_id SERIAL PRIMARY KEY,
                       otp_code VARCHAR(6) NOT NULL,
                       issued_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       expiration_time TIMESTAMP NOT NULL,
                       verified BOOLEAN NOT NULL DEFAULT FALSE,
                       user_id INT UNIQUE,
                       FOREIGN KEY (user_id) REFERENCES user_tb (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- CREATE TABLE file_tb (
--                          file_id SERIAL PRIMARY KEY,
--                          file_name VARCHAR(255),
--                          file_type VARCHAR(50),
--                          file_size BIGINT,
--                          note_id INT,
--                          FOREIGN KEY (note_id) REFERENCES note_tb(noted_id)
-- );
--
-- --Test--
-- SELECT * FROM user_tb ORDER BY Id;
-- SELECT * FROM user_tb WHERE Id = 2;
-- SELECT * FROM user_tb WHERE LOWER(name) LIKE CONCAT('%', LOWER('v'), '%');
--
-- INSERT INTO user_tb (userName, email, password, gender)
-- VALUES ('adwdaw', 'dawdawd', 'dawd', 'dawd') RETURNING *;

-- INSERT INTO tags_tb (tag_name)
-- VALUES
--     ('Tag 1'),
--     ('Tag 2'),
--     ('Tag 3'),
--     ('Tag 4'),
--     ('Tag 5');
--
-- INSERT INTO note_tb (title, note_content, note_description, creation_date, select_color, filesimgvideo)
-- VALUES
--     ('Title 1', 'Content 1', 'Description 1', '2024-04-07 10:00:00', 'Color 1', 'ImageVideo 1'),
--     ('Title 2', 'Content 2', 'Description 2', '2024-04-07 11:00:00', 'Color 2', 'ImageVideo 2'),
--     ('Title 3', 'Content 3', 'Description 3', '2024-04-07 12:00:00', 'Color 3', 'ImageVideo 3'),
--     ('Title 4', 'Content 4', 'Description 4', '2024-04-07 13:00:00', 'Color 4', 'ImageVideo 4'),
--     ('Title 5', 'Content 5', 'Description 5', '2024-04-07 14:00:00', 'Color 5', 'ImageVideo 5');

-- SELECT t.tag_name FROM tags_tb t  INNER JOIN tag_note tn ON tn.tag_id = t.tag_id  WHERE tn.noted_id = 26,

SELECT * FROM user_tb WHERE email = 'vatteysoma@gmail.com' AND Password = 'heng@123';
SELECT * FROM note_tb WHERE user_id = 3 ORDER BY creation_date DESC;
SELECT username And email FROM user_tb WHERE user_id ='2';

UPDATE user_tb SET username = 'Vattey' WHERE email = 'vatteysoma@gmail.com';
SELECT user_id, userName AS name, email FROM user_tb WHERE user_id = '6'
