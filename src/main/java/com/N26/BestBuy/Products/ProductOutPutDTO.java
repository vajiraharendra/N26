package com.N26.BestBuy.Products;

public class ProductOutPutDTO extends ProductInputDTO
{
	private double id;
	private String updatedAt;
	private String createdAt;
	

	public ProductOutPutDTO(String name, String type, double price, double shipping, String upc, String description,
			String manufacturer, String model, String url, String image, double id, String updatedAt,
			String createdAt) {
		super(name, type, price, shipping, upc, description, manufacturer, model, url, image);
		this.id = id;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}
	
	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
}
