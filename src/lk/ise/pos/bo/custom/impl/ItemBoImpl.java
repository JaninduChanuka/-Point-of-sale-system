package lk.ise.pos.bo.custom.impl;

import lk.ise.pos.bo.custom.ItemBo;
import lk.ise.pos.dao.custom.ItemDao;
import lk.ise.pos.dao.custom.impl.ItemDaoImpl;
import lk.ise.pos.dto.ItemDto;
import lk.ise.pos.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    private ItemDao itemDao = new ItemDaoImpl();

    @Override
    public boolean saveItem(ItemDto dto) throws Exception {
        return itemDao.save(new Item(dto.getCode(), dto.getDescription(), dto.getQtyOnHand(), dto.getUnitPrice()));
    }

    @Override
    public boolean updateItem(ItemDto dto) throws Exception {
        return itemDao.update(new Item(dto.getCode(), dto.getDescription(), dto.getQtyOnHand(), dto.getUnitPrice()));
    }

    @Override
    public ItemDto findItem(String code) throws Exception {
        Item item = itemDao.find(code);
        if (item != null) {
            return new ItemDto(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice());
        }
        return null;
    }

    @Override
    public boolean deleteItem(String code) throws Exception {
        return itemDao.delete(code);
    }

    @Override
    public List<ItemDto> findAllItems() throws Exception {
        List<Item> all = itemDao.findAll();
        List<ItemDto> dtos = new ArrayList<>();
        for (Item item : all) {
            dtos.add(new ItemDto(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice())
            );
        }
        return dtos;
    }

    @Override
    public List<String> loadItemCodes() throws SQLException, ClassNotFoundException {
        return itemDao.loadItemCodes();
    }

    @Override
    public boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException {
        return itemDao.updateQty(code, qty);
    }
}
