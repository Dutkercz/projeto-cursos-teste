ALTER TABLE db_user
ADD COLUMN is_active BOOLEAN DEFAULT TRUE;

UPDATE db_user
SET is_active = TRUE;