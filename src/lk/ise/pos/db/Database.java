package lk.ise.pos.db;


import lk.ise.pos.entity.Customer;
import lk.ise.pos.entity.Item;
import lk.ise.pos.entity.Order;
import lk.ise.pos.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

public class Database {
    public static ArrayList<User> users = new ArrayList();
    public static ArrayList<Customer> customers = new ArrayList();
    public static ArrayList<Item> items = new ArrayList();
    public static ArrayList<Order> orders = new ArrayList<>();
    private static String encryptPassword(String rowPassword) {
        return BCrypt.hashpw(rowPassword, BCrypt.gensalt());
    }

}
