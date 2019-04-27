package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	public static boolean register(User u)
	{
		try(Connection con=DataBase.getConnection())
		{
			PreparedStatement ps=con.prepareStatement("insert into users values(?,?,?,?,?,sysdate)");
			ps.setString(1, u.getUnamr());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFullname());
			ps.setString(4, u.getEmail());
			ps.setString(5, u.getMobile());
			int count=ps.executeUpdate();
			return count==1;
		}
		catch(Exception e)
		{
			System.out.println("UserDao :"+e.getMessage());
			return false;
		}
	}
	 public static User login(String username, String password) {

	        try (Connection con = DataBase.getConnection()) {
	            PreparedStatement ps = con.prepareStatement
	                ("select * from users where unamr = ? and password = ?");
	            ps.setString(1, username);
	            ps.setString(2, password);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                User u = new User();
	                u.setUnamr(username);
	                u.setPassword(password);
	                u.setFullname(rs.getString("fullname"));
	                u.setEmail(rs.getString("email"));
	                u.setMobile(rs.getString("mobile"));
	                return u;
	            } else {
	                return null;
	            }
	        } catch (Exception ex) {
	            System.out.println("UserDAO-> login() : " + ex.getMessage());
	            return null;
	        }
	    }
	 public static boolean changePassword(String uname,String password,String newpassword)
	 {
		 try(Connection con = DataBase.getConnection())
		 {
			 PreparedStatement ps=con.prepareStatement("update users set password = ? where unamr = ? and password = ?");
			 ps.setString(1, newpassword);
			 ps.setString(2, uname);
			 ps.setString(3, password);
			 int count=ps.executeUpdate();
			 return count==1;
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 return false;
		 }
	 }
	 	
	 public static User getUser(String uname, String email) {

	        try (Connection con = DataBase.getConnection()) {
	            PreparedStatement ps = con.prepareStatement
	               ("select * from users where unamr = ? or email = ?");
	            ps.setString(1, uname);
	            ps.setString(2, email);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                User u = new User();
	                u.setUnamr(uname);
	                u.setPassword(rs.getString("password"));
	                u.setFullname(rs.getString("fullname"));
	                u.setEmail(rs.getString("email"));
	                u.setMobile(rs.getString("mobile"));
	                return u;
	            } else {
	                return null;
	            }
	        } catch (Exception ex) {
	            System.out.println("UserDAO-> getUser() : " + ex.getMessage());
	            return null;
	        }
	    }
}



