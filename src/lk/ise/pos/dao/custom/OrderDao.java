package lk.ise.pos.dao.custom;

import lk.ise.pos.dao.CrudDao;
import lk.ise.pos.entity.Order;

import java.sql.SQLException;

public interface OrderDao extends CrudDao<Order,String> {
    public String generateOrderId() throws SQLException, ClassNotFoundException;
}
