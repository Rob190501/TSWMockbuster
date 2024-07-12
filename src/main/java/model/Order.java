package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;


public class Order {
	
	private Integer id;
	private LocalDate date;
	private Float amount;
	private Collection<RentedMovie> rentedMovies;
	private Collection<PurchasedMovie> purchasedMovies;
	
	public Order(Integer id, LocalDate date, Float amount) {
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.rentedMovies = new ArrayList<RentedMovie>();
		this.purchasedMovies = new ArrayList<PurchasedMovie>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Collection<RentedMovie> getRentedMovies() {
		return rentedMovies;
	}
	
	public void addRentedMovie(RentedMovie movie) {
		this.rentedMovies.add(movie);
	}
	
	public Collection<PurchasedMovie> getPurchasedMovies() {
		return purchasedMovies;
	}
	
	public void addPurchasedMovie(PurchasedMovie movie) {
		this.purchasedMovies.add(movie);
	}
}
