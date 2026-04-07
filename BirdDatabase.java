import java.sql.*;

public class BirdDatabase {
    public static void main(String[] args) {
        try {
            //connect to SQLite database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:bird.db");
            Statement stmt = conn.createStatement();

            System.out.println("Connected to database.\n");

            //create tables (schema)

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Family (" +
                    "family_id INTEGER PRIMARY KEY, " +
                    "family_name TEXT)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Bird (" +
                    "bird_id INTEGER PRIMARY KEY, " +
                    "common_name TEXT, " +
                    "scientific_name TEXT, " +
                    "conservation_status TEXT, " +
                    "family_id INTEGER, " +
                    "FOREIGN KEY (family_id) REFERENCES Family(family_id))");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Habitat (" +
                    "habitat_id INTEGER PRIMARY KEY, " +
                    "habitat_name TEXT)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Diet (" +
                    "diet_id INTEGER PRIMARY KEY, " +
                    "diet_type TEXT)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS BirdHabitat (" +
                    "bird_id INTEGER, " +
                    "habitat_id INTEGER, " +
                    "PRIMARY KEY (bird_id, habitat_id))");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS BirdDiet (" +
                    "bird_id INTEGER, " +
                    "diet_id INTEGER, " +
                    "PRIMARY KEY (bird_id, diet_id))");

            System.out.println("Tables created.\n");

            //insert data (database instance)

            //families
            stmt.executeUpdate("INSERT OR IGNORE INTO Family VALUES (1, 'Cardinalidae')");
            stmt.executeUpdate("INSERT OR IGNORE INTO Family VALUES (2, 'Bombycillidae')");
            stmt.executeUpdate("INSERT OR IGNORE INTO Family VALUES (3, 'Ardeidae')");
            stmt.executeUpdate("INSERT OR IGNORE INTO Family VALUES (4, 'Corvidae')");
            stmt.executeUpdate("INSERT OR IGNORE INTO Family VALUES (5, 'Fringillidae')");
            stmt.executeUpdate("INSERT OR IGNORE INTO Family VALUES (6, 'Anatidae')");

            //birds
            stmt.executeUpdate("INSERT OR IGNORE INTO Bird VALUES (1, 'Northern Cardinal', 'Cardinalis cardinalis', 'Least Concern', 1)");
            stmt.executeUpdate("INSERT OR IGNORE INTO Bird VALUES (2, 'Bohemian Waxwing', 'Bombycilla garrulus', 'Least Concern', 2)");
            stmt.executeUpdate("INSERT OR IGNORE INTO Bird VALUES (3, 'Great Blue Heron', 'Ardea herodias', 'Least Concern', 3)");
            stmt.executeUpdate("INSERT OR IGNORE INTO Bird VALUES (4, 'Blue Jay', 'Cyanocitta cristata', 'Least Concern', 4)");
            stmt.executeUpdate("INSERT OR IGNORE INTO Bird VALUES (5, 'Common Raven', 'Corvus corax', 'Least Concern', 4)");
            stmt.executeUpdate("INSERT OR IGNORE INTO Bird VALUES (6, 'American Goldfinch', 'Spinus tristis', 'Least Concern', 5)");
            stmt.executeUpdate("INSERT OR IGNORE INTO Bird VALUES (7, 'Canada Goose', 'Branta canadensis', 'Least Concern', 6)");

            System.out.println("Sample data inserted.\n");

            //SELECT
  
            System.out.println("All Birds:");
            ResultSet rs = stmt.executeQuery("SELECT * FROM Bird");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("bird_id") + " | " +
                        rs.getString("common_name") + " | " +
                        rs.getString("scientific_name")
                );
            }

            //INSERT (new bird)
      
            stmt.executeUpdate(
                "INSERT INTO Bird VALUES (8, 'Snowy Owl', 'Bubo scandiacus', 'Least Concern', 3)"
            );
            System.out.println("\nInserted Snowy Owl.");

            //UPDATE

            stmt.executeUpdate(
                "UPDATE Bird SET conservation_status = 'Endangered' WHERE bird_id = 1"
            );
            System.out.println("Updated Northern Cardinal status.");

            //DELETE

            stmt.executeUpdate(
                "DELETE FROM Bird WHERE bird_id = 8"
            );
            System.out.println("Deleted Snowy Owl.");

            //FINAL SELECT

            System.out.println("\nFinal Bird List:");
            rs = stmt.executeQuery("SELECT * FROM Bird");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("bird_id") + " | " +
                        rs.getString("common_name") + " | " +
                        rs.getString("conservation_status")
                );
            }

            conn.close();
            System.out.println("\nDone.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}