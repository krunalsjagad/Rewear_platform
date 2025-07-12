package utils;

import dao.UserDAO;

/**
 * Centralizes point-awarding and -refunding logic.
 */
public class PointManager {
    private static final UserDAO userDAO = new UserDAO();

    /**
     * Award points to a user.
     * @param userId the recipient user ID
     * @param points number of points to add (positive)
     * @return true if DB update succeeded
     */
    public static boolean awardPoints(int userId, int points) {
        return userDAO.updatePoints(userId, points);
    }

    /**
     * Deduct points from a user.
     * @param userId the user spending points
     * @param points number of points to subtract (positive)
     * @return true if DB update succeeded
     */
    public static boolean deductPoints(int userId, int points) {
        // note: updatePoints uses delta so we pass -points
        return userDAO.updatePoints(userId, -points);
    }

    /**
     * Refund points to a user after a swap is rejected or expired.
     * @param userId the user to refund
     * @param points number of points to refund (positive)
     * @return true if DB update succeeded
     */
    public static boolean refundPoints(int userId, int points) {
        return userDAO.updatePoints(userId, points);
    }
}
