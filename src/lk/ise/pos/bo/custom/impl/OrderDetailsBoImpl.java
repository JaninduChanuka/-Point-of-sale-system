package lk.ise.pos.bo.custom.impl;

import lk.ise.pos.bo.custom.OrderDetailsBo;
import lk.ise.pos.dao.DaoFactory;
import lk.ise.pos.dao.custom.OrderDetailsDao;
import lk.ise.pos.dto.OrderDetailsDto;
import lk.ise.pos.entity.OrderDetails;
import lk.ise.pos.enums.DaoType;

public class OrderDetailsBoImpl implements OrderDetailsBo {
    private OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    @Override
    public boolean saveOrderDetail(OrderDetailsDto dto) throws Exception {
        return orderDetailsDao.save(new OrderDetails(dto.getCode(), dto.getOrderId(), dto.getUnitPrice(), dto.getQty()));
    }
}
