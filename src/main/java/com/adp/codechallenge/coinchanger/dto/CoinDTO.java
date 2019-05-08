package com.adp.codechallenge.coinchanger.dto;


import com.adp.codechallenge.coinchanger.service.CoinName;

public class CoinDTO {
	
	private CoinName coinName;
	private int quantity;

	
	public CoinName getCoinName() {
		return coinName;
	}
	public void setCoinName(CoinName coinName) {
		this.coinName = coinName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	
//	private int halfDollar;
//	private int quarter;
//	private int dime;
//	private int nickel;
//	private int penny;
//	public int getQuarter() {
//		return quarter;
//	}
//	
//	
//	public int getHalfDollar() {
//		return halfDollar;
//	}
//
//
//	public void setHalfDollar(int halfDollar) {
//		this.halfDollar = halfDollar;
//	}
//
//
//	public void setQuarter(int quarter) {
//		this.quarter = quarter;
//	}
//	public int getDime() {
//		return dime;
//	}
//	public void setDime(int dime) {
//		this.dime = dime;
//	}
//	public int getNickel() {
//		return nickel;
//	}
//	public void setNickel(int nickel) {
//		this.nickel = nickel;
//	}
//	public int getPenny() {
//		return penny;
//	}
//	public void setPenny(int penny) {
//		this.penny = penny;
//	}
	
	

}
