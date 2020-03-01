package com.data.api.okex.websocket.model;

import java.util.List;

/**
 * Created by junji on 2020/2/29.
 */
public class FutureDepth12ThtWebSocketResponse {

    private String table;
    private String action;
    private List<FutureDepth12ThtModel> data;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<FutureDepth12ThtModel> getData() {
        return data;
    }

    public void setData(List<FutureDepth12ThtModel> data) {
        this.data = data;
    }
}
