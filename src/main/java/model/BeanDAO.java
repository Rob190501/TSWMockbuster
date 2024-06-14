package model;

import java.util.Collection;
import control.exceptions.DAOException;

public interface BeanDAO<T> {
	public void save(T bean) throws DAOException;

	public void delete(int id) throws DAOException;

	public T retrieveByID(int id) throws DAOException;

	public Collection<T> retrieveAll(String order) throws DAOException;
}
