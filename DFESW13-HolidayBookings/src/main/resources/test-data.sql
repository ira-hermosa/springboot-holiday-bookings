DROP TABLE IF EXISTS `holiday_booking`;

CREATE TABLE holiday_booking( 
id long AUTO_INCREMENT, 
country VARCHAR(30) NOT NULL,
weather VARCHAR(25) NOT NULL,
price float NOT NULL,
all_inclusive boolean NOT NULL,
PRIMARY KEY (id)
);


INSERT INTO `holiday_booking` (`country`, `weather`, `price`, `all_Inclusive`) VALUES ('country1', 'weather1', 1, true);
INSERT INTO `holiday_booking` (`country`, `weather`, `price`, `all_Inclusive`) VALUES ('country2', 'weather2', 2, true);
