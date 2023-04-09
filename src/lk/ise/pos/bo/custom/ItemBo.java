package lk.ise.pos.bo.custom;

import lk.ise.pos.bo.SuperBo;
import lk.ise.pos.dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemBo extends SuperBo {
    public boolean saveItem(ItemDto dto) throws Exception;
    public boolean updateItem(ItemDto dto) throws Exception;
    public ItemDto findItem(String code) throws Exception;
    public boolean deleteItem(String code) throws Exception;
    public List<ItemDto> findAllItems() throws Exception;

    public List<String> loadItemCodes() throws SQLException, ClassNotFoundException;

    public  boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException;
}
