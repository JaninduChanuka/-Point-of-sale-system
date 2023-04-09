package lk.ise.pos.dao.custom;

import lk.ise.pos.dao.CrudDao;
import lk.ise.pos.entity.User;

import java.sql.SQLException;

public interface UserDao extends CrudDao<User, String> {
    public void initializeUsers() throws SQLException, ClassNotFoundException;
}
