package com.adp.codechallenge.coinchanger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adp.codechallenge.coinchanger.domain.CoinExchangeRequest;
import com.adp.codechallenge.coinchanger.domain.CoinExchangeResponse;
import com.adp.codechallenge.coinchanger.domain.CoinResponse;
import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.machine.CoinChangerMachine;
import com.adp.codechallenge.coinchanger.util.ValidationUtil;

@Service
public class CoinChangerService {

	@Autowired
	private CoinChangerMachine coinChangerMachine;

	public CoinExchangeResponse changeBillForCoins(CoinExchangeRequest request, String algo) throws Exception {
		
		ValidationUtil.runValidations(request);
		int totalAmount = request.getBills().stream().map(Integer::parseInt).reduce(0,Integer::sum);
		List<CoinDTO> coinDtos = coinChangerMachine.calculateNumberOfCoins(totalAmount, algo);
		List<CoinResponse> coinResponses = new ArrayList<>();
		
		coinDtos.forEach(coinDto ->{
			CoinResponse coinResponse = new CoinResponse();
			coinResponse.setName(coinDto.getCoinName().name());
			coinResponse.setQuantity(coinDto.getQuantity());
			coinResponses.add(coinResponse);
		});
		CoinExchangeResponse response = new CoinExchangeResponse();
		response.setCoins(coinResponses);
		return response;

	}

}
