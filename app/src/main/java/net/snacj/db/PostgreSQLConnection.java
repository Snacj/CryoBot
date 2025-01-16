package net.snacj.db;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import net.snacj.util.LogConstants;

import java.sql.*;

public class PostgreSQLConnection extends ListenerAdapter {
    Dotenv dotenv = Dotenv.load();
    final String URL = dotenv.get("db_url");
    final String USERNAME = dotenv.get("db_user");
    final String PASSWORD = dotenv.get("db_pass");
    private Connection connection;
    private static PostgreSQLConnection instance;

    private PostgreSQLConnection() {
        initialize();
    }

    private void initialize() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println(LogConstants.K + "PostgreSQL JDBC Driver Registered!");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (connection != null) {
                System.out.println(LogConstants.K + "Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println(LogConstants.E + "Failed to make connection!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println(LogConstants.E + "PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(LogConstants.E + "Connection failure.");
            e.printStackTrace();
        }
    }

    public static PostgreSQLConnection getInstance() {
        if (instance == null) {
            instance = new PostgreSQLConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println(LogConstants.K + "Connection closed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initDB(@NotNull GuildReadyEvent event) {
        createTableIfNotExists(memberTable());
        createTableIfNotExists(levelingTable());
        createTableIfNotExists(locationTable());
        createTableIfNotExists(rPGTable());
        createTableIfNotExists(characterTable());
        event.getJDA().getGuilds().forEach(guild -> guild.loadMembers().onSuccess(members -> {
            String insertMemberSQL = "INSERT INTO Member (id, username, readname) VALUES (?, ?, ?) " +
                    "ON CONFLICT (id) DO NOTHING";
            String insertLevelingSQL = "INSERT INTO leveling (id) VALUES (?) " +
                    "ON CONFLICT (id) DO NOTHING";
            String insertRPGSQL = "INSERT INTO RPG (id) VALUES (?) " +
                    "ON CONFLICT (id) DO NOTHING";
            String insertLocationSQL = "INSERT INTO location (id) VALUES (?) " +
                    "ON CONFLICT (id) DO NOTHING";
            String insertCharacterSQL = "INSERT INTO character (id) VALUES (?) " +
                    "ON CONFLICT (id) DO NOTHING";

            try (PreparedStatement memberStatement = connection.prepareStatement(insertMemberSQL);
                 PreparedStatement levelingStatement = connection.prepareStatement(insertLevelingSQL);
                 PreparedStatement rpgStatement = connection.prepareStatement(insertRPGSQL);
                 PreparedStatement locationStatement = connection.prepareStatement(insertLocationSQL);
                 PreparedStatement characterStatement = connection.prepareStatement(insertCharacterSQL)) {
                for (var member : members) {
                    if (!member.getUser().isBot()) {
                        long memberId =  member.getIdLong();
                        String username = member.getUser().getName();
                        String readname = member.getEffectiveName();

                        memberStatement.setLong(1, memberId);
                        memberStatement.setString(2, username);
                        memberStatement.setString(3, readname);
                        memberStatement.addBatch();

                        levelingStatement.setLong(1, memberId);
                        levelingStatement.addBatch();

                        rpgStatement.setLong(1, memberId);
                        rpgStatement.addBatch();

                        locationStatement.setLong(1, memberId);
                        locationStatement.addBatch();

                        characterStatement.setLong(1, memberId);
                        characterStatement.addBatch();
                    }
                }

                memberStatement.executeBatch();
                levelingStatement.executeBatch();
                locationStatement.executeBatch();
                rpgStatement.executeBatch();
                characterStatement.executeBatch();

                System.out.println(LogConstants.I + "Members added to the database for guild: " + guild.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).onError(throwable -> {
            System.err.println(LogConstants.E + "Failed to load members for guild: " + guild.getName());
            throwable.printStackTrace();
        }));
    }

    public void createTableIfNotExists(String createTableSQL) {

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String memberTable() {
        return "CREATE TABLE IF NOT EXISTS Member ("
                + "id BIGINT PRIMARY KEY, "
                + "username VARCHAR(255), "
                + "readname VARCHAR(255)"
                + ")";
    }

    public String levelingTable() {
        return "CREATE TABLE IF NOT EXISTS leveling ("
                + "id BIGINT PRIMARY KEY , "
                + "FOREIGN KEY (id) REFERENCES Member(id) , "
                + "level INT DEFAULT 0, "
                + "xp BIGINT DEFAULT 0 "
                + ")";
    }

    public String rPGTable() {
        return "CREATE TABLE IF NOT EXISTS RPG ("
                + "id BIGINT PRIMARY KEY , "
                + "FOREIGN KEY (id) REFERENCES Member(id) , "
                + "gold BIGINT DEFAULT 0 , "
                + "dailygold BOOL DEFAULT FALSE , "
                + "effect VARCHAR(255) DEFAULT 'none' "
                + ")";
    }
    public String locationTable() {
        return "CREATE TABLE IF NOT EXISTS location ("
                + "id BIGINT PRIMARY KEY , "
                + "FOREIGN KEY (id) REFERENCES Member(id) , "
                + "location VARCHAR(255) DEFAULT 'Taverne' "
                + ")";
    }
    public String characterTable() {
        return "CREATE TABLE IF NOT EXISTS character ("
                + "id BIGINT PRIMARY KEY , "
                + "FOREIGN KEY (id) REFERENCES Member(id) , "
                + "name VARCHAR(255) DEFAULT '' , "
                + "age INT DEFAULT 0 , "
                + "race VARCHAR(255) DEFAULT '' , "
                + "class VARCHAR(255) DEFAULT '' "
                + ")";
    }
}
