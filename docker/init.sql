USE jjangi;
CREATE TABLE `jjangi`.`game_session` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `current_turn` VARCHAR(45) NULL,
    `status` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `jjangi`.`piece_state` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `game_session_id` INT NOT NULL,
    `type` VARCHAR(45) NOT NULL,
    `team` VARCHAR(45) NOT NULL,
    `position_row` INT NOT NULL,
    `position_column` INT NOT NULL,
    PRIMARY KEY (`id`)
);
