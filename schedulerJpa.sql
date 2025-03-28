CREATE TABLE author (
                        author_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        login_id VARCHAR(50) NOT NULL UNIQUE,
                        name VARCHAR(50) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        created_date DATETIME NOT NULL,
                        updated_date DATETIME NOT NULL
);

CREATE TABLE schedule (
                          schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          author_id BIGINT NOT NULL,
                          title VARCHAR(100) NOT NULL,
                          contents TEXT,
                          created_date DATETIME NOT NULL,
                          updated_date DATETIME NOT NULL,

                          CONSTRAINT fk_schedule_author FOREIGN KEY (author_id) REFERENCES author(author_id) ON DELETE CASCADE
);

CREATE TABLE comment
(
    comment_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id    BIGINT   NOT NULL,
    schedule_id  BIGINT   NOT NULL,
    content     TEXT     NOT NULL,
    created_date DATETIME NOT NULL,
    updated_date DATETIME NOT NULL,

    CONSTRAINT fk_comment_author FOREIGN KEY (author_id) REFERENCES author (author_id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_schedule FOREIGN KEY (schedule_id) REFERENCES schedule (schedule_id) ON DELETE CASCADE
);