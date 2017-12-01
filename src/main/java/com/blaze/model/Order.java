package com.blaze.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

	private String orderId;
	private List<Product> productList;
	private Double total;
	
}
