package lk.ise.pos.dao.custom;

import lk.ise.pos.dao.CrudDao;
import lk.ise.pos.entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao extends CrudDao<Item, String> {
    public List<String> loadItemCodes() throws SQLException, ClassNotFoundException;
    public  boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException;
}
