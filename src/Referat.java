import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Referat {

    // JDBC-URL, Benutzername und Passwort Attribute
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/classicmodels";
    private static final String BENUTZER = "root";
    private static final String PASSWORT = "lZ9_S8=>z3;i";

    public static void main(String[] args) {
        Connection verbindung = null; // Connection Klasse initialisiert

        try {
            // Verbindung zur Datenbank herstellen
            verbindung = DriverManager.getConnection(JDBC_URL, BENUTZER, PASSWORT);
            if (verbindung != null) {
                System.out.println("Verbindung erfolgreich hergestellt!");
            } else {
                System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank.");
                return;
            }

            // Neuen Kunden hinzufügen
            neuerKundeHinzufügen(verbindung, 9998, "Neuer Kunde", "Mustermann", "Max", "123-456-7890", "Musterstraße 1", "", "Musterstadt", "Musterstaat", "12345", "Deutschland", null, 10000.00);

            // Alle Kunden auflisten
            alleKundenAuflisten(verbindung);

        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank.");
            e.printStackTrace();
        } finally {
            try {
                if (verbindung != null && !verbindung.isClosed()) {
                    verbindung.close();
                    System.out.println("Verbindung erfolgreich geschlossen!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void neuerKundeHinzufügen(Connection verbindung, int kundenNummer, String kundenName, String kontaktNachname, String kontaktVorname, String telefon, String adresse1, String adresse2, String stadt, String bundesland, String plz, String land, Integer verkaufsMitarbeiterNummer, Double kreditLimit) {
        String sql = "INSERT INTO customers (customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = verbindung.prepareStatement(sql)) {
            statement.setInt(1, kundenNummer);
            statement.setString(2, kundenName);
            statement.setString(3, kontaktNachname);
            statement.setString(4, kontaktVorname);
            statement.setString(5, telefon);
            statement.setString(6, adresse1);
            statement.setString(7, adresse2);
            statement.setString(8, stadt);
            statement.setString(9, bundesland);
            statement.setString(10, plz);
            statement.setString(11, land);
            if (verkaufsMitarbeiterNummer != null) {
                statement.setInt(12, verkaufsMitarbeiterNummer);
            } else {
                statement.setNull(12, java.sql.Types.INTEGER);
            }
            statement.setDouble(13, kreditLimit);
            statement.executeUpdate();
            System.out.println("Neuer Kunde hinzugefügt: " + kundenName);
        } catch (SQLException e) {
            System.out.println("Fehler beim Hinzufügen des neuen Kunden.");
            e.printStackTrace();
        }
    }

    private static void alleKundenAuflisten(Connection verbindung) {
        String sql = "SELECT customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit FROM customers";

        try (Statement statement = verbindung.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int kundenNummer = resultSet.getInt("customerNumber");
                String kundenName = resultSet.getString("customerName");
                String kontaktNachname = resultSet.getString("contactLastName");
                String kontaktVorname = resultSet.getString("contactFirstName");
                String telefon = resultSet.getString("phone");
                String adresse1 = resultSet.getString("addressLine1");
                String adresse2 = resultSet.getString("addressLine2");
                String stadt = resultSet.getString("city");
                String bundesland = resultSet.getString("state");
                String plz = resultSet.getString("postalCode");
                String land = resultSet.getString("country");
                int verkaufsMitarbeiterNummer = resultSet.getInt("salesRepEmployeeNumber");
                double kreditLimit = resultSet.getDouble("creditLimit");

                System.out.println("Kundennummer: " + kundenNummer);
                System.out.println("Kundenname: " + kundenName);
                System.out.println("Kontaktname: " + kontaktVorname + " " + kontaktNachname);
                System.out.println("Telefon: " + telefon);
                System.out.println("Adresse 1: " + adresse1);
                System.out.println("Adresse 2: " + adresse2);
                System.out.println("Stadt: " + stadt);
                System.out.println("Bundesland: " + bundesland);
                System.out.println("PLZ: " + plz);
                System.out.println("Land: " + land);
                System.out.println("Verkaufsmitarbeiternummer: " + verkaufsMitarbeiterNummer);
                System.out.println("Kreditlimit: " + kreditLimit);
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Kundendaten.");
            e.printStackTrace();
        }
    }
}