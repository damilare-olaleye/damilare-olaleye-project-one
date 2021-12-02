DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS reimbursement;

CREATE EXTENSION pgcrypto;

CREATE TABLE users (
	
	user_id SERIAL PRIMARY KEY,
	employee_username VARCHAR(255) UNIQUE NOT NULL,
	employee_password VARCHAR(255) NOT NULL,
	user_first_name VARCHAR(255) NOT NULL,
	user_last_name VARCHAR(255) NOT NULL,
	user_email VARCHAR(255) UNIQUE NOT NULL,
	user_role VARCHAR(255)

);

SELECT * FROM users;

SELECT * FROM users WHERE employee_username = 'json1';

INSERT INTO users (employee_username, employee_password, user_first_name, user_last_name, user_email, user_role) 
	VALUES ('olalede','justPass','Damilare','Olaleye','dammyo@yahoo.com','Employee');

INSERT INTO users (employee_username, employee_password, user_first_name, user_last_name, user_email, user_role) 
	VALUES ('jterry','pass123','John','Terry','jterry.com','Employee');

INSERT INTO users (employee_username, employee_password, user_first_name, user_last_name, user_email, user_role) 
	VALUES ('jmourinho','pass245','Jose','Mourinho','jmourinho@gmail.com','Employee');

INSERT INTO users (employee_username, employee_password, user_first_name, user_last_name, user_email, user_role) 
	VALUES ('marteta','pass000','Mikel','Arteta','marteta@yahoo.com','Finance Manager');

INSERT INTO users (employee_username, employee_password, user_first_name, user_last_name, user_email, user_role) 
	VALUES ('olalede1','justPass01','Damilare01','Olaleye01','dammyo01@yahoo.com','Finance Manager');

SELECT * FROM users;

CREATE TABLE reimbursement (
	reimbursement_id SERIAL PRIMARY KEY, 
	reimbursement_submitted TIMESTAMP NOT NULL,
	reimbursement_resolved TIMESTAMP,
	reimbursement_status VARCHAR(25) NOT NULL DEFAULT 'PENDING',
	reimbursement_type VARCHAR(55) NOT NULL,
	reimbursement_description VARCHAR(255) NOT NULL,
	reimbursement_amount NUMERIC(12,2) NOT NULL,
	reimbursement_receipt BYTEA NOT NULL,
	reimbursement_author INTEGER,
	reimbursement_resolver INTEGER,
		
	CONSTRAINT fK_reimb_author FOREIGN KEY (reimbursement_author)
		REFERENCES users(user_id),
		
	CONSTRAINT fk_reimb_resolver FOREIGN KEY (reimbursement_resolver)
		REFERENCES users(user_id)
	
);

SELECT reimbursement_status, reimbursement_resolver, reimbursement_type 
					reimbursement_description FROM reimbursement WHERE reimbursement_id = '1';
				
SELECT * FROM Reimbursement WHERE reimbursement_id = '2';

SELECT * FROM reimbursement WHERE user_id = '1';

SELECT * FROM reimbursement WHERE reimbursement_status = 'APPROVED' ORDER BY reimbursement_submitted;

SELECT * FROM reimbursement WHERE reimbursement_id=1;

SELECT * FROM reimbursement ORDER BY reimbursement_submitted = ;

SELECT reimbursement_status FROM reimbursement WHERE reimbursement_status = 'PENDING';

SELECT reimbursement_status FROM reimbursement WHERE reimbursement_status = 'PENDING' AND reimbursement_id ='1';

SELECT * FROM reimbursement;
/* pg_read_binary_file('/Users/damilare.olaleye/Downloads/lodge.jpeg') */

/* insert into reimbursement */
INSERT INTO reimbursement (reimbursement_id, reimbursement_submitted, reimbursement_resolved, reimbursement_status, 
reimbursement_status_ID, reimbursement_type, reimbursement_description, reimbursement_amount, reimbursement_receipt, 
reimbursement_author, reimbursement_resolver, reimbursement_resolver_ID, user_id) 
VALUES (1, '11-26-2021', '11-27-2021', 'Pending', 1, 'TRAVEL', 'Went eating with for team bonding', '$1240',
		'', 1, 1, 1, 3);

INSERT INTO reimbursement (reimbursement_id, reimbursement_submitted, reimbursement_resolved, reimbursement_status, 
reimbursement_status_ID, reimbursement_type, reimbursement_description, reimbursement_amount, reimbursement_receipt, 
reimbursement_author, reimbursement_resolver, reimbursement_resolver_ID, user_id) 
VALUES (2, '11-25-2021', '11-26-2021', 'Approved', 1, 'LODGING', 'Went lodging with hommies', '$2400',
		'', 1, 1, 1, 1);
	
