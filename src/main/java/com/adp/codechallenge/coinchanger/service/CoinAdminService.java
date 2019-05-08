package com.adp.codechallenge.coinchanger.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adp.codechallenge.coinchanger.domain.CoinAdminRequest;
import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.store.CoinStore;

@Service
public class CoinAdminService {

	@Autowired
	private CoinStore coinStore;
	
	public void initializeMachine(CoinAdminRequest request) {
		if(request != null) {
			List<CoinDTO> coinDtos = new ArrayList<>();
			request.getCoins().forEach(coin ->{
				CoinDTO coinDto = new CoinDTO();
				coinDto.setCoinName(CoinName.valueOf(coin.getName()));
				coinDto.setQuantity(coin.getQuantity());
				coinDtos.add(coinDto);
			});
			coinStore.initialize(coinDtos);

			
		}
	}
	
	public void addInventory(CoinAdminRequest request) {
		if(request != null) {

			List<CoinDTO> coinDtos = new ArrayList<>();
			request.getCoins().forEach(coin ->{
				CoinDTO coinDto = new CoinDTO();
				coinDto.setCoinName(CoinName.valueOf(coin.getName()));
				coinDto.setQuantity(coin.getQuantity());
				coinDtos.add(coinDto);
			});
			coinStore.addCoinInventory(coinDtos);
			
		}
	}
	
	public List<CoinDTO> getInventory() {
		return coinStore.getCoinInventory();
	}
	
	
}
