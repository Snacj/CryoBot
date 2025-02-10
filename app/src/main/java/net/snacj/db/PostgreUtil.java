package net.snacj.db;

import net.snacj.util.LogConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Utility class for PostgreSQL database operations
 */
public class PostgreUtil {
    PostgreSQLConnection dbConnection = PostgreSQLConnection.getInstance();
    Connection connection = dbConnection.getConnection();

    /*
     * updates the level of a user in the database.
     */
    public void updateMemberLevel(Long userId, int level) {
        System.out.println(LogConstants.I + "Executing update for user ID: " + userId + " with level " + level);
        String updateSQL = "UPDATE Member SET level = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setInt(1, level);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * updates the experience points of a user in the database.
     */
    public void updateMemberTime(Long userId, long seconds) {
        System.out
                .println(LogConstants.I + "Executing update for user ID: " + userId + " with " + seconds + " seconds.");
        String updateSQL = "UPDATE Member SET xp = xp + ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setLong(1, seconds);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * updates the credits of a user in the database.
     */
    public void updateMemberCredits(Long userId, long credits) {
        System.out
                .println(LogConstants.I + "Executing update for user ID: " + userId + " with " + credits + " credits.");
        String updateSQL = "UPDATE Member SET credits = credits + ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setLong(1, credits);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * updates the location of a user in the database.
     */
    public void updateMemberLocation(Long userId, String location) {
        System.out.println(LogConstants.I + "Executing update for user ID: " + userId + " with " + location);
        String updateSQL = "UPDATE location SET location = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, location);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * updates the effect of a user in the database.
     */
    public void updateMemberEffect(Long userId, String status) {
        System.out.println(LogConstants.I + "Executing update for user ID: " + userId + " with " + status);
        String updateSQL = "UPDATE Member SET effect = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * updates the daily credits of a user in the database.
     */
    public void updateDailyCredits(Long userId) {
        System.out.println(LogConstants.I + "Executing update for user ID: " + userId);
        String updateSQL = "UPDATE Use SET dailycredits = TRUE WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * retrieves the level of a user from the database.
     */
    public int getLevelFromMember(long userId) {
        int level = -1;
        String selectSQL = "SELECT level FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    level = resultSet.getInt("level");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return level;
    }

    /*
     * retrieves the experience points of a user from the database.
     */
    public long getXpFromMember(long userId) {
        long xp = -1;
        String selectSQL = "SELECT xp FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    xp = resultSet.getInt("xp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xp;
    }

    /*
     * retrieves the credits of a user from the database.
     */
    public long getCreditsFromMember(long userId) {
        long credits = -1;
        String selectSQL = "SELECT credits FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    credits = resultSet.getInt("credits");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return credits;
    }

    /*
     * retrieves the location of a user from the database.
     */
    public String getLocationFromMember(long userId) {
        String location = "-1";
        String selectSQL = "SELECT location FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    location = resultSet.getString("location");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return location;
    }

    /*
     * retrieves the effect of a user from the database.
     */
    public String getEffectFromMember(long userId) {
        String status = "-1";
        String selectSQL = "SELECT status FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    status = resultSet.getString("status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    /*
     * retrieves the daily credits of a user from the database.
     */
    public boolean getDailyCreditsFromMember(long userId) {
        boolean dailyCredits = false;
        String selectSQL = "SELECT dailycredits FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    dailyCredits = resultSet.getBoolean("dailycredits");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dailyCredits;
    }

    /*
     * retrieves the character of a user from the database.
     */
    public String[] getCharacterFromMember(long userId) {
        String[] character = { "", "", "", "", "" };
        String id;
        String name;
        String age;
        String level;
        String species;
        String profession;
        String status;
        String shipAssignment;
        String location;

        String selectSQL = "SELECT * FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getString("id");
                    name = resultSet.getString("name");
                    age = resultSet.getString("age");
                    level = resultSet.getString("level");
                    species = resultSet.getString("species");
                    profession = resultSet.getString("profession");
                    status = resultSet.getString("status");
                    shipAssignment = resultSet.getString("shipAssignment");
                    location = resultSet.getString("location");

                    character[0] = id;
                    character[1] = name;
                    character[2] = age;
                    character[3] = level;
                    character[4] = species;
                    character[5] = profession;
                    character[6] = status;
                    character[7] = shipAssignment;
                    character[8] = location;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return character;
    }

    /*
     * resets the location of all users in the database.
     */
    public void resetMemberLocation() {
        System.out.println(LogConstants.I + "Executing update for all users");
        String updateSQL = "UPDATE Member SET location = 'Orion Space Station'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * resets the daily credits boolean to false.
     */
    public void resetDailyCredits() {
        System.out.println(LogConstants.I + "Executing update for all users");
        String updateSQL = "UPDATE Member SET dailycredits = false";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
