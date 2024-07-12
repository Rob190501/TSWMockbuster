package model;

public class PurchasedMovie extends Movie {

	private Order order;
	private Float price;
	
	public PurchasedMovie(Integer id, String title, String plot, Integer duration, Integer year,
			Integer availableLicenses, Float dailyRentalPrice, Float purchasePrice, Boolean isVisible,
			String posterPath, Order order, Float price) {
		super(id, title, plot, duration, year, availableLicenses, dailyRentalPrice, purchasePrice, isVisible, posterPath);
		this.order = order;
		this.price = price;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
