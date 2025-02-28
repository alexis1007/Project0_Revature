-- -----------------------------------------------------
-- Schema loans
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS loans CASCADE;
CREATE SCHEMA loans;
COMMENT ON SCHEMA loans IS 'Schema for loan management system';

-- Ensure the schema is set in the search path
SET search_path TO loans;

-- First: Tables with no dependencies
-- -----------------------------------------------------
-- Table loans.mailing_addresses
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS loans.mailing_addresses (
    mailing_addresses_id SERIAL PRIMARY KEY,
    street VARCHAR(45) NOT NULL,
    city VARCHAR(45) NOT NULL,
    state VARCHAR(45) NOT NULL,
    zip VARCHAR(45) NOT NULL,
    country VARCHAR(45) NOT NULL
);
-- -----------------------------------------------------
-- Table loans.account_types
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS loans.account_types (
    account_types_id SERIAL PRIMARY KEY,
    account_type VARCHAR(45) NOT NULL
);
-- -----------------------------------------------------
-- Table loans.application_statuses
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS loans.application_statuses (
    application_statuses_id SERIAL PRIMARY KEY,
    application_statuses VARCHAR(10) NOT NULL
);
-- -----------------------------------------------------
-- Table loans.loan_type
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS loans.loan_type (
    loan_type_id SERIAL PRIMARY KEY,
    loan_type VARCHAR(10) NOT NULL
);

-- Second: Tables with single dependencies
-- -----------------------------------------------------
-- Table loans.accounts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS loans.accounts (
    accounts_id SERIAL PRIMARY KEY,
    usuario VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    account_types_id INTEGER NOT NULL,
    CONSTRAINT fk_accounts_account_types
        FOREIGN KEY (account_types_id)
        REFERENCES loans.account_types (account_types_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Third: Tables with multiple dependencies
-- -----------------------------------------------------
-- Table loans.user_profiles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS loans.user_profiles (
    user_profiles_id SERIAL PRIMARY KEY,
    accounts_id INTEGER NOT NULL,
    mailing_addresses_id INTEGER NOT NULL,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    phone_number VARCHAR(45),
    credit_score INTEGER NOT NULL,
    birth_date DATE NOT NULL,
    CONSTRAINT fk_user_profiles_mailing_addresses
        FOREIGN KEY (mailing_addresses_id)
        REFERENCES loans.mailing_addresses (mailing_addresses_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_user_profiles_accounts
        FOREIGN KEY (accounts_id)
        REFERENCES loans.accounts (accounts_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Fourth: Tables with most dependencies
-- -----------------------------------------------------
-- Table loans.loan_applications
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS loans.loan_applications (
    loan_applications_id SERIAL PRIMARY KEY,
    loan_type_id INTEGER NOT NULL,
    application_statuses_id INTEGER NOT NULL,
    user_profiles_id INTEGER NOT NULL,
    principal_balance NUMERIC NOT NULL,
    interest NUMERIC(10,2) NOT NULL,
    term_length INTEGER NOT NULL,
    total_balance NUMERIC NOT NULL,
    borrower VARCHAR(45) NOT NULL,
    application_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_loan_applications_loan_type
        FOREIGN KEY (loan_type_id)
        REFERENCES loans.loan_type (loan_type_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_loan_applications_application_statuses
        FOREIGN KEY (application_statuses_id)
        REFERENCES loans.application_statuses (application_statuses_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_loan_applications_user_profiles
        FOREIGN KEY (user_profiles_id)
        REFERENCES loans.user_profiles (user_profiles_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Finally: Add indexes and constraints
CREATE INDEX idx_accounts_usuario ON loans.accounts(usuario);
CREATE INDEX idx_user_profiles_last_name ON loans.user_profiles(last_name);
CREATE INDEX idx_loan_applications_application_date ON loans.loan_applications(application_date);

ALTER TABLE loans.user_profiles 
    ADD CONSTRAINT check_credit_score 
    CHECK (credit_score >= 300 AND credit_score <= 850);

ALTER TABLE loans.loan_applications 
    ADD CONSTRAINT check_positive_amounts 
    CHECK (principal_balance > 0 AND interest >= 0 AND term_length > 0);

COMMENT ON TABLE loans.accounts IS 'Stores user account credentials and types';
COMMENT ON TABLE loans.loan_applications IS 'Stores loan application details and status';