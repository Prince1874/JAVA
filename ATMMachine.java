import java.sql.*;  
import java.util.Scanner;

class ATMMachine{  

    private static final String url = "jdbc:mysql://localhost:3306/ATM";
    private static final String user = "prince";
    private static final String password = "Prince@123";

  public static float CheckBlance(int acc){
           Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            float balance=0;

             try {
            
            conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT balance FROM user WHERE account_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, acc);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                balance = rs.getFloat("balance");
                return balance;
            } 
        }catch (SQLException e) {
            System.out.println(e);
        }
        return balance;

  }

  public static void Deposit(int acc){

         Connection conn = null; 

         Scanner sc = new Scanner(System.in);

            System.out.println("\nEnter amount: ");
            float amount = sc.nextFloat();

             try {
            conn = DriverManager.getConnection(url, user, password);

            String sql = "UPDATE user set balance= balance + ?  WHERE account_no = ?";
                       
           PreparedStatement debitStatement = conn.prepareStatement(sql);
           debitStatement.setFloat(1,amount);
           debitStatement.setInt(2,acc);
           debitStatement.executeUpdate();
           System.out.println("Deposit successfull...!");
            System.out.println("Current Balance: " + CheckBlance(acc));

        }catch (SQLException e) {
            System.out.println(e);
        }
        sc.close();

  }

  public static void Withraw(int acc){

         Connection conn = null; 
         Scanner sc = new Scanner(System.in);

            System.out.println("\nEnter amount: ");
            float amount = sc.nextFloat();

            float test = CheckBlance(acc);
            if(amount>test){
                System.out.println("Insufficient balance...!");
                System.exit(0);
            }

             try {
            conn = DriverManager.getConnection(url, user, password);

            String sql = "UPDATE user set balance= balance - ?  WHERE account_no = ?";
                       
           PreparedStatement creditStatement = conn.prepareStatement(sql);
           creditStatement.setFloat(1,amount);
           creditStatement.setInt(2,acc);
           creditStatement.executeUpdate();
           System.out.println("Deposit successfull...!");
            System.out.println("Current Balance: " + CheckBlance(acc));

        }catch (SQLException e) {
            System.out.println(e);
        }
        sc.close();

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
                System.out.println("\n\n1.Check Balance");
                System.out.println("2.Deposit");
                System.out.println("3.Withraw");

                System.out.print("\nEnter your choice: ");
                int choice = sc.nextInt();

                switch(choice){
                    case 1:
                        float amount = CheckBlance(acc);
                        System.out.println("Current balance: " + amount);
                        break;
                    case 2:
                        Deposit(acc);
                        break;
                    case 3:
                        Withraw(acc);
                        break;
                    default:
                        System.out.println("Invalid Option try again......???");
                        System.exit(0);
                        break;    
                     }
        
                }else{
                     System.out.println("Wrong Pin..?");
                }

            } 
        }catch (SQLException e) {
            System.out.println(e);
        }
        sc.close();

}  
}