package com.data.api.okex.websocket;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import okio.ByteString;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;

/**
 * Created by junji on 2019/1/28.
 */
public class Application {



    public static void main(String[] args) {


        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("wss://real.okex.com:10442/ws/v3")
                .build();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                List<String> topicList = new ArrayList<>();
                topicList.add("spot/candle60s:EOS-USDT");
                topicList.add("spot/candle60s:BTC-USDT");

                RequestModel requestModel = new RequestModel();
                requestModel.setOp("subscribe");
                requestModel.setArgs(topicList);
                webSocket.send(JSON.toJSONString(requestModel));
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {

                System.out.println(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {

                System.out.println(uncompress(bytes.toByteArray()));
            }
        });
    }



    private static String uncompress(byte[] bytes) {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
             final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             final Deflate64CompressorInputStream zin = new Deflate64CompressorInputStream(in)) {
            final byte[] buffer = new byte[1024];
            int offset;
            while (-1 != (offset = zin.read(buffer))) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}
