package net.snacj.db;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import net.snacj.util.LogConstants;

import java.sql.*;

public class PostgreSQLConnection extends ListenerAdapter {
    // get database credentials from .env file
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
        createTableIfNotExists(MemberTable());

        event.getJDA().getGuilds().forEach(guild -> guild.loadMembers().onSuccess(members -> {
            String insertMemberSQL = "INSERT INTO Member (id, username, readname) VALUES (?, ?, ?) " +
                    "ON CONFLICT (id) DO NOTHING";

            try (PreparedStatement MemberStatement = connection.prepareStatement(insertMemberSQL);) {

                for (var member : members) {
                    if (!member.getUser().isBot()) {
                        long memberId =  member.getIdLong();
                        String Membername = member.getUser().getName();
                        String readname = member.getEffectiveName();

                        MemberStatement.setLong(1, memberId);
                        MemberStatement.setString(2, Membername);
                        MemberStatement.setString(3, readname);
                        MemberStatement.addBatch();
                    }
                }

                MemberStatement.executeBatch();

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

    /**
     * SQL queries
     */
    public String MemberTable() {
        return "CREATE TABLE IF NOT EXISTS Member ("
                + "id BIGINT PRIMARY KEY, "
                + "username VARCHAR(255), "
                + "readname VARCHAR(255), "
                + "age INT DEFAULT 0, "
                + "species VARCHAR(255) DEFAULT '', "
                + "profession VARCHAR(255) DEFAULT 'Citizen', "
                + "level INT DEFAULT 0, "
                + "xp BIGINT DEFAULT 0, "
                + "location VARCHAR(255) DEFAULT 'Orion Space Station', "
                + "shipassignment VARCHAR(255) DEFAULT 'None', "
                + "status VARCHAR(255) DEFAULT 'None', "
                + "credits BIGINT DEFAULT 0, "
                + "dailycredits BOOL DEFAULT FALSE"
                + ")";
    }
}
