package Application.Model;

public class accounts {
    private int id;
    private String username;
    private String password;
    private int accountType;

    public accounts(){    }
    public accounts(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
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
                '}';

    }
}
