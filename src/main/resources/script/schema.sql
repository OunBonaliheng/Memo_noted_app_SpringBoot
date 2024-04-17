CREATE TABLE note_tb (
                         noted_id SERIAL PRIMARY KEY,
                         title VARCHAR(255),
                         note_content VARCHAR(500),
                         note_description VARCHAR(500),
                         creation_date TIMESTAMP,
                         select_color VARCHAR(50),
                         receive_img VARCHAR(255),
                         receive_video VARCHAR(255)
);
CREATE TABLE files(
                      file_id SERIAL PRIMARY KEY ,
                      noted_id int,
                      receive_img VARCHAR(255),
                      receive_video VARCHAR(255),
                      FOREIGN KEY (noted_id) REFERENCES note_tb (noted_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE tags_tb (

                         tag_id SERIAL PRIMARY KEY,
                         tag_name VARCHAR(255)
);

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
                         Password VARCHAR(255) NOT NULL,
                         gender gender_enum NOT NULL,
                         profile_image VARCHAR(255)
);
CREATE TABLE otp_tb(
                       opt_id SERIAL PRIMARY KEY,
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
