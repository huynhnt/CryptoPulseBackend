package com.trading.response;

import lombok.Data;
import java.util.List;

@Data
public class CoinGeckoSearchResponse {
    private List<CoinGeckoSearchItem> coins;
}
