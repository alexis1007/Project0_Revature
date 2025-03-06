package Application.Model;

import java.time.LocalDateTime;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
    private int userId;
    private String username;
    
    // For receiving raw password @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password; 
    
    // For storing hashed password @JsonIgnore
    private String passwordHash; 
    
    private int userTypeId;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private boolean isActive;

    // Default constructor
    public User() {}

    // Constructor for creating new users
    public User(String username, String passwordHash, int userTypeId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.userTypeId = userTypeId;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    // Full constructor
    public User(int userId, String username, String passwordHash, int userTypeId,
                LocalDateTime createdAt, LocalDateTime lastLogin, boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.userTypeId = userTypeId;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.isActive = isActive;
    }

    // Getters and setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public int getUserTypeId() { return userTypeId; }
    public void setUserTypeId(int userTypeId) { this.userTypeId = userTypeId; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    // Add new getter/setter for password
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
