package Application.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.Model.UserProfile;
import Application.Service.UserProfileService;
import io.javalin.http.Context;

public class UserProfilesController {
    private static final Logger logger = LoggerFactory.getLogger(UserProfilesController.class);
    private final UserProfileService userProfileService;

    public UserProfilesController() {
        this.userProfileService = new UserProfileService();
    }

    public void getAllProfilesHandler(Context ctx) {
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401).json("Unauthorized");
            return;
        }
        ctx.json(userProfileService.getAllUserProfiles());
    }

    public void getProfileByIdHandler(Context ctx) {
        try {
            Integer userId = ctx.sessionAttribute("user_id");
            if (userId == null) {
                ctx.status(401).json("Unauthorized");
                return;
            }

            int profileId = Integer.parseInt(ctx.pathParam("id"));
            UserProfile profile = userProfileService.getUserProfileById(profileId);
            
            if (profile != null) {
                // Only allow users to view their own profile unless they're an admin
                if (profile.getUserId() == userId || isAdmin(userId)) {
                    ctx.json(profile);
                } else {
                    ctx.status(403).json("Forbidden");
                }
            } else {
                ctx.status(404).json("Profile not found");
            }
        } catch (Exception e) {
            logger.error("Error getting profile", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void createProfileHandler(Context ctx) {
        try {
            Integer userId = ctx.sessionAttribute("user_id");
            if (userId == null) {
                ctx.status(401).json("Unauthorized");
                return;
            }

            UserProfile profile = ctx.bodyAsClass(UserProfile.class);
            profile.setUserId(userId); // Ensure profile is created for logged-in user
            
            UserProfile created = userProfileService.createUserProfile(profile);
            if (created != null) {
                ctx.status(201).json(created);
            } else {
                ctx.status(400).json("Invalid profile data");
            }
        } catch (Exception e) {
            logger.error("Error creating profile", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void updateProfileHandler(Context ctx) {
        try {
            Integer userId = ctx.sessionAttribute("user_id");
            if (userId == null) {
                ctx.status(401).json("Unauthorized");
                return;
            }

            int profileId = Integer.parseInt(ctx.pathParam("id"));
            UserProfile existingProfile = userProfileService.getUserProfileById(profileId);
            
            if (existingProfile == null) {
                ctx.status(404).json("Profile not found");
                return;
            }

            // Only allow users to update their own profile unless they're an admin
            if (existingProfile.getUserId() != userId && !isAdmin(userId)) {
                ctx.status(403).json("Forbidden");
                return;
            }

            UserProfile profile = ctx.bodyAsClass(UserProfile.class);
            userProfileService.updateUserProfile(profileId, profile);
            ctx.status(204);
        } catch (Exception e) {
            logger.error("Error updating profile", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void deleteProfileHandler(Context ctx) {
        try {
            Integer userId = ctx.sessionAttribute("user_id");
            if (userId == null) {
                ctx.status(401).json("Unauthorized");
                return;
            }

            int profileId = Integer.parseInt(ctx.pathParam("id"));
            UserProfile existingProfile = userProfileService.getUserProfileById(profileId);
            
            if (existingProfile == null) {
                ctx.status(404).json("Profile not found");
                return;
            }

            // Only allow users to delete their own profile unless they're an admin
            if (existingProfile.getUserId() != userId && !isAdmin(userId)) {
                ctx.status(403).json("Forbidden");
                return;
            }

            userProfileService.deleteUserProfile(profileId);
            ctx.status(204);
        } catch (Exception e) {
            logger.error("Error deleting profile", e);
            ctx.status(500).json("Internal server error");
        }
    }

    private boolean isAdmin(int userId) {
        // Implement admin check logic here
        // You might want to check the user_types_id in the users table
        return false; // Temporary implementation
    }
}
