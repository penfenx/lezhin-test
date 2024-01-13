CREATE DATABASE IF NOT EXISTS lezhin;
USE lezhin;

CREATE TABLE contents
(
    ID            INT AUTO_INCREMENT PRIMARY KEY,
    contents_name VARCHAR(255) NULL COMMENT '작품명',
    author        VARCHAR(255) NULL COMMENT '작가',
    coin          INT NULL COMMENT '금액 (0원이면 무료)',
    open_date     DATETIME NULL COMMENT '서비스 제공일',
    is_adult      TINYINT(1) DEFAULT 0 NOT NULL COMMENT '1: 성인작품, 0: 일반작품'
);

CREATE TABLE review
(
    user_id     INT NOT NULL COMMENT '유저 ID',
    contents_id INT NOT NULL COMMENT '작품 ID',
    rating      ENUM ('LIKE', 'DISLIKE') NULL COMMENT 'LIKE: 좋아요, DISLIKE: 싫어요',
    comment     VARCHAR(255) NULL COMMENT '댓글',
    PRIMARY KEY (user_id, contents_id),
    INDEX idx_contents_id (contents_id),
    INDEX idx_user_id (user_id),
    INDEX idx_rating (rating)
);

CREATE TABLE user
(
    ID            INT AUTO_INCREMENT PRIMARY KEY,
    user_name     VARCHAR(255) NULL COMMENT '유저 이름',
    user_email    VARCHAR(255) NULL COMMENT '유저 이메일',
    gender        ENUM ('MALE', 'FEMALE', 'NON_BINARY') NULL COMMENT 'MALE: 남성, FEMALE: 여성, NON_BINARY: 비바이너리',
    type          ENUM ('NORMAL', 'ADULT') NULL COMMENT 'NORMAL: 일반, ADULT: 성인',
    register_date DATETIME NULL COMMENT '서비스등록일',
    INDEX idx_register_date (register_date)
);

CREATE TABLE view_log
(
    log_id      INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT NULL COMMENT '유저 ID',
    contents_id INT NULL COMMENT '작품 ID',
    view_date   DATETIME NULL COMMENT '조회 일시',
    INDEX idx_user_id (user_id),
    INDEX idx_contents_id (contents_id)
);