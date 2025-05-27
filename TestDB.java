import java.sql.*;
import java.util.Scanner;

public class TestDB {

    private static final String url = "jdbc:mysql://localhost:3306/ATM";
    private static final String user = "prince";
    private static final String password = "Prince@123";

    public static void createTable() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter table name: ");
        String tableName = sc.nextLine();
        try {

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE `" + tableName + "` (id INT primary key, name VARCHAR(30))";
            stmt.execute(sql);
            System.out.println("Table created...!");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void deleteTable() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter table name: ");
        String tableName = sc.nextLine();

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("drop table " + tableName);
            System.out.println("Table deleted...!");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void insertData(int id, String name) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "insert into friend values(?,?)";
            PreparedStatement insertStmt = conn.prepareStatement(sql);
            insertStmt.setInt(1, id);
            insertStmt.setString(2, name);
            insertStmt.execute();
            System.out.println("Student" + name + "inserted sucessfully..!");
        } catch (SQLException e) {

            System.out.println(e);
        }
    }

    public static void deleteRow(int id) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String sql = "delete from friend where id = " + id;
            stmt.execute(sql);
            System.out.println("Data deleted...!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        deleteRow(1);

    }
}
