package model.dao;

import java.util.Collection;

import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.RentedMovie;

public class RentedMovieDAO implements DAOInterface<RentedMovie> {

	private DataSource ds = null;
	private static String table = "movie_rental_order";
	
	public RentedMovieDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(RentedMovie bean) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RentedMovie retrieveByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<RentedMovie> retrieveAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
