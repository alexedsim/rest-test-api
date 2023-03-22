CREATE DATABASE IF NOT EXISTS betest;
USE betest;

CREATE TABLE IF NOT EXISTS useragent (
  user_agent_hash VARCHAR(255) NOT NULL,
  user_agent_string TEXT NOT NULL,
  timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  browser VARCHAR(255),
  os VARCHAR(255),
  device_type VARCHAR(255),
  PRIMARY KEY (user_agent_hash)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;