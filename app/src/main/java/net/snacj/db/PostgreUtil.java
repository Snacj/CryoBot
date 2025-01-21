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
     * This method updates the level of a Member in the database.
     */
    public void updateMemberLevel(Long MemberId, int level) {
        System.out.println(LogConstants.I + "Executing update for Member ID: " + MemberId + " with level " + level);
        String updateSQL = "UPDATE Member SET level = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setInt(1, level);
            preparedStatement.setLong(2, MemberId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method updates the experience points of a Member in the database.
     */
    public void updateMemberTime(Long MemberId, long seconds) {
        System.out.println(LogConstants.I + "Executing update for Member ID: " + MemberId + " with " + seconds + " seconds.");
        String updateSQL = "UPDATE Member SET xp = xp + ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setLong(1, seconds);
            preparedStatement.setLong(2, MemberId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method updates the credits of a Member in the database.
     */
    public void updateMemberCredits(Long MemberId, long credits) {
        System.out.println(LogConstants.I + "Executing update for Member ID: " + MemberId + " with " + credits + " credits.");
        String updateSQL = "UPDATE Member SET credits = credits + ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setLong(1, credits);
            preparedStatement.setLong(2, MemberId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method updates the location of a Member in the database.
     */
    public void updateMemberLocation(Long MemberId, String location) {
        System.out.println(LogConstants.I + "Executing update for Member ID: " + MemberId + " with " + location);
        String updateSQL = "UPDATE location SET location = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, location);
            preparedStatement.setLong(2, MemberId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method updates the effect of a Member in the database.
     */
    public void updateMemberEffect(Long MemberId, String status) {
        System.out.println(LogConstants.I + "Executing update for Member ID: " + MemberId + " with " + status);
        String updateSQL = "UPDATE Member SET effect = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, MemberId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method updates the daily credits of a Member in the database.
     */
    public void updateDailyCredits(Long MemberId) {
        System.out.println(LogConstants.I + "Executing update for Member ID: " + MemberId);
        String updateSQL = "UPDATE Use SET dailycredits = TRUE WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setLong(1, MemberId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method retrieves the experience points of a Member from the database.
     */
    public long getXpFromMember(long MemberId) {
        long xp = -1;
        String selectSQL = "SELECT xp FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, MemberId);
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
     * This method retrieves the credits of a Member from the database.
     */
    public long getCreditsFromMember(long MemberId) {
        long credits = -1;
        String selectSQL = "SELECT credits FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, MemberId);
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
     * This method retrieves the location of a Member from the database.
     */
    public String getLocationFromMember(long MemberId) {
        String location = "-1";
        String selectSQL = "SELECT location FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, MemberId);
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
     * This method retrieves the effect of a Member from the database.
     */
    public String getEffectFromMember(long MemberId) {
        String status = "-1";
        String selectSQL = "SELECT status FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, MemberId);
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
     * This method retrieves the daily credits of a Member from the database.
     */
    public boolean getDailyCreditsFromMember(long MemberId) {
        boolean dailyCredits = false;
        String selectSQL = "SELECT dailycredits FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, MemberId);
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
     * This method retrieves the character of a Member from the database.
     */
    public String[] getCharacterFromMember(long MemberId){
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

        String selectSQL = "SELECT * FROM Member WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, MemberId);
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
     * This method resets the location of all Members in the database.
     */
    public void resetMemberLocation() {
        System.out.println(LogConstants.I + "Executing update for all Members");
        String updateSQL = "UPDATE Member SET location = 'Orion Space Station'";
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
        System.out.println(LogConstants.I + "Executing update for all Members");
        String updateSQL = "UPDATE Member SET dailycredits = false";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
