package Application.Model;

public class LoanApplications{
    private int idLoanApplication;
    private int loanType;
    private String applicationStatus;
    private float principalBalance;
    private float interest;
    private String borrower;
    private String applicationDate;

    private LoanApplications(){

    }
    private LoanApplications(int loanType, String applicationStatus,float principalBalance, float interest, String borrower, String applicationDate){
        this.loanType = loanType;
        this.applicationStatus = applicationStatus;
        this.principalBalance = principalBalance;
        this.interest = interest;
        this.borrower = borrower;
        this.applicationDate = applicationDate;
    }
    //get
    public float getInterest() {        return interest;    }
    public float getPrincipalBalance() {        return principalBalance;    }
    public int getIdLoanApplication() {        return idLoanApplication;    }
    public int getLoanType() {        return loanType;    }
    public String getApplicationDate() {        return applicationDate;    }
    public String getApplicationStatus() {        return applicationStatus;    }
    public String getBorrower() {        return borrower;    }
    //set
    public void setApplicationDate(String applicationDate) {        this.applicationDate = applicationDate;    }
    public void setApplicationStatus(String applicationStatus) {        this.applicationStatus = applicationStatus;    }
    public void setBorrower(String borrower) {        this.borrower = borrower;    }
    public void setIdLoanApplication(int idLoanApplication) {        this.idLoanApplication = idLoanApplication;    }
    public void setInterest(float interest) {        this.interest = interest;    }
    public void setLoanType(int loanType) {        this.loanType = loanType;    }
    public void setPrincipalBalance(float principalBalance) {        this.principalBalance = principalBalance;    }
    @Override
    public String toString(){

        //pendiente
        return null;

    }
}

