package Application.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    private int userId;
    private String username;
    private boolean isManager;

    public LoginResponse(int userId, String username, boolean isManager) {
        this.userId = userId;
        this.username = username;
        this.isManager = isManager;
    }

    // Getters
    @JsonProperty("user_id")
    public int getUserId() { 
        return userId; 
    }

    public String getUsername() { 
        return username; 
    }

    @JsonProperty("is_manager")
    public boolean isManager() { 
        return isManager; 
    }

    // Setters
    public void setUserId(int userId) { 
        this.userId = userId; 
    }

    public void setUsername(String username) { 
        this.username = username; 
    }

    public void setManager(boolean manager) { 
        isManager = manager; 
    }
}