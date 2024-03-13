package org.murugappan.DAO;

public interface CartDAO {
 void addToCart(Integer userid, Integer productid, Integer quantity);

void showCart(Integer userid);
void deleteCart(int userid);
}
