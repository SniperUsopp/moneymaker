package com.data.api.okex.websocket;

import java.util.List;

/**
 * Created by junji on 2019/1/28.
 */
public class RequestModel {

    private String op;
    private List<String> args;

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
