package lk.ise.pos.dao.custom.impl;

import lk.ise.pos.dao.CrudUtil;
import lk.ise.pos.dao.custom.CustomerDao;
import lk.ise.pos.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer c) throws Exception {
        return CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?)", c.getId(), c.getName(), c.getAddress(), c.getSalary());
    }

    @Override
    public boolean update(Customer c) throws Exception {
        return CrudUtil.execute("UPDATE customer SET name=?, address=?, salary=? WHERE id=?", c.getName(), c.getAddress(), c.getSalary(), c.getId());
    }

    @Override
    public Customer find(String id) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE id=?", id);
        if (resultSet.next()) {
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.execute("DELETE FROM customer WHERE id=?", id);
    }

    @Override
    public List<Customer> findAll() throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer");
        List<Customer> customerList = new ArrayList<>();
        while (resultSet.next()) {
            customerList.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return customerList;
    }

    @Override
    public List<String> loadCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT id FROM customer");
        List<String> list = new ArrayList<>();
        while (set.next()){
            list.add(set.getString(1));
        }
        return list;
    }
}
