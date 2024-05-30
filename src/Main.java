import java.sql.*;


public class Main {
    public static void main(String[] args) {
        String str="select id,name from students";
        String url="jdbc:mysql://localhost:3306/student_management";
        String username="root";
        String password="";
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            Statement st=con.createStatement();
            ResultSet r=st.executeQuery(str);
            while (r.next()) {
                String name = r.getString("name");
                String id = r.getString("id");
                System.out.print(id + " ");
                System.out.println(name);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
}