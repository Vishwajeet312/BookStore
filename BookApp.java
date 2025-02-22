import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class BookApp {
    
    private String name, author;
    private int quantity;
    private double price;
    
    private Scanner sc = new Scanner(System.in);

    private static final String DB_URL = "jdbc:mysql://localhost/book";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "MySQL";
    
    public void enterBook() {
        System.out.print("Enter Book Name : ");
        name = sc.nextLine();
        System.out.print("Enter Book Author : ");
        author = sc.nextLine();
        System.out.print("Enter Book Quantity : ");
        quantity = sc.nextInt();
        System.out.print("Enter Book Price : ");
        price = sc.nextDouble();
        sc.nextLine(); 

        String insertQuery = "INSERT INTO books (name, author, quantity, price) VALUES (?, ?, ?, ?)";
        
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(insertQuery)) {
             
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            pst.setString(1, name);
            pst.setString(2, author);
            pst.setInt(3, quantity);
            pst.setDouble(4, price);
            pst.executeUpdate();
            
            System.out.println("Book added successfully!");

        } catch (Exception err) {
            System.out.println("ERROR: " + err);
        }
    }

    public void checkBook() {
        System.out.print("Enter the Book Name to be Searched : ");
        String bookSearch = sc.nextLine();

        String searchQuery = "SELECT * FROM books WHERE name = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(searchQuery)) {
             
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            pst.setString(1, bookSearch);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Book Name : " + rs.getString("name"));
                    System.out.println("Author Name : " + rs.getString("author"));
                    System.out.println("Quantity : " + rs.getInt("quantity"));
                    System.out.println("Price : " + rs.getDouble("price"));
                } else {
                    System.out.println("Book not found.");
                }
            }

        } catch (Exception err) {
            System.out.println("ERROR: " + err);
        }
    }

    public static void main(String[] args) {
        Scanner sc1 = new Scanner(System.in);
        System.out.print("Enter number of books to be entered: ");
        int no = sc1.nextInt();
        sc1.nextLine();

        BookApp b = new BookApp();

        for (int i = 0; i < no; i++) {
            b.enterBook();
        }
        b.checkBook();
        sc1.close();
    }
}