package ims.database;

import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class UsersDb {
    Connection cn=null;
    Statement st=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    public UsersDb()
    {
	try
	{
            Class.forName("oracle.jdbc.driver.OracleDriver");//REGISTER AND LOAD THE JDBC DRIVER
            this.cn=DriverManager.getConnection("jdbc:oracle:oci:@localhost:1521:xe","b22","b22");//ESTABLISH THE CONNECTION	
	}catch(ClassNotFoundException ce){
            ce.printStackTrace();
	}catch(SQLException se){
            se.printStackTrace();
	}
    }
    
    public boolean checkLogin(String username,String password)
    {
        String sql="select * from users where username='"+username+"' and password='"+password+"'";
        boolean flag=false;
        try{
            st=this.cn.createStatement();//CREATE THE STATEMENT
            rs=st.executeQuery(sql);//EXECUTE THE QUERY AND RETRIEVE DATA INTO RESULTSET
            if(rs.next()){
                flag=true;
            }
        }catch(SQLException se){
            se.printStackTrace();
        }
        return flag;
    }
    
    public void register(String username,String password,String name,String phno,String email){
        String insert_sql="insert into users values(?,?,?,?,?)";
        try{
            
            ps=cn.prepareStatement(insert_sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,name);
            ps.setString(4,phno);
            ps.setString(5,email);
            ps.executeUpdate();
            cn.commit();
            JOptionPane.showMessageDialog(null, "User Registered !!","Success", JOptionPane.WARNING_MESSAGE);
        }catch(SQLException se){
            JOptionPane.showMessageDialog(null, "Cannot Be Added !!","Error", JOptionPane.WARNING_MESSAGE);
            se.printStackTrace();
        }
    }
}