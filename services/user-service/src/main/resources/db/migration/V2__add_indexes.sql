CREATE UNIQUE INDEX uq_user_profiles_username ON user_profiles(username);
CREATE UNIQUE INDEX uq_user_profiles_email ON user_profiles(email);

CREATE INDEX idx_addresses_user_id ON addresses(user_id);

CREATE UNIQUE INDEX uq_addresses_default_per_user
  ON addresses(user_id)
  WHERE is_default = true;