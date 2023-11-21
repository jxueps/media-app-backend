CREATE DATABASE  IF NOT EXISTS `media_app`;
USE `media_app`;


DROP TABLE IF EXISTS `user_movie`;
DROP TABLE IF EXISTS `review`;
DROP TABLE IF EXISTS `movie`;
DROP TABLE IF EXISTS `token`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT 'USER',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `token_string` varchar(255) NOT NULL,
  `token_type` varchar(255) DEFAULT 'BEARER',
  `expired` boolean DEFAULT false,
  `revoked` boolean DEFAULT false,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user` VALUES 
	(1,'Leslie','Andrews','leslie@luv2code.com', '$2a$12$KtfsQ4e8iw2LOZSyp2BdLugpjs.7U4qvjG4QpR.z7P7voMN3RfcuG', 'USER');

CREATE TABLE `movie` (
  `imdb_id` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `year` varchar(4) DEFAULT NULL,
  `runtime` varchar(255) DEFAULT NULL,
  `rated` varchar(255) DEFAULT NULL,
  `poster` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`imdb_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` float DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
--   `user_movie_id` int NOT NULL,
  PRIMARY KEY (`id`)
--   FOREIGN KEY (`user_movie_id`) REFERENCES user_movie(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



CREATE TABLE `user_movie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `imdb_id` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `watched` datetime DEFAULT NULL,
  `priority` varchar(255) DEFAULT 'HIGH',
  `review_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(id),
  FOREIGN KEY (`imdb_id`) REFERENCES movie(imdb_id),
  FOREIGN KEY (`review_id`) REFERENCES review(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



