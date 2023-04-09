package lk.ise.pos.dao.custom.impl;

import lk.ise.pos.dao.CrudUtil;
import lk.ise.pos.dao.custom.OrderDao;
import lk.ise.pos.dao.custom.OrderDetailsDao;
import lk.ise.pos.entity.OrderDetails;

import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {

    @Override
    public boolean save(OrderDetails orderDetails) throws Exception {
        return CrudUtil.execute(
                "INSERT INTO order_detail VALUES(?,?,?,?)",
                orderDetails.getCode()
                ,orderDetails.getOrderId(),
                orderDetails.getQty(),
                orderDetails.getUnitPrice()
        );
    }

    @Override
    public boolean update(OrderDetails orderDetails) throws Exception {
        return false;
    }

    @Override
    public OrderDetails find(String s) throws Exception {
        return null;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public List<OrderDetails> findAll() throws Exception {
        return null;
    }
}
