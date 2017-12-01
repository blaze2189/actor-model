package com.blaze.model;

import com.blaze.util.Food;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	private Food food;
	private Integer productId;
	private String productName;
	private Double productPrice;
	
}
