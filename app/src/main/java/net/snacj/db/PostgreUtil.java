package net.snacj.db;

import net.snacj.util.LogConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * This class is responsible for updating and retrieving data from the PostgreSQL database.
 */
public class PostgreUtil {
    PostgreSQLConnection dbConnection = PostgreSQLConnection.getInstance();
    Connection connection = dbConnection.getConnection();

    /*
     * This method updates the level of a user in the database.
     */
    public void updateUserLevel(Long userId, int level) {
        System.out.println(LogConstants.I + "Executing update for user ID: " + userId + " with level " + level);
        String updateSQL = "UPDATE User SET level = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setInt(1, level);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method updates the experience points of a user in the database.
     */
    public void updateUserTime(Long userId, long seconds) {
        System.out.println(LogConstants.I + "Executing update for user ID: " + userId + " with " + seconds + " seconds.");
        String updateSQL = "UPDATE User SET xp = xp + ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setLong(1, seconds);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method updates the credits of a user in the database.
     */
    public void updateUserCredits(Long userId, long credits) {
        System.out.println(LogConstants.I + "Executing update for user ID: " + userId + " with " + credits + " credits.");
        String updateSQL = "UPDATE User SET credits = credits + ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setLong(1, credits);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method updates the location of a user in the database.
     */
    public void updateUserLocation(Long userId, String location) {
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
     * This method updates the effect of a user in the database.
     */
    public void updateUserEffect(Long userId, String status) {
        System.out.println(LogConstants.I + "Executing update for user ID: " + userId + " with " + status);
        String updateSQL = "UPDATE User SET effect = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method updates the daily credits of a user in the database.
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
     * This method retrieves the experience points of a user from the database.
     */
    public long getXpFromUser(long userId) {
        long xp = -1;
        String selectSQL = "SELECT xp FROM User WHERE id = ?";
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
     * This method retrieves the credits of a user from the database.
     */
    public long getCreditsFromUser(long userId) {
        long credits = -1;
        String selectSQL = "SELECT credits FROM User WHERE id = ?";
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
     * This method retrieves the location of a user from the database.
     */
    public String getLocationFromUser(long userId) {
        String location = "-1";
        String selectSQL = "SELECT location FROM User WHERE id = ?";
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
     * This method retrieves the effect of a user from the database.
     */
    public String getEffectFromUser(long userId) {
        String status = "-1";
        String selectSQL = "SELECT status FROM User WHERE id = ?";
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
     * This method retrieves the daily credits of a user from the database.
     */
    public boolean getDailyCreditsFromUser(long userId) {
        boolean dailyCredits = false;
        String selectSQL = "SELECT dailycredits FROM RPG WHERE id = ?";
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
     * This method retrieves the character of a user from the database.
     */
    public String[] getCharacterFromUser(long userId){
        String[] character = {"", "", "", "", ""};
        String id;
        String name;
        String age;
        String level;
        String species;
        String profession;
        String status;
        String shipAssignment;
        String location;

        String selectSQL = "SELECT * FROM User WHERE id = ?";
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
     * This method resets the location of all users in the database.
     */
    public void resetUserLocation() {
        System.out.println(LogConstants.I + "Executing update for all users");
        String updateSQL = "UPDATE User SET location = 'Orion Space Station'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method resets the daily credits boolean to false.
     */
    public void resetDailyCredits() {
        System.out.println(LogConstants.I + "Executing update for all users");
        String updateSQL = "UPDATE User SET dailycredits = false";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
