package lk.ise.pos.bo.custom;

import lk.ise.pos.dto.OrderDetailsDto;

public interface OrderDetailsBo {
    public boolean saveOrderDetail(OrderDetailsDto dto) throws Exception;
}
