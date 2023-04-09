package lk.ise.pos.bo.custom;

import lk.ise.pos.bo.SuperBo;
import lk.ise.pos.dto.OrderDetailsDto;
import lk.ise.pos.dto.OrderDto;
import lk.ise.pos.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public interface OrderBo extends SuperBo {
    public boolean saveOrder(OrderDto dto, List<OrderDetailsDto> details) throws Exception;
    public String generateOrderId() throws SQLException, ClassNotFoundException;
}
