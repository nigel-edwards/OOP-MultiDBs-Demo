package w08;

import java.sql.*;

public class BDExample {

    private Connection conn;
    private boolean connected = false;

    public void connect(String url, String username, String password) throws SQLException {
        if (!connected) {
            conn = DriverManager.getConnection(url,  username, password);
            System.out.println("Connection to database has been established.");
            connected = true;
        }
    }

    public void disconnect() throws SQLException {
        if (connected) {
            conn.close();
            connected = false;
            conn = null;
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }


    public static void main(String[] args) {

        BDExample db = new BDExample();

        String[] urls = {"jdbc:sqlite:Chinook_Sqlite.sqlite", "jdbc:mysql://localhost:3306/Chinook", "jdbc:postgresql://localhost/chinook" };
        String[] usernames = { null, "root", "postgres"};
        String[] passwords = { null, "rootpassword", "admin"};

        for (int i = 0; i < urls.length; i++) {
            System.out.println("\n######################################################################################################");
            System.out.println("Conecting to :" + urls[i]);
            System.out.println("######################################################################################################");
            try {
                db.connect(urls[i], usernames[i], passwords[i]);

                ResultSet rs = db.executeQuery("SELECT * FROM Album;");
                while (rs.next()) {
                    // Note there are some differences in the column names so used column idexes here
                    int id = rs.getInt(1);
                    String title = rs.getString(2);
                    int artistId = rs.getInt(3);
                    System.out.println(id + ", " + title + ", " + artistId);
                }

                db.disconnect();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
