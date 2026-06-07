CREATE TABLE IF NOT EXISTS operation_records (
  id INT AUTO_INCREMENT PRIMARY KEY,
  module_name VARCHAR(120) NOT NULL,
  owner_name VARCHAR(80) NOT NULL,
  status VARCHAR(40) NOT NULL,
  metric VARCHAR(40) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO operation_records (module_name, owner_name, status, metric)
VALUES ('楼层座位图可视化', '运营组', 'ready', '100%');

CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  real_name VARCHAR(50) NOT NULL,
  email VARCHAR(100),
  phone VARCHAR(20),
  role VARCHAR(20) NOT NULL DEFAULT 'USER',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS seats (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  seat_code VARCHAR(20) NOT NULL UNIQUE,
  floor INT NOT NULL,
  area VARCHAR(50),
  seat_type VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
  has_power BOOLEAN DEFAULT FALSE,
  is_available BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS reservations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  seat_id BIGINT NOT NULL,
  reservation_date DATE NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'RESERVED',
  checkin_time TIMESTAMP NULL,
  checkout_time TIMESTAMP NULL,
  qr_code VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS violation_records (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  reservation_id BIGINT NOT NULL,
  seat_id BIGINT NOT NULL,
  seat_code VARCHAR(20) NOT NULL,
  violation_type VARCHAR(30) NOT NULL DEFAULT 'TIMEOUT_NO_CHECKIN',
  violation_time TIMESTAMP NOT NULL,
  reservation_date DATE NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  description VARCHAR(255),
  handled BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_violation_user ON violation_records(user_id);
CREATE INDEX IF NOT EXISTS idx_violation_time ON violation_records(violation_time);
CREATE INDEX IF NOT EXISTS idx_violation_date ON violation_records(reservation_date);

INSERT INTO users (username, real_name, email, phone, role) VALUES
('admin', '系统管理员', 'admin@library.edu', '13800000000', 'ADMIN'),
('zhangsan', '张三', 'zhangsan@university.edu', '13800000001', 'USER'),
('lisi', '李四', 'lisi@university.edu', '13800000002', 'USER'),
('wangwu', '王五', 'wangwu@university.edu', '13800000003', 'USER'),
('zhaoliu', '赵六', 'zhaoliu@university.edu', '13800000004', 'USER'),
('sunqi', '孙七', 'sunqi@university.edu', '13800000005', 'USER');

INSERT INTO seats (seat_code, floor, area, seat_type, has_power) VALUES
('F1-A01', 1, 'A区', 'NORMAL', FALSE),
('F1-A02', 1, 'A区', 'WINDOW', TRUE),
('F1-A03', 1, 'A区', 'NORMAL', TRUE),
('F1-B01', 1, 'B区', 'WINDOW', FALSE),
('F1-B02', 1, 'B区', 'NORMAL', TRUE),
('F2-A01', 2, 'A区', 'WINDOW', TRUE),
('F2-A02', 2, 'A区', 'NORMAL', FALSE),
('F2-B01', 2, 'B区', 'NORMAL', TRUE),
('F2-B02', 2, 'B区', 'WINDOW', TRUE),
('F3-A01', 3, 'A区', 'WINDOW', TRUE);

INSERT INTO reservations (user_id, seat_id, reservation_date, start_time, end_time, status, qr_code) VALUES
(2, 1, '2026-06-01', '08:00:00', '10:00:00', 'CANCELLED', 'QR20260601001'),
(2, 2, '2026-06-02', '09:00:00', '11:00:00', 'COMPLETED', 'QR20260602001'),
(3, 3, '2026-06-03', '14:00:00', '16:00:00', 'VIOLATED', 'QR20260603001'),
(2, 1, '2026-06-04', '08:00:00', '10:00:00', 'VIOLATED', 'QR20260604001'),
(3, 2, '2026-06-05', '10:00:00', '12:00:00', 'VIOLATED', 'QR20260605001'),
(4, 4, '2026-06-05', '14:00:00', '17:00:00', 'VIOLATED', 'QR20260605002'),
(2, 3, '2026-06-06', '08:00:00', '11:00:00', 'VIOLATED', 'QR20260606001'),
(5, 5, '2026-06-06', '09:00:00', '12:00:00', 'VIOLATED', 'QR20260606002'),
(3, 6, '2026-06-06', '14:00:00', '16:00:00', 'VIOLATED', 'QR20260606003'),
(2, 7, '2026-06-07', '08:00:00', '10:00:00', 'VIOLATED', 'QR20260607001');

INSERT INTO violation_records (user_id, reservation_id, seat_id, seat_code, violation_type, violation_time, reservation_date, start_time, end_time, description) VALUES
(3, 3, 3, 'F1-A03', 'TIMEOUT_NO_CHECKIN', '2026-06-03 14:15:00', '2026-06-03', '14:00:00', '16:00:00', '预约开始后15分钟未签到，系统自动释放座位'),
(2, 4, 1, 'F1-A01', 'TIMEOUT_NO_CHECKIN', '2026-06-04 08:15:00', '2026-06-04', '08:00:00', '10:00:00', '预约开始后15分钟未签到，系统自动释放座位'),
(3, 5, 2, 'F1-A02', 'TIMEOUT_NO_CHECKIN', '2026-06-05 10:15:00', '2026-06-05', '10:00:00', '12:00:00', '预约开始后15分钟未签到，系统自动释放座位'),
(4, 6, 4, 'F1-B01', 'TIMEOUT_NO_CHECKIN', '2026-06-05 14:15:00', '2026-06-05', '14:00:00', '17:00:00', '预约开始后15分钟未签到，系统自动释放座位'),
(2, 7, 3, 'F1-A03', 'TIMEOUT_NO_CHECKIN', '2026-06-06 08:15:00', '2026-06-06', '08:00:00', '11:00:00', '预约开始后15分钟未签到，系统自动释放座位'),
(5, 8, 5, 'F1-B02', 'TIMEOUT_NO_CHECKIN', '2026-06-06 09:15:00', '2026-06-06', '09:00:00', '12:00:00', '预约开始后15分钟未签到，系统自动释放座位'),
(3, 9, 6, 'F2-A01', 'TIMEOUT_NO_CHECKIN', '2026-06-06 14:15:00', '2026-06-06', '14:00:00', '16:00:00', '预约开始后15分钟未签到，系统自动释放座位'),
(2, 10, 7, 'F2-A02', 'TIMEOUT_NO_CHECKIN', '2026-06-07 08:15:00', '2026-06-07', '08:00:00', '10:00:00', '预约开始后15分钟未签到，系统自动释放座位');
