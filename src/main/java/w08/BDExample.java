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

        String[] urls = {"jdbc:sqlite:Chinook_Sqlite.sqlite", "jdbc:mysql://localhost:3306/chinook", "jdbc:postgresql://localhost/chinook" };
        String[] usernames = { null, "reader", "postgres"};
        String[] passwords = { null, "mysql", "R0semar1e"};

        for (int i = 0; i < urls.length; i++) {
            System.out.println("\n######################################################################################################");
            System.out.println("Conecting to :" + urls[i]);
            System.out.println("######################################################################################################");
            try {
                db.connect(urls[i], usernames[i], passwords[i]);

                ResultSet rs = db.executeQuery("SELECT * FROM album;");
                while (rs.next()) {
                    int id = rs.getInt("AlbumId");
                    String title = rs.getString("Title");
                    int artistId = rs.getInt("ArtistID");
                    System.out.println(id + ", " + title + ", " + artistId);
                }

                db.disconnect();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
