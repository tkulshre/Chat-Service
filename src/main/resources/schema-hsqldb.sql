CREATE TABLE USER (
  user_id    INTEGER INCREMENT,
  user_name  VARCHAR(150) PRIMARY KEY,
  password   VARCHAR(150)
);

CREATE TABLE USER_MESSAGES (
  user_id_1    INTEGER,
  user_id_2    INTEGER,
  message_id   INTEGER INCREMENT
);

CREATE TABLE MESSAGES (
  message_id   INTEGER,
  message_data VARCHAR(500),
  timestamp    TIMESTAMP
);

CREATE TABLE VIDEO_METADATA (
  message_id     INTEGER,
  source         INTEGER 1,
  length         INTEGER 100
);

CREATE TABLE IMAGE_METADATA (
  message_id     INTEGER,
  height         INTEGER 10,
  width          INTEGER 10
);

