package Application.Model;

public class LoanApplications{
    private int idLoanApplication;
    private int loanType;
    private int applicationStatus;
    private int profileID;
    private float principalBalance;
    private float interest;
    private int termLength;
    private float totalBalance;
    private String borrower;
    private String applicationDate;

    private LoanApplications(){

    }
    private LoanApplications(int loanType, int applicationStatus,float principalBalance, float interest, String borrower, String applicationDate){
        this.loanType = loanType;
        this.applicationStatus = applicationStatus;
        this.principalBalance = principalBalance;
        this.interest = interest;
        this.borrower = borrower;
        this.applicationDate = applicationDate;
    }
    //get
    public int getIdLoanApplication() {        return idLoanApplication;    }
    public int getLoanType() {        return loanType;    }
    public int getApplicationStatus() {        return applicationStatus;    }
    public int getProfileID() {        return profileID;    }
    public float getPrincipalBalance() {        return principalBalance;    }
    public float getInterest() {        return interest;    }
    public int getTermLength() {        return termLength;    }
    public float getTotalBalance() {        return totalBalance;    }
    public String getBorrower() {        return borrower;    }
    public String getApplicationDate() {        return applicationDate;    }
    //set
    public void setIdLoanApplication(int idLoanApplication) {        this.idLoanApplication = idLoanApplication;    }
    public void setLoanType(int loanType) {        this.loanType = loanType;    }
    public void setApplicationStatus(String applicationStatus) {        this.applicationStatus = Integer.parseInt(applicationStatus);    }
    public void setProfileID(int profileID) {        this.profileID = profileID;    }
    public void setPrincipalBalance(float principalBalance) {        this.principalBalance = principalBalance;    }
    public void setInterest(float interest) {        this.interest = interest;    }
    public void setTermLength(int termLength) {        this.termLength = termLength;    }
    public void setTotalBalance(float totalBalance) {        this.totalBalance = totalBalance;   }
    public void setBorrower(String borrower) {        this.borrower = borrower;    }
    public void setApplicationDate(String applicationDate) {        this.applicationDate = applicationDate;    }

    @Override
    public String toString(){

        //pendiente
        return null;

    }
}

