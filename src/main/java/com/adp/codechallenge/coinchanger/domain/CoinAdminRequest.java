package com.adp.codechallenge.coinchanger.domain;

import java.util.List;

public class CoinAdminRequest {
	
	List<CoinResponse> coins;

	public List<CoinResponse> getCoins() {
		return coins;
	}

	public void setCoins(List<CoinResponse> coins) {
		this.coins = coins;
	}
	
	
	

}
