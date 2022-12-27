SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS bookMark;
DROP TABLE IF EXISTS reply;
DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS user;




/* Create Tables */

CREATE TABLE board
(
	bid int NOT NULL AUTO_INCREMENT,
	uid varchar(20) NOT NULL,
	title varchar(128) NOT NULL,
	content varchar(128),
	modTime datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
	viewCnt int DEFAULT 0 NOT NULL,
	replyCnt int DEFAULT 0 NOT NULL,
	isDel int DEFAULT 0 NOT NULL,
	files varchar(400),
	PRIMARY KEY (bid)
);


CREATE TABLE bookMark
(
	uid varchar(20) NOT NULL,
	bid int NOT NULL,
);


CREATE TABLE reply
(
	rid int NOT NULL AUTO_INCREMENT,
	content varchar(128) NOT NULL,
	regDate datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
	isMine int DEFAULT 0 NOT NULL,
	uid varchar(20) NOT NULL,
	bid int NOT NULL,
	PRIMARY KEY (rid)
);


CREATE TABLE user
(
	uid varchar(20) NOT NULL,
	pwd char(60) NOT NULL,
	uname varchar(20) NOT NULL,
	email varchar(32),
	regDate datetime DEFAULT (CURRENT_DATE) NOT NULL,
	isDel int DEFAULT 0 NOT NULL,
	PRIMARY KEY (uid)
);



/* Create Foreign Keys */

ALTER TABLE bookMark
	ADD FOREIGN KEY (bid)
	REFERENCES board (bid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reply
	ADD FOREIGN KEY (bid)
	REFERENCES board (bid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE board
	ADD FOREIGN KEY (uid)
	REFERENCES user (uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE bookMark
	ADD FOREIGN KEY (uid)
	REFERENCES user (uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reply
	ADD FOREIGN KEY (uid)
	REFERENCES user (uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



