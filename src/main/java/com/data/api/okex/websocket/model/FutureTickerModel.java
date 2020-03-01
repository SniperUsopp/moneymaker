package com.data.api.okex.websocket.model;

/**
 * Created by junji on 2020/3/1.
 */
public class FutureTickerModel {

    private String last;
    private String best_bid;
    private String high_24h;
    private String low_24h;
    private String volume_24h;
    private String open_interest;
    private String best_ask;
    private String instrument_id;
    private String open_24h;
    private String timestamp;
    private String volume_token_24h;

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getBest_bid() {
        return best_bid;
    }

    public void setBest_bid(String best_bid) {
        this.best_bid = best_bid;
    }

    public String getHigh_24h() {
        return high_24h;
    }

    public void setHigh_24h(String high_24h) {
        this.high_24h = high_24h;
    }

    public String getLow_24h() {
        return low_24h;
    }

    public void setLow_24h(String low_24h) {
        this.low_24h = low_24h;
    }

    public String getVolume_24h() {
        return volume_24h;
    }

    public void setVolume_24h(String volume_24h) {
        this.volume_24h = volume_24h;
    }

    public String getOpen_interest() {
        return open_interest;
    }

    public void setOpen_interest(String open_interest) {
        this.open_interest = open_interest;
    }

    public String getBest_ask() {
        return best_ask;
    }

    public void setBest_ask(String best_ask) {
        this.best_ask = best_ask;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getOpen_24h() {
        return open_24h;
    }

    public void setOpen_24h(String open_24h) {
        this.open_24h = open_24h;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVolume_token_24h() {
        return volume_token_24h;
    }

    public void setVolume_token_24h(String volume_token_24h) {
        this.volume_token_24h = volume_token_24h;
    }
}
