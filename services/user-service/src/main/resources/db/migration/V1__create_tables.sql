CREATE TABLE user_profiles (
  id UUID PRIMARY KEY,
  username VARCHAR(60) NOT NULL,
  email VARCHAR(254) NOT NULL,
  full_name VARCHAR(120) NOT NULL,
  phone VARCHAR(30),
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE addresses (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL REFERENCES user_profiles(id) ON DELETE CASCADE,
  label VARCHAR(60) NOT NULL,
  line1 VARCHAR(160) NOT NULL,
  line2 VARCHAR(160),
  city VARCHAR(80) NOT NULL,
  state VARCHAR(80) NOT NULL,
  country CHAR(2) NOT NULL,
  postal_code VARCHAR(20),
  reference VARCHAR(200),
  is_default BOOLEAN NOT NULL DEFAULT false,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);