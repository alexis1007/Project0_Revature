package Application.Model;

public class LoanType {
    private int loanTypeId;
    private String loanType;

    // Default constructor
    public LoanType() {}

    // Parameterized constructor
    public LoanType(int loanTypeId, String loanType) {
        this.loanTypeId = loanTypeId;
        this.loanType = loanType;
    }

    // Constructor without ID for creation
    public LoanType(String loanType) {
        this.loanType = loanType;
    }

    // Getters
    public int getLoanTypeId() {
        return loanTypeId;
    }

    public String getLoanType() {
        return loanType;
    }

    // Setters
    public void setLoanTypeId(int loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    @Override
    public String toString() {
        return "LoanType{" +
                "loanTypeId=" + loanTypeId +
                ", loanType='" + loanType + '\'' +
                '}';
    }
}