package com.adp.codechallenge.coinchanger.machine;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.service.CoinName;

@Service
public interface CoinCalculator {

	public List<CoinDTO> getNoOfCoins(int billAmount, String algo);
}
