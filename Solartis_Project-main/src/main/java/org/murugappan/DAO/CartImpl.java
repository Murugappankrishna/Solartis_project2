package org.murugappan.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.murugappan.repo.JDBC;

public class CartImpl implements CartDAO {
	  JDBC jdbc=new JDBC();
	  Connection con= jdbc.establishConnection();
	  PreparedStatement preparedStatement;
	  PreparedStatement deleteStatement;
	@Override
	public void addToCart(Integer userid, Integer productid , Integer quantity ) {
		
		 try {
			 
	        	
	            preparedStatement = con.prepareStatement("INSERT INTO cart (user_id, product_id, quantity, total_amount) VALUES (?, ?, ?, (SELECT selling_price * ? FROM product_details WHERE product_id = ?))");
	            preparedStatement.setInt(1,userid);
	            preparedStatement.setInt(2,productid);
	            preparedStatement.setInt(3,quantity);
	            preparedStatement.setInt(4,quantity);
	            preparedStatement.setInt(5,productid);
	            int rowsInserted = preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	           e.printStackTrace();
	        }
		
	}

public void showCart(Integer userid)  {
	
	try {
		 preparedStatement = con.prepareStatement("Select u.username, pd.product_name, c.quantity,c.total_amount FROM cart c JOIN users u ON c.user_id = u.user_id JOIN product_details pd ON c.product_id = pd.product_id WHERE u.user_id = ?");
		 preparedStatement.setInt(1,userid);
		 ResultSet rs = preparedStatement.executeQuery();
		 while (rs.next()) {
		        String username = rs.getString("username");
		        String productName = rs.getString("product_name");
		        int quantity = rs.getInt("quantity");
		        int totalAmount = rs.getInt("total_amount");
		        System.out.printf("%-15s%-15s%-10d%-15d\n", username, productName, quantity, totalAmount);
		    }
		 
		 
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
public void deleteCart(int userId) {
	try {
	 preparedStatement = con.prepareStatement("SELECT cart_id FROM cart WHERE user_id = ?");
     deleteStatement = con.prepareStatement("DELETE FROM cart WHERE cart_id = ?");
	  preparedStatement.setInt(1,userId);
	 ResultSet rs = preparedStatement.executeQuery();
	 while (rs.next()) {
		 int cartId = rs.getInt("cart_id");
		 deleteStatement.setInt(1, cartId);
		 int rowsAffected = deleteStatement.executeUpdate();
		 
	 }
	}
	catch(SQLException e){
		
	}
}

}
