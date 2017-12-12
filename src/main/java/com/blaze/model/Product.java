package com.blaze.model;

import com.blaze.util.Food;
import com.blaze.util.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	private Food food;
	private Integer productId;
	private String productName;
	private Double productPrice;
	private Status status=Status.NA;
	
	@Override
	public String toString() {
		
		return "Food: "+food+"\nproduct name: "+productName+"\nstatus: "+status;
	}
	
}