INSERT INTO reimbursement (reimbursement_id, reimbursement_submitted, reimbursement_resolved, reimbursement_status, 
reimbursement_status_ID, reimbursement_type, reimbursement_description, reimbursement_amount, reimbursement_receipt, 
reimbursement_author, reimbursement_resolver, reimbursement_resolver_ID, user_id) 
VALUES (3, '11-25-2021', '11-28-2021', 'Denied', 1, 'LODGING', 'Went lodging with hommies', '$12400',
		'', 1, 1, 1, 2);
	
INSERT INTO reimbursement (reimbursement_id, reimbursement_submitted, reimbursement_resolved, reimbursement_status, 
reimbursement_status_ID, reimbursement_type, reimbursement_description, reimbursement_amount, reimbursement_receipt, 
reimbursement_author, reimbursement_resolver, reimbursement_resolver_ID, user_id) 
VALUES (4, '11-28-2021', '11-29-2021', 'Approved', 1, 'OTHER', 'Went fishing with families', '$400',
		'', 1, 1, 1, 4);
	
INSERT INTO reimbursement (reimbursement_id, reimbursement_submitted, reimbursement_resolved, reimbursement_status, 
reimbursement_status_ID, reimbursement_type, reimbursement_description, reimbursement_amount, reimbursement_receipt, 
reimbursement_author, reimbursement_resolver, reimbursement_resolver_ID, user_id) 
VALUES (5, '11-28-2021', '11-29-2021', 'Approved', 1, 'TRAVEL', 'Went fishing with families', '$400',
		'', 1, 1, 1, 1);
	


/* get all reimbursements */
SELECT *  
FROM reimbursement;

/* get reimbursement by id */
SELECT reimbursement_id, reimbursement_submitted, reimbursement_resolved, reimbursement_status, reimbursement_type, 
	   reimbursement_description, reimbursement_amount, reimbursement_receipt, reimbursement_author, 
	   reimbursement_resolver, user_id 
	   FROM reimbursement 
	   WHERE reimbursement_id = '2';
	  
/* get reimbursement by user */
SELECT reimbursement_id, reimbursement_submitted, reimbursement_resolved, reimbursement_status, 
		reimbursement_type, reimbursement_description, reimbursement_amount
		reimbursement_receipt, reimbursement_author, reimbursement_resolver, user_id
		FROM reimbursement
		WHERE user_id = '1';

/* add reimbursement */
INSERT INTO reimbursement (reimbursement_type, reimbursement_amount, reimbursement_description, 
			reimbursement_submitted, reimbursement_receipt) 
		VALUES ('LODGING','$1200','Attend vetrans event with co-workers','11-26-2021',?);

/* get receipt */
SELECT reimbursement_receipt 
FROM reimbursement 
WHERE reimbursement_id = '2';

/* update reimbursement */
UPDATE reimbursement
SET reimbursement_status = 'APPROVED', reimbursement_resolver = '1', reimbursement_resolved = '11-25-2021' 
WHERE reimbursement_id = 2;

/* resolve reimbursement */
UPDATE reimbursement 
SET reimbursement_status_ID = '1', reimbursement_resolved = '11-25-2021'
WHERE reimbursement_id = '2';

/* get current user reimbursement */
SELECT reimbursement_id, reimbursement_submitted, reimbursement_resolved, reimbursement_status, reimbursement_type, 
	   reimbursement_description, reimbursement_amount, reimbursement_receipt, 
	   reimbursement_author, reimbursement_resolver, user_id 
FROM reimbursement;

/* get image from reimbursement by id */
SELECT reimbursement_receipt 
FROM reimbursement
WHERE reimbursement_id = '2';

/* change status */
UPDATE reimbursement  
SET reimbursement_status = 'Denied',  
	reimbursement_status_ID = '1'
WHERE user_id = '1';

/* get reimbursement status */
SELECT * 
FROM reimbursement 
WHERE reimbursement_status = 'Pending' AND reimbursement_resolver_ID = '1';

/* get reimbursement type */
SELECT reimbursement_type 
FROM reimbursement
WHERE reimbursement_id = '1';

/* request employee history */
SELECT * FROM reimbursement 
WHERE user_id = 1;

/* display employee history */
SELECT * FROM reimbursement 
WHERE user_id = 1;

/* approve reimbursement */
UPDATE reimbursement 
SET reimbursement_status = 'Approved' 
WHERE reimbursement_id = 2;

/* reject reimbursement */
UPDATE reimbursement 
SET reimbursement_status = 'Denied' 
WHERE reimbursement_id = 1;

/* Select all */
SELECT * FROM reimbursement;


INSERT INTO users (employee_username, employee_password, user_first_name, user_last_name, user_email, user_role) 
	VALUES ('username01', crypt('justPass01', gen_salt('bf')),'Damilare01','Olaleye01','username01@yahoo.com','Finance Manager');

SELECT * FROM users;