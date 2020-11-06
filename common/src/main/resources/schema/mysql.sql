CREATE TABLE `mcbans_actions`
(
    `violation_uuid`      VARCHAR(36)  NOT NULL,
    `player_uuid`         VARCHAR(36)  NOT NULL,
    `executor_uuid`       VARCHAR(36)  NOT NULL,
    `deexecutor_uuid`     VARCHAR(36)           DEFAULT NULL,
    `violation_reason`    VARCHAR(256) NOT NULL,
    `violation_type`      VARCHAR(16)  NOT NULL,
    `violation_time`      LONG         NOT NULL,
    `violation_duration`  LONG         NOT NULL,
    `violation_cancelled` BOOL         NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`violation_uuid`)
) DEFAULT CHARSET = utf8mb4;