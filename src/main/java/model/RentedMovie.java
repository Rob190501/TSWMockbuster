package model;

public class RentedMovie extends Movie {

	private Order order;
	private Float dailyPrice;
	private Integer days;
	
	public RentedMovie(Integer id, String title, String plot, Integer duration, Integer year, Integer availableLicenses,
			Float dailyRentalPrice, Float purchasePrice, Boolean isVisible, String posterPath, Order order,
			Float dailyPrice, Integer days) {
		super(id, title, plot, duration, year, availableLicenses, dailyRentalPrice, purchasePrice, isVisible,
				posterPath);
		this.order = order;
		this.dailyPrice = dailyPrice;
		this.days = days;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Float getDailyPrice() {
		return dailyPrice;
	}
	
	public void setDailyPrice(Float dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
	
	public Integer getDays() {
		return days;
	}
	
	public void setDays(Integer days) {
		this.days = days;
	}

	
}
