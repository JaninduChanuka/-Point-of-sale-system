package lk.ise.pos.dao;

import java.util.List;

public interface CrudDao<T, ID> extends SuperDao {
    public boolean save(T t) throws Exception;

    public boolean update(T t) throws Exception;

    public T find(ID id) throws Exception;

    public boolean delete(ID id) throws Exception;

    public List<T> findAll() throws Exception;

}
