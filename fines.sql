USE library;

INSERT INTO fines(Loan_id, Fine_amt, Paid) 
	SELECT bl.Loan_id, datediff(curdate(),Due_date)*0.55, 0
	FROM book_loans as bl JOIN fines as f on bl.Loan_id = f.Loan_id
	where Date_in is null AND datediff(curdate(),Due_date) <0 AND (f.Paid='1' OR f.Paid is null) 
ON DUPLICATE KEY UPDATE    
Fine_amt =datediff(curdate(),Due_date)*0.50, Paid='0' ;

INSERT INTO fines(Loan_id, Fine_amt, Paid) 
	SELECT bl.Loan_id, datediff(Date_in,Due_date)*0.55, 0 
	FROM book_loans as bl JOIN fines as f on bl.Loan_id = f.Loan_id
	where Date_in is not null AND datediff(Date_in,Due_date) <0 AND (f.Paid='1' OR f.Paid is null)
ON DUPLICATE KEY UPDATE    
Fine_amt =datediff(Date_in,Due_date)*0.50, Paid='0' ;

SELECT * from fines;