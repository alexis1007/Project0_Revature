package Application.Model;

public class Accounts {
    private int id;
    private String username;
    private String password;
    private int accountType;

    public Accounts(){    }
    public Accounts(int id, String username, String password, int accountType){
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }
    public int getId(){        return id;    }
    public void setId(int id){        this.id = id;    }

    public String getUsername(){        return username;    }
    public void setUsername(String password){        this.username = username;    }

    public String getPassword() {        return password;    }
    public void setPassword(String password) {        this.password = password;    }

    public int getAccountType() {        return accountType;    }
    public void setAccountType(int accountType) {        this.accountType = accountType;    }

    @Override
    public String toString(){
        return "User{" +
                "id=" +id+
                ", user name='"+username+'\''+
                ", password='"+password+'\''+
                ", account type='"+accountType+'\''+
                '}';

    }
}
