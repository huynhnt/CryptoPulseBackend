package com.trading.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trading.modal.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page, int perPage) throws Exception;
    String getMarketChart(String coinId,int days) throws Exception;
    String getCoinDetails(String coinId) throws JsonProcessingException;

    Coin findById(String coinId) throws Exception;

    List<Coin> searchCoin(String keyword) throws Exception;

    String getTop50CoinsByMarketCapRank();

    String getTreadingCoins();
}
