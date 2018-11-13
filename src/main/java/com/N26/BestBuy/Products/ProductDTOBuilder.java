package com.N26.BestBuy.Products;

public class ProductDTOBuilder 
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
	
	
	public ProductDTOBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public ProductDTOBuilder setType(String type) {
		this.type = type;
		return this;
	}
	
	public ProductDTOBuilder setPrice(double price) {
		this.price = price;
		return this;
	}
	
	public ProductDTOBuilder setShipping(double shipping) {
		this.shipping = shipping;
		return this;
	}
	
	public ProductDTOBuilder setUpc(String upc) {
		this.upc = upc;
		return this;
	}
	
	public ProductDTOBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public ProductDTOBuilder setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
		return this;
	}
	public ProductDTOBuilder setModel(String model) {
		this.model = model;
		return this;
	}
	
	public ProductDTOBuilder setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public ProductDTOBuilder setImage(String image) {
		this.image = image;
		return this;
	}
	
	public ProductInputDTO build()
	{
		return new ProductInputDTO(name, type, price, shipping, upc, description, manufacturer, model, url, image);
	}
	
}
