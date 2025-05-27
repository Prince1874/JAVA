
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BookStore {
    private static final String url = "jdbc:mysql://localhost:3306/book_store";
    private static final String user = "root";
    private static final String password = "Prince@123";

    public static void addBook() {
        Connection conn = null;
        // PreparedStatement stmt = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Book Id: ");
        String b_id = sc.nextLine();
        System.out.print("Enter Book Name: ");
        String b_name = sc.nextLine();
        System.out.print("Enter Book Author: ");
        String b_author = sc.nextLine();
        System.out.print("Enter Book Price: ");
        Double b_price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Boook Availablity: ");
        String b_avl = sc.nextLine();
        sc.close();

        try {

            conn = DriverManager.getConnection(url, user, password);
            String sql = "insert into book_details values(?,?,?,?,?)";
            PreparedStatement insertStmt = conn.prepareStatement(sql);
            insertStmt.setString(1, b_id);
            insertStmt.setString(2, b_name);
            insertStmt.setString(3, b_author);
            insertStmt.setDouble(4, b_price);
            insertStmt.setString(5, b_avl);
            insertStmt.execute();
            System.out.println("Book Added Successfully...!");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void deleteBook() {
        // Scanner sc = new Scanner(System.in);
        // System.out.print("Enter Book id: ");
        // String id = sc.nextLine();
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String sql = "delete from book_details where book_id = 'B002' ";
            // String sql = "delete from friend where id = " + id;
            stmt.execute(sql);
            System.out.println("Book deleted...!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1.Add Book\t2.Delete Book\tCtrl+c to Exit");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                deleteBook();
                break;
            default:
                System.out.println("Wrong input..!");
                break;
        }
        sc.close();
    }

}
