package com.adp.codechallenge.coinchanger.machine;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.exception.NotEnoughCoinsException;
import com.adp.codechallenge.coinchanger.service.CoinName;
import com.adp.codechallenge.coinchanger.store.CoinStore;

@Service
public class CoinChangerMachine {

	@Autowired
	private CoinStore coinStore;

	public List<CoinDTO> calculateNumberOfCoins(int billAmount, String algo) throws Exception {

		double totalInventoryValue = coinStore.getTotalInventoryValue();
		if (billAmount > totalInventoryValue) {
			throw new NotEnoughCoinsException("Not enough coins are available to exchange for bill");
		}
		CoinCalculator coinCalculator = new CoinCalculatorImpl();
		List<CoinDTO> result = null;
		synchronized (this) {

			result = coinCalculator.getNoOfCoins(billAmount, algo);
			coinStore.updateCoinInventory(result);

		}
		return result;

	}

}
