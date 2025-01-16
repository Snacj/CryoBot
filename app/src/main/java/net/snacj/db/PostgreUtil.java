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
        String updateSQL = "UPDATE Leveling SET level = ? WHERE id = ?";
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
        String updateSQL = "UPDATE Leveling SET xp = xp + ? WHERE id = ?";
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
        String updateSQL = "UPDATE RPG SET credits = credits + ? WHERE id = ?";
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
    public void updateUserEffect(Long userId, String effect) {
        System.out.println(LogConstants.I + "Executing update for user ID: " + userId + " with " + effect);
        String updateSQL = "UPDATE RPG SET effect = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, effect);
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
        String updateSQL = "UPDATE RPG SET dailycredits = TRUE WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method updates the character of a user in the database.
     */
    public void updateCharacter(Long userId, String name, String klasse, String race, int age) {
        System.out.println(LogConstants.I + "Executing updcate for user ID: " + userId);
        String updateSQL = "UPDATE character SET name = ?, class = ?, race = ?, age = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, klasse);
            preparedStatement.setString(3, race);
            preparedStatement.setInt(4, age);
            preparedStatement.setLong(5, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method retrieves the level of a user from the database.
     */
    public int getLevelFromUser(long userId) {
        int level = -1;
        String selectSQL = "SELECT level FROM Leveling WHERE id = ?";
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
     * This method retrieves the experience points of a user from the database.
     */
    public long getXpFromUser(long userId) {
        long xp = -1;
        String selectSQL = "SELECT xp FROM Leveling WHERE id = ?";
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
        String selectSQL = "SELECT credits FROM RPG WHERE id = ?";
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
        String selectSQL = "SELECT location FROM location WHERE id = ?";
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
        String effect = "-1";
        String selectSQL = "SELECT effect FROM RPG WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    effect = resultSet.getString("effect");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return effect;
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
    public String[] getChracterFromUser(long userId){
        String[] character = {"", "", "", "", ""};
        String id;
        String name;
        String age;
        String klasse;
        String race;
        String selectSQL = "SELECT * FROM character WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getString("id");
                    name = resultSet.getString("name");
                    age = resultSet.getString("age");
                    klasse = resultSet.getString("class");
                    race = resultSet.getString("race");
                    character[0] = id;
                    character[1] = name;
                    character[2] = age;
                    character[3] = klasse;
                    character[4] = race;
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
        String updateSQL = "UPDATE location SET location = 'Taverne'";
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
        String updateSQL = "UPDATE rpg SET dailycredits = false";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
