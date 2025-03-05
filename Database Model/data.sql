-- Insert account types
INSERT INTO loans.account_types (account_type) VALUES
    ('ADMIN'),
    ('USER'),
    ('MANAGER');

-- Insert application statuses
INSERT INTO loans.application_statuses (application_statuses) VALUES
    ('PENDING'),
    ('APPROVED'),
    ('REJECTED'),
    ('REVIEW');

-- Insert loan types
INSERT INTO loans.loan_type (loan_type) VALUES
    ('PERSONAL'),
    ('AUTO'),
    ('HOME'),
    ('BUSINESS');

-- Insert mailing addresses
INSERT INTO loans.mailing_addresses (street, city, state, zip, country) VALUES
    ('123 Main St', 'New York', 'NY', '10001', 'USA'),
    ('456 Oak Ave', 'Los Angeles', 'CA', '90001', 'USA'),
    ('789 Pine Rd', 'Chicago', 'IL', '60601', 'USA'),
    ('321 Maple Dr', 'Houston', 'TX', '77001', 'USA');

-- Insert accounts
INSERT INTO loans.accounts (username, password, account_types_id) VALUES
    ('admin', 'admin123', 1),
    ('john.doe', 'pass123', 2),
    ('jane.smith', 'pass456', 2),
    ('manager', 'manager123', 3);

-- Insert user profiles
INSERT INTO loans.user_profiles (accounts_id, mailing_addresses_id, first_name, last_name, phone_number, credit_score, birth_date) VALUES
    (1, 1, 'Admin', 'User', '555-0100', 800, '1980-01-01'),
    (2, 2, 'John', 'Doe', '555-0200', 720, '1985-05-15'),
    (3, 3, 'Jane', 'Smith', '555-0300', 680, '1990-08-22'),
    (4, 4, 'Manager', 'User', '555-0400', 750, '1982-03-10');

-- Insert loan applications
INSERT INTO loans.loan_applications (
    loan_type_id, 
    application_statuses_id, 
    user_profiles_id, 
    principal_balance, 
    interest, 
    term_length, 
    total_balance, 
    borrower, 
    application_date
) VALUES
    (1, 1, 2, 5000.00, 5.5, 12, 5275.00, 'John Doe', CURRENT_TIMESTAMP),
    (2, 2, 2, 25000.00, 4.5, 48, 28125.00, 'John Doe', CURRENT_TIMESTAMP - INTERVAL '5 days'),
    (3, 3, 3, 300000.00, 3.5, 360, 484773.00, 'Jane Smith', CURRENT_TIMESTAMP - INTERVAL '10 days'),
    (4, 4, 3, 50000.00, 6.0, 24, 56000.00, 'Jane Smith', CURRENT_TIMESTAMP - INTERVAL '2 days');

-- Verify data insertion
SELECT 'Account Types' as table_name, COUNT(*) as record_count FROM loans.account_types
UNION ALL
SELECT 'Application Statuses', COUNT(*) FROM loans.application_statuses
UNION ALL
SELECT 'Loan Types', COUNT(*) FROM loans.loan_type
UNION ALL
SELECT 'Mailing Addresses', COUNT(*) FROM loans.mailing_addresses
UNION ALL
SELECT 'Accounts', COUNT(*) FROM loans.accounts
UNION ALL
SELECT 'User Profiles', COUNT(*) FROM loans.user_profiles
UNION ALL
SELECT 'Loan Applications', COUNT(*) FROM loans.loan_applications;