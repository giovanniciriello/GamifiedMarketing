CREATE TRIGGER ADD_POINT
BEFORE UPDATE ON submissions
FOR EACH ROW
BEGIN

  DECLARE optional_points INT;
  DECLARE required_points INT;

  SET optional_points = NEW.points;

  IF NEW.sex IS NOT NULL THEN
    SET optional_points = optional_points + 2;
  END IF;

  IF NEW.age IS NOT NULL THEN
    SET optional_points = optional_points + 2;
  END IF;

  IF NEW.expertise_level IS NOT NULL THEN
    SET optional_points = optional_points + 2;
  END IF;

  SELECT count(*)
    INTO required_points
    FROM responses R
    WHERE submission_id = NEW.id;

  SET NEW.points = optional_points + required_points;

END;
