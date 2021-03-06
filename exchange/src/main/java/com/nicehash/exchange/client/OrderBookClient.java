package com.nicehash.exchange.client;

import com.nicehash.exchange.client.domain.market.OrderBookSync;

import java.io.Closeable;

/**
 * OrderBook sync client
 */
public interface OrderBookClient extends Closeable {
    /**
     * Get order book instance that is constantly synced,
     * until closed.
     *
     * @param symbol the order book symbol
     * @return syncable order book instance
     */
    OrderBookSync orderBook(String symbol);
}
