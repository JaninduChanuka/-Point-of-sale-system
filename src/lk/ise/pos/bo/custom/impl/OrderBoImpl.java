package lk.ise.pos.bo.custom.impl;

import lk.ise.pos.bo.BoFactory;
import lk.ise.pos.bo.custom.ItemBo;
import lk.ise.pos.bo.custom.OrderBo;
import lk.ise.pos.dao.DaoFactory;
import lk.ise.pos.dao.custom.OrderDao;
import lk.ise.pos.dao.custom.OrderDetailsDao;
import lk.ise.pos.db.DBConnection;
import lk.ise.pos.dto.OrderDetailsDto;
import lk.ise.pos.dto.OrderDto;
import lk.ise.pos.entity.Order;
import lk.ise.pos.entity.OrderDetails;
import lk.ise.pos.enums.BoType;
import lk.ise.pos.enums.DaoType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    private OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);
    private OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    @Override
    public boolean saveOrder(OrderDto dto, List<OrderDetailsDto> details) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isOrderSaved = orderDao.save(new Order(dto.getOrderId(), dto.getCustomer(), dto.getDate(), dto.getTotal()));
            if (isOrderSaved) {
                for (OrderDetailsDto d : details) {
                    boolean isItemSaved = orderDetailsDao.save(new OrderDetails(d.getCode(), d.getOrderId(), d.getUnitPrice(), d.getQty()));
                    if (isItemSaved) {
                        boolean b = itemBo.updateQty(d.getCode(), d.getQty());
                        if (!b) {
                            connection.rollback();
                            return false;
                        }
                    } else {
                        connection.rollback();
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.commit();
            connection.setAutoCommit(true);
        }
        return true;
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        return orderDao.generateOrderId();
    }
}
