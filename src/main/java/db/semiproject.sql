SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS Board;
DROP TABLE IF EXISTS user;




/* Create Tables */

CREATE TABLE Board
(
	bid int NOT NULL AUTO_INCREMENT,
	title varchar(128) NOT NULL,
	content varchar(5000),
	addr varchar(300) NOT NULL,
	tel varchar(20),
	area varchar(10),
	tag varchar(20),
	closeTime varchar(20),
	homepage varchar(128),
	thum varchar(200) NOT NULL,
	likeCnt int DEFAULT 0 NOT NULL,
	viewCnt int DEFAULT 0 NOT NULL,
	reCnt int DEFAULT 0 NOT NULL,
	grade decimal(2,1),
	modTime datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
	rec int DEFAULT 0 NOT NULL,
	isDel int DEFAULT 0 NOT NULL,
	PRIMARY KEY (bid)
);


CREATE TABLE user
(
	uid varchar(20) NOT NULL,
	pwd char(60) NOT NULL,
	uname varchar(20) NOT NULL,
	email varchar(32),
	area varchar(10) NOT NULL,
	regDate datetime DEFAULT (CURRENT_DATE) NOT NULL,
	isDel int DEFAULT 0 NOT NULL,
	PRIMARY KEY (uid)
);



