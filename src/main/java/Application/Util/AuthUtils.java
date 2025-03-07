package Application.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.Service.UserService;
import io.javalin.http.Context;

public class AuthUtils {
    private static final Logger logger = LoggerFactory.getLogger(AuthUtils.class);

    public static boolean isAuthenticated(Context ctx) {
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401).json("Unauthorized");
            logger.warn("Unauthorized access attempt to: {}", ctx.path());
            return false;
        }
        return true;
    }

    public static boolean checkManager(Context ctx, UserService userService) {
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null || !userService.isManager(userId)) {
            ctx.status(403).json("Forbidden: Manager access required");
            logger.warn("Non-manager access attempt to: {}", ctx.path());
            return false;
        }
        return true;
    }

    public static boolean checkOwnerOrManager(Context ctx, UserService userService, int resourceOwnerId) {
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null || (userId != resourceOwnerId && !userService.isManager(userId))) {
            ctx.status(403).json("Forbidden: Not authorized");
            logger.warn("Unauthorized access attempt by user {} to resource owned by {}", userId, resourceOwnerId);
            return false;
        }
        return true;
    }
}