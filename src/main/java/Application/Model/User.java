package Application.Model;

public class User {
    private int id;
    private String username; 
    private String password;
    private int userTypeId;

    public User(){    }
    public User(int id, String username, String password, int userTypeId){
        this.id = id;
        this.username = username;
        this.password = password;
        this.userTypeId = userTypeId;
    }

     // Getters
    public int getId(){        return id;    }
    public String getUsername(){        return username;    }
    public String getPassword() {        return password;    }
    public int getUserTypeId() { return userTypeId; }

    // Setters
    public void setId(int id){        this.id = id;    }
    public void setUsername(String username){        this.username = username;    }
    public void setPassword(String password) {        this.password = password;    }
    public void setUserTypeId(int userTypeId) { this.userTypeId = userTypeId; }

    @Override
    public String toString(){
        return "User{" +
                "id=" +id+
                ", user name='"+username+'\''+
                ", password='"+password+'\''+
                ", account type='"+userTypeId+'\''+
                '}';

    }
}
