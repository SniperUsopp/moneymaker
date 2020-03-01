package com.data.api.okex.websocket.model;

import java.util.List;

/**
 * Created by junji on 2020/3/1.
 */
public class FutureTickerWebSocketResponse {

    private String table;
    private List<FutureTickerModel> data;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<FutureTickerModel> getData() {
        return data;
    }

    public void setData(List<FutureTickerModel> data) {
        this.data = data;
    }
}
