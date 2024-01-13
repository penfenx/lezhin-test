# 테스트 데이터

INSERT INTO user (user_name, user_email, gender, type, register_date)
VALUES
('John Doe', 'john.doe@example.com', 'MALE', 'NORMAL', NOW()),
('Jane Smith', 'jane.smith@example.com', 'FEMALE', 'NORMAL', NOW()),
('Alice Johnson', 'alice.johnson@example.com', 'NON_BINARY', 'NORMAL', NOW()),
('Bob Williams', 'bob.williams@example.com', 'MALE', 'ADULT', NOW()),
('Charlie Brown', 'charlie.brown@example.com', 'MALE', 'NORMAL', NOW()),
('Diana Davis', 'diana.davis@example.com', 'FEMALE', 'NORMAL', NOW()),
('Ella Miller', 'ella.miller@example.com', 'NON_BINARY', 'ADULT', NOW()),
('Frank Wilson', 'frank.wilson@example.com', 'MALE', 'NORMAL', NOW()),
('Grace Moore', 'grace.moore@example.com', 'FEMALE', 'NORMAL', NOW()),
('Henry Taylor', 'henry.taylor@example.com', 'MALE', 'NORMAL', NOW());


INSERT INTO contents (contents_name, author, coin, open_date)
VALUES
('레바툰', '레바', 0, NOW()),
('던전 속 사정', '레바', 3, NOW()),
('몸에 좋은 남자', '박형준', 3, NOW()),
('일상생활 가능하세요?', 'Alice Crazy', 3, NOW()),
('마황의 귀환', 'Hongshu', 3, NOW()),
('도원암귀', '우루시바라 유라', 3, NOW()),
('체인소 맨', 'Author7', 3000, NOW()),
('차원이동 레벨업', '시대만왕', 4, NOW()),
('어쩌다 뉴비남매', 'WO', 3, NOW()),
('원펀맨', 'ONE', 4, NOW());