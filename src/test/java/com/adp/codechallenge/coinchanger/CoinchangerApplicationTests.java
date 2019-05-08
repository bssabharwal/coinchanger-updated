package com.adp.codechallenge.coinchanger;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.adp.codechallenge.coinchanger.domain.CoinExchangeRequest;
import com.adp.codechallenge.coinchanger.domain.CoinExchangeResponse;
import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.exception.NotEnoughCoinsException;
import com.adp.codechallenge.coinchanger.exception.ValidationException;
import com.adp.codechallenge.coinchanger.service.CoinChangerService;
import com.adp.codechallenge.coinchanger.service.CoinName;
import com.adp.codechallenge.coinchanger.store.CoinStore;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinchangerApplicationTests {

	@Autowired
	CoinChangerService coinChangerService;

	@Autowired
	CoinStore coinStore;

	@Test
	public void contextLoads() {
	}

	public void initializeCoinStore() {
		List<CoinDTO> coinDtos = new ArrayList<>();
		CoinDTO coinDto1 = new CoinDTO();
		coinDto1.setCoinName(CoinName.HalfDollar);
		coinDto1.setQuantity(10);
		coinDtos.add(coinDto1);
		CoinDTO coinDto2 = new CoinDTO();
		coinDto2.setCoinName(CoinName.Quarter);
		coinDto2.setQuantity(10);
		coinDtos.add(coinDto2);
		coinDtos.add(coinDto1);
		CoinDTO coinDto3 = new CoinDTO();
		coinDto3.setCoinName(CoinName.Dime);
		coinDto3.setQuantity(20);
		coinDtos.add(coinDto3);
		CoinDTO coinDto4 = new CoinDTO();
		coinDto4.setCoinName(CoinName.Nickel);
		coinDto4.setQuantity(50);
		coinDtos.add(coinDto4);
		CoinDTO coinDto5 = new CoinDTO();
		coinDto5.setCoinName(CoinName.Penny);
		coinDto5.setQuantity(100);
		coinDtos.add(coinDto5);
		coinStore.initialize(coinDtos);
	}

	@Test
	public void successSingleRequestWithLessAlgo() throws Exception {

		initializeCoinStore();
		CoinExchangeRequest request = new CoinExchangeRequest();
		List<String> bills = new ArrayList<>();
		bills.add("5");
		bills.add("2");
		request.setBills(bills);

		CoinExchangeResponse response = coinChangerService.changeBillForCoins(request, "less");

		response.getCoins().forEach(coin -> {
			if (coin.getName() == CoinName.HalfDollar.name()) {
				assertEquals(Integer.valueOf(10), coin.getQuantity());
			} else if (coin.getName() == CoinName.Quarter.name()) {
				assertEquals(Integer.valueOf(8), coin.getQuantity());
			}
		});

	}

	@Test
	public void successSingleRequestWithMoreAlgo() throws Exception {

		initializeCoinStore();
		CoinExchangeRequest request = new CoinExchangeRequest();
		List<String> bills = new ArrayList<>();
		bills.add("5");
		bills.add("2");
		request.setBills(bills);
		CoinExchangeResponse response = coinChangerService.changeBillForCoins(request, "more");

		response.getCoins().forEach(coin -> {
			if (coin.getName() == CoinName.Penny.name()) {
				assertEquals(Integer.valueOf(100), coin.getQuantity());
			} else if (coin.getName() == CoinName.Nickel.name()) {
				assertEquals(Integer.valueOf(50), coin.getQuantity());
			} else if (coin.getName() == CoinName.Dime.name()) {
				assertEquals(Integer.valueOf(20), coin.getQuantity());
			} else if (coin.getName() == CoinName.Quarter.name()) {
				assertEquals(Integer.valueOf(6), coin.getQuantity());
			}
		});

	}

	@Test
	public void successMultipleRequestWithLessAlgo() throws Exception {
		initializeCoinStore();
		CoinExchangeRequest firstRequest = new CoinExchangeRequest();
		List<String> bills = new ArrayList<>();
		bills.add("5");
		bills.add("2");
		firstRequest.setBills(bills);
		CoinExchangeResponse firstResponse = coinChangerService.changeBillForCoins(firstRequest, "less");
		firstResponse.getCoins().forEach(coin -> {
			if (coin.getName() == CoinName.HalfDollar.name()) {
				assertEquals(Integer.valueOf(10), coin.getQuantity());
			} else if (coin.getName() == CoinName.Quarter.name()) {
				assertEquals(Integer.valueOf(8), coin.getQuantity());
			}
		});
		CoinExchangeRequest secondRequest = new CoinExchangeRequest();
		List<String> bills1 = new ArrayList<>();
		bills1.add("2");
		secondRequest.setBills(bills1);
		CoinExchangeResponse secondResponse = coinChangerService.changeBillForCoins(secondRequest, "less");
		secondResponse.getCoins().forEach(coin -> {
			if (coin.getName() == CoinName.Quarter.name()) {
				assertEquals(Integer.valueOf(2), coin.getQuantity());
			} else if (coin.getName() == CoinName.Dime.name()) {
				assertEquals(Integer.valueOf(15), coin.getQuantity());
			}
		});

	}

	@Test
	public void successMultipleRequestWithMoreAlgo() throws Exception {
		initializeCoinStore();
		CoinExchangeRequest firstRequest = new CoinExchangeRequest();
		List<String> bills = new ArrayList<>();
		bills.add("5");
		bills.add("2");
		firstRequest.setBills(bills);
		CoinExchangeResponse firstResponse = coinChangerService.changeBillForCoins(firstRequest, "more");
		firstResponse.getCoins().forEach(coin -> {
			if (coin.getName() == CoinName.Penny.name()) {
				assertEquals(Integer.valueOf(100), coin.getQuantity());
			} else if (coin.getName() == CoinName.Nickel.name()) {
				assertEquals(Integer.valueOf(50), coin.getQuantity());
			} else if (coin.getName() == CoinName.Dime.name()) {
				assertEquals(Integer.valueOf(20), coin.getQuantity());
			} else if (coin.getName() == CoinName.Quarter.name()) {
				assertEquals(Integer.valueOf(6), coin.getQuantity());
			}
		});
		CoinExchangeRequest secondRequest = new CoinExchangeRequest();
		List<String> bills1 = new ArrayList<>();
		bills1.add("2");
		secondRequest.setBills(bills1);
		CoinExchangeResponse secondResponse = coinChangerService.changeBillForCoins(secondRequest, "more");
		secondResponse.getCoins().forEach(coin -> {
			if (coin.getName() == CoinName.Quarter.name()) {
				assertEquals(Integer.valueOf(4), coin.getQuantity());
			} else if (coin.getName() == CoinName.HalfDollar.name()) {
				assertEquals(Integer.valueOf(2), coin.getQuantity());
			}
		});

	}

	@Test
	public void successMultipleConcurrentRequest() throws Exception {

		initializeCoinStore();

		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		CompletableFuture<CoinExchangeResponse> thread1 = CompletableFuture.supplyAsync(() -> {
			CoinExchangeRequest request = new CoinExchangeRequest();
			List<String> bills = new ArrayList<>();
			bills.add("5");
			bills.add("2");
			request.setBills(bills);
			CoinExchangeResponse response = null;
			try {
				response = coinChangerService.changeBillForCoins(request, "less");
			} catch (Exception e) {

				e.printStackTrace();
			}
			return response;
		}, threadPool);

		CompletableFuture<CoinExchangeResponse> thread2 = CompletableFuture.supplyAsync(() -> {
			CoinExchangeRequest request = new CoinExchangeRequest();
			List<String> bills = new ArrayList<>();
			bills.add("2");
			request.setBills(bills);
			CoinExchangeResponse response = null;
			try {
				response = coinChangerService.changeBillForCoins(request, "less");
			} catch (Exception e) {

				e.printStackTrace();
			}
			return response;
		}, threadPool);

		CompletableFuture.allOf(thread1, thread2).join();
		threadPool.shutdown();
		assertEquals(Integer.valueOf(0), Integer.valueOf(CoinStore.getInventory(CoinName.HalfDollar)));
		assertEquals(Integer.valueOf(0), Integer.valueOf(CoinStore.getInventory(CoinName.Quarter)));
		assertEquals(Integer.valueOf(5), Integer.valueOf(CoinStore.getInventory(CoinName.Dime)));
		assertEquals(Integer.valueOf(50), Integer.valueOf(CoinStore.getInventory(CoinName.Nickel)));
		assertEquals(Integer.valueOf(100), Integer.valueOf(CoinStore.getInventory(CoinName.Penny)));

	}

	@Test(expected = NotEnoughCoinsException.class)
	public void errorNotEnoughCoins() throws Exception {
		initializeCoinStore();
		CoinExchangeRequest request = new CoinExchangeRequest();
		List<String> bills = new ArrayList<>();
		bills.add("50");
		request.setBills(bills);
		coinChangerService.changeBillForCoins(request,"less");
	}

	@Test(expected = ValidationException.class)
	public void errorValidation() throws Exception {
		initializeCoinStore();
		CoinExchangeRequest request = new CoinExchangeRequest();
		List<String> bills = new ArrayList<>();
		bills.add("2.25");
		request.setBills(bills);
		coinChangerService.changeBillForCoins(request,"less");
	}

}
