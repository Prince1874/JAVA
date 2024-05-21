import java.sql.*;  
import java.util.Scanner;

class ATMMachine{  

    private static final String url = "jdbc:mysql://localhost:3306/ATM";
    private static final String user = "prince";
    private static final String password = "Prince@123";

    public static void menu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n1.Check Balance");
        System.out.println("2.Deposit");
        System.out.println("3.Withraw");

        System.out.print("\nEnter your choice: ");
        int choice = sc.nextInt();

        switch(choice){
            case 1:
                CheckBlance();
                break;
            case 2:
                Deposit();
                break;
            case 3:
                Withraw();
            default:
                System.out.println("Invalid Option try again......???");
                System.exit(0);
                break;    
        }
        
    }

  public static void CheckBlance(){
           Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;


             try {
            
            conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT balance FROM user WHERE account_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 10003);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                float balance = rs.getFloat("balance");
                System.out.println("Current Balance: " + balance);
            } 
        }catch (SQLException e) {
            System.out.println(e);
        }

  }

  public static void Deposit(){

  }

  public static void Withraw(){

  }

    public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;


            System.out.print("Enter Your Account Number: ");
            int acc = sc.nextInt();
            System.out.print("Enter Your 4 digit pin: ");
            int atmPin = sc.nextInt();

        try {
            
            conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT pin,name FROM user WHERE account_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, acc);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int pin = rs.getInt("pin");
                String name = rs.getString("name");
                if(pin == atmPin){
                     System.out.println("\nHelo " + name);
                     menu();
                }
                else{
                     System.out.println("Wrong Pin..?");
                }

            } 
        }catch (SQLException e) {
            System.out.println(e);
        }
}  
}