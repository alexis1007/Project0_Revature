package Application.Model;

public class accounts {
    private int id;
    private String username;
    private String password;

    public accounts(){

    }
    public accounts(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getUsername(){
        return username;

    }
    public void setUsername(String password){
        this.username = username;
    }
    @Override
    public String toString(){
        return "User{" +
                "id=" + id;

    }
}
