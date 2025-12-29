package model;

public class UserModel {

    private int user_id;          // from MySQL
    private String username;
    private String password;
    private String confirmpassword;

    // Constructor for SIGNUP (no user_id yet)
    public UserModel(String username, String password, String confirmpassword) {
        this.username = username;
        this.password = password;
        this.confirmpassword = confirmpassword;
    }

    // Constructor for LOGIN / DB (with user_id + password)
    public UserModel(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }
    
    

    // GETTERS & SETTERS
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    // Make sure this actually sets password
    public void setPassword(String password) {
        this.password = password;
    }

    public String getconfirmpassword() {
        return confirmpassword;
    }

    public void setconfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

}