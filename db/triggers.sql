DELIMITER $$

CREATE TRIGGER NO_BAD_WORDS
AFTER INSERT ON responses
FOR EACH ROW
BEGIN
	DECLARE badWordsCount INT;
  DECLARE userId INT;

	SELECT COUNT(*)
	INTO badWordsCount
	FROM bad_words AS BW
	WHERE UPPER(REGEXP_REPLACE(concat(' ', NEW.body, ' '), '[^[:alnum:]]+', ' ')  )
		LIKE + concat('%', UPPER(BW.text),'%');

	IF badWordsCount > 0 THEN

    ROLLBACK();

		SELECT U.id
        INTO userId
        FROM submissions S JOIN users U ON(S.user_id = U.id)
		    WHERE S.id = NEW.submission_id
        LIMIT 1;

      UPDATE users
        SET banned_at = current_timestamp()
        WHERE id = userId;
    END IF;

END$$

DELIMITER;


DELIMITER $$

CREATE TRIGGER ADD_POINT
BEFORE UPDATE ON submissions
FOR EACH ROW
BEGIN
	DECLARE total_points INT;
    DECLARE optional_points INT;

    SET total_points = NEW.points;

    IF NEW.sex IS NOT NULL THEN
		SET total_points = total_points + 2;
    END IF;

    IF NEW.age IS NOT NULL THEN
		SET total_points = total_points + 2;
    END IF;

    IF NEW.expertise_level IS NOT NULL THEN
		SET total_points = total_points + 2;
    END IF;

    SELECT count(*)
    INTO optional_points
    FROM responses R
    WHERE submission_id = NEW.id;

    SET total_points = total_points + optional_points;

    SET NEW.points = total_points;

END$$
