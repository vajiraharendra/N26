package com.N26.BestBuy.Products;

public class ProductInputDTO 
{

	protected String name;
	protected String type;
	protected double price;
	protected double shipping;
	protected String upc;
	protected String description;
	protected String manufacturer;
	protected String model;
	protected String url;
	protected String image;
	
	public ProductInputDTO(String name, String type, double price, double shipping, String upc, String description,
			String manufacturer, String model, String url, String image) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.shipping = shipping;
		this.upc = upc;
		this.description = description;
		this.manufacturer = manufacturer;
		this.model = model;
		this.url = url;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
}
