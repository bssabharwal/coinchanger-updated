package com.adp.codechallenge.coinchanger.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.service.CoinName;

@Repository
public class CoinStore {

	private static Map<CoinName, Integer> coinVault = new ConcurrentHashMap<>();
	public static CoinName[] lessCoinDetails =  CoinName.values() ;
	public static CoinName[] moreCoinDetails =  CoinName.values() ;
	
	static {
		coinVault.put(CoinName.HalfDollar, new Integer(100));
		coinVault.put(CoinName.Quarter, new Integer(100));
		coinVault.put(CoinName.Dime, new Integer(100));
		coinVault.put(CoinName.Nickel, new Integer(100));
		coinVault.put(CoinName.Penny, new Integer(100));
		Arrays.sort(lessCoinDetails, Comparator.comparing(CoinName::getValue));
		Arrays.sort(moreCoinDetails, Comparator.comparing(CoinName::getValue));
		Arrays.sort(moreCoinDetails,Collections.reverseOrder(Comparator.comparing(CoinName::getValue)));
	}

	public void initialize(List<CoinDTO> coinDto) {
		coinDto.forEach(dto -> {
			coinVault.put(dto.getCoinName(), new Integer(dto.getQuantity()));
		});

	}

	public static int getInventory(CoinName coinName) {
		return coinVault.get(coinName);
	}

	public List<CoinDTO> getCoinInventory() {
		List<CoinDTO> coinDtos = new ArrayList<>();
		for (Map.Entry<CoinName, Integer> entry : coinVault.entrySet()) {
			CoinDTO coinDto = new CoinDTO();
			coinDto.setCoinName(entry.getKey());
			coinDto.setQuantity(entry.getValue());
			coinDtos.add(coinDto);

		}
		return coinDtos;

	}

	public double getTotalInventoryValue() {
		double totalValue = 0.00;
		for (Map.Entry<CoinName, Integer> entry : coinVault.entrySet()) {
			CoinName coinName = entry.getKey();
			totalValue += coinName.getValue() * entry.getValue();

		}
		return totalValue;
	}

	public void updateCoinInventory(List<CoinDTO> coinDtos) {

		coinDtos.forEach(dto -> {
			if (dto != null) {
				int updatedValue = coinVault.get(dto.getCoinName()) - dto.getQuantity();
				coinVault.put(dto.getCoinName(), new Integer(updatedValue));
			}
		});

	}

	public void addCoinInventory(List<CoinDTO> coinDtos) {

		coinDtos.forEach(dto -> {
			int updatedValue = coinVault.get(dto.getCoinName()) + dto.getQuantity();
			coinVault.put(dto.getCoinName(), new Integer(updatedValue));
		});

	}
}
