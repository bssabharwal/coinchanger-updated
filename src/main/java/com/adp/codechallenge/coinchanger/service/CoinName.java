package com.adp.codechallenge.coinchanger.service;

public enum CoinName {
	
	HalfDollar(0.50), Quarter(0.25), Dime(0.10), Nickel(0.05), Penny(0.01);
	private double value = 0.0;
	private CoinName(double v) {
		this.value = v;
	}
	public double getValue() {
		return value;
	}
	
	
	
}
