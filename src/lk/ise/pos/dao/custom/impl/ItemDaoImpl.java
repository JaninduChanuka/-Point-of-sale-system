package lk.ise.pos.dao.custom.impl;

import lk.ise.pos.dao.CrudUtil;
import lk.ise.pos.dao.custom.ItemDao;
import lk.ise.pos.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    @Override
    public boolean save(Item i) throws Exception {
        return CrudUtil.execute("INSERT INTO item VALUES(?,?,?,?)", i.getCode(), i.getDescription(), i.getQtyOnHand(), i.getUnitPrice());
    }

    @Override
    public boolean update(Item i) throws Exception {
        return CrudUtil.execute("UPDATE item SET description=?, qty_on_hand=?, unit_price=? WHERE code=?", i.getDescription(), i.getQtyOnHand(), i.getUnitPrice(), i.getCode());
    }

    @Override
    public Item find(String code) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM item WHERE code=?", code);
        if (resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

    @Override
    public boolean delete(String code) throws Exception {
        return CrudUtil.execute("DELETE FROM item WHERE code=?", code);
    }

    @Override
    public List<Item> findAll() throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM item");
        List<Item> itemList = new ArrayList<>();
        while (resultSet.next()) {
            itemList.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            ));
        }
        return itemList;
    }

    @Override
    public List<String> loadItemCodes() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT code FROM item");
        List<String> list = new ArrayList<>();
        while (set.next()){
            list.add(set.getString(1));
        }
        return list;
    }

    @Override
    public boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE item set qty_on_hand=(qty_on_hand-?) WHERE code=?",qty,code);
    }
}
