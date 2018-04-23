

DROP DATABASE IF EXISTS tank_web;
CREATE DATABASE tank_web;

use tank_web;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
  email VARCHAR(20),
  password VARCHAR(20),
  type SMALLINT NOT NULL DEFAULT 0,
  phone VARCHAR(20),
  score INT DEFAULT 0.0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS observer;
CREATE TABLE observer
(
  observer_id BIGINT NOT NULL ,
  observee_id BIGINT NOT NULL ,
  date BIGINT DEFAULT 0,
  PRIMARY KEY (observer_id, observee_id)
  #   FOREIGN KEY (observer_id) REFERENCES user(id),
  #   FOREIGN KEY (observee_id) REFERENCES user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS project;
CREATE TABLE project
(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  name VARCHAR(20),
  url VARCHAR(80),
  compress VARCHAR(20),
  language VARCHAR(20),
  date BIGINT DEFAULT 0,
  size FLOAT DEFAULT 0.0,
  compile_state SMALLINT DEFAULT 0,
  run_state SMALLINT DEFAULT 0,
  random SMALLINT DEFAULT 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS map;
CREATE TABLE map
(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  name VARCHAR(20),
  url VARCHAR(80),
  date BIGINT DEFAULT 0,
  win_score INT DEFAULT 0,
  lose_score INT DEFAULT 0
  #   FOREIGN KEY (user_id) REFERENCES user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS win_rate;
CREATE TABLE win_rate
(
  user_id BIGINT,
  map_id BIGINT,
  rate FLOAT DEFAULT 0,
  PRIMARY KEY (user_id, map_id)
  #   FOREIGN KEY (user_id) REFERENCES user(id),
  #   FOREIGN KEY (map_id) REFERENCES map(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS room;
CREATE TABLE room
(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  project_id BIGINT,
  map_id BIGINT,
  max_challengers INT DEFAULT 20,
  cur_challengers INT DEFAULT 0,
  start_time BIGINT DEFAULT 0,
  duration BIGINT DEFAULT 0
  #   FOREIGN KEY (project_id) REFERENCES project(id),
  #   FOREIGN KEY (map_id) REFERENCES map(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS battle_log;
CREATE TABLE battle_log
(
  id VARCHAR(50) PRIMARY KEY NOT NULL ,
  project_a_id BIGINT NOT NULL ,
  project_b_id BIGINT NOT NULL ,
  map_id BIGINT NOT NULL ,
  winner SMALLINT DEFAULT 0,
  url VARCHAR(80),
  compress VARCHAR(20),
  date BIGINT DEFAULT 0,
  size FLOAT DEFAULT 0.0
  #   FOREIGN KEY (project_a_id) REFERENCES project(id),
  #   FOREIGN KEY (project_b_id) REFERENCES project(id),
  #   FOREIGN KEY (map_id) REFERENCES  map(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS like_log;
CREATE TABLE like_log
(
  user_id BIGINT NOT NULL,
  battle_log_id VARCHAR(50) NOT NULL ,
  date BIGINT DEFAULT 0,
  PRIMARY KEY (user_id, battle_log_id)
  #   FOREIGN KEY (user_id) REFERENCES user(id),
  #   FOREIGN KEY (battle_log_id) REFERENCES battle_log(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS invite;
CREATE TABLE invite
(
  inviter_id BIGINT NOT NULL ,
  invitee_id BIGINT NOT NULL ,
  room_id BIGINT NOT NULL ,
  date BIGINT DEFAULT 0,
  duration BIGINT DEFAULT 0,
  state SMALLINT DEFAULT 0,
  PRIMARY KEY (inviter_id, invitee_id, room_id)
  #   FOREIGN KEY (inviter_id) REFERENCES user(id),
  #   FOREIGN KEY (invitee_id) REFERENCES user(id),
  #   FOREIGN KEY (room_id) REFERENCES room(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS record;
CREATE TABLE record
(
  player_a_id BIGINT NOT NULL ,
  player_b_id BIGINT NOT NULL ,
  battle_log_id VARCHAR(50) NOT NULL ,
  winner SMALLINT DEFAULT 0,
  date BIGINT DEFAULT 0,
  PRIMARY KEY (player_a_id, player_b_id, battle_log_id)
  #   FOREIGN KEY (player_a_id) REFERENCES user(id),
  #   FOREIGN KEY (player_b_id) REFERENCES user(id),
  #   FOREIGN KEY (battle_log_id) REFERENCES battle_log(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

