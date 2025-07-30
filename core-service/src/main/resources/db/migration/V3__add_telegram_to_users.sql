ALTER TABLE users
    ADD COLUMN telegram VARCHAR(40);
UPDATE users SET telegram = '@Sergey_Kosenko_1973';
ALTER TABLE users ALTER COLUMN telegram SET NOT NULL;