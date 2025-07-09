-- Add enabled column with default value and NOT NULL constraint in one step
ALTER TABLE users ADD COLUMN enabled BOOLEAN NOT NULL DEFAULT TRUE;

-- Handle orders status column
ALTER TABLE orders ALTER COLUMN status SET NOT NULL;