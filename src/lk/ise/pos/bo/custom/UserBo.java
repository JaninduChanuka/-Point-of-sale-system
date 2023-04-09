package lk.ise.pos.bo.custom;

import lk.ise.pos.bo.SuperBo;
import lk.ise.pos.dto.UserDto;

import java.sql.SQLException;

public interface UserBo extends SuperBo {
    public void initializeUsers() throws SQLException, ClassNotFoundException;
    public UserDto findUser(String username) throws Exception;
}
