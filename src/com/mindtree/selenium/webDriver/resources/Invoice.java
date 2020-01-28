package com.mindtree.selenium.webDriver.resources;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
	private int invoiceNumber;
	private double total;
	protected List<String> name = new ArrayList<String>();
	protected List<Double> price = new ArrayList<Double>();
	protected List<String> description = new ArrayList<String>();
	protected List<String> material = new ArrayList<String>();
	protected List<String> color = new ArrayList<String>();
	protected List<String> warranty = new ArrayList<String>();
	
	public int getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getName(int index) {
		return name.get(index);
	}
	public void setName(String name) {
		this.name.add(name);
	}
	public double getPrice(int index) {
		return price.get(index);
	}
	public void setPrice(double price) {
		this.price.add(price);
	}
	public String getDescription(int index) {
		return description.get(index);
	}
	public void setDescription(String description) {
		this.description.add(description);
	}
	public String getMaterial(int index) {
		return material.get(index);
	}
	public void setMaterial(String material) {
		this.material.add(material);
	}
	public String getColor(int index) {
		return color.get(index);
	}
	public void setColor(String color) {
		this.color.add(color);
	}
	public String getWarranty(int index) {
		return warranty.get(index);
	}
	public void setWarranty(String warranty) {
		this.warranty.add(warranty);
	}

	
	
}
