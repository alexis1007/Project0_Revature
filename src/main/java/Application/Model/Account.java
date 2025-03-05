package Application.Model;

public class Account {
    private int id;
    private String username;
    private String password;
    private int accountType;

    public Account(){    }
    public Account(int id, String username, String password, int accountType){
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }
    public int getId(){        return id;    }
    public String getUsername(){        return username;    }
    public String getPassword() {        return password;    }
    public int getAccountType() {        return accountType;    }


    public void setId(int id){        this.id = id;    }
    public void setUsername(String username){        this.username = username;    }
    public void setPassword(String password) {        this.password = password;    }
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
