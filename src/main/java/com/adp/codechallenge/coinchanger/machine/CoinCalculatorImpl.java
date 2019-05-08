package com.adp.codechallenge.coinchanger.machine;

import java.util.ArrayList;
import java.util.List;

import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.service.CoinName;
import com.adp.codechallenge.coinchanger.store.CoinStore;

public class CoinCalculatorImpl implements CoinCalculator {

	public List<CoinDTO> getNoOfCoins(int billAmount, String algo) {

		CoinName[] coinDetails = null;

		if ("more".equalsIgnoreCase(algo)) {
			coinDetails = CoinStore.moreCoinDetails;
		} else {
			coinDetails = CoinStore.lessCoinDetails;
		}

		int len = coinDetails.length;

		int[] inventoryArray = new int[len];
		double[] coinDenominations = new double[len];
		for (int i = 0; i < len; i++) {
			coinDenominations[i] = coinDetails[i].getValue();
			inventoryArray[i] = CoinStore.getInventory(coinDetails[i]);
		}

		CoinDTO[] result = new CoinDTO[len];

		double amount = billAmount;
		for (int i = len - 1; i > -1; i--) {

			Double d = amount / coinDenominations[i];
			int no = d.intValue();
			if (no > inventoryArray[i]) {
				CoinDTO coinDto = new CoinDTO();
				coinDto.setCoinName(coinDetails[i]);
				coinDto.setQuantity(inventoryArray[i]);
				result[i] = coinDto;
				amount -= coinDenominations[i] * inventoryArray[i];
			} else {
				CoinDTO coinDto = new CoinDTO();
				coinDto.setCoinName(coinDetails[i]);
				coinDto.setQuantity(no);
				result[i] = coinDto;

				break;
			}
		}
		List<CoinDTO> coinDtos = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			if (result[i] != null) {
				coinDtos.add(result[i]);
			}
		}
		return coinDtos;

	}

}
