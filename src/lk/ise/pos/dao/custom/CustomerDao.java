package lk.ise.pos.dao.custom;

import lk.ise.pos.dao.CrudDao;
import lk.ise.pos.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao extends CrudDao<Customer, String> {
    public List<String> loadCustomerIds() throws SQLException, ClassNotFoundException;
}
