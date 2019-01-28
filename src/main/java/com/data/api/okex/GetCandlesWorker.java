package com.data.api.okex;

import com.alibaba.fastjson.JSON;
import com.okcoin.commons.okex.open.api.bean.spot.result.Book;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.spot.SpotProductAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotProductAPIServiceImpl;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by junji on 2019/1/6.
 */
public class GetCandlesWorker {

    public static void main(String[] argv) {
        APIConfiguration config = new APIConfiguration();
        config.setEndpoint(Constants.BASE_URL);
        config.setApiKey(Constants.API_KEY);
        config.setSecretKey(Constants.SECRET_KEY);
        config.setPassphrase(Constants.KEY_PASS);
        config.setPrint(false);

        final BigDecimal jvhe = new BigDecimal("0.00000001");

        final SpotProductAPIService spotProductAPIService = new SpotProductAPIServiceImpl(config);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Book result = spotProductAPIService.bookProductsByProductId("EOS-USDT", "10", jvhe);
                System.out.println(JSON.toJSONString(result));
            }
        }, 1000, 1000);


    }

}
