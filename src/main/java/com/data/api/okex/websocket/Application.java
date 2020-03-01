package com.data.api.okex.websocket;

import com.alibaba.fastjson.JSON;
import com.data.api.okex.websocket.model.*;
import okhttp3.*;
import okio.ByteString;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by junji on 2019/1/28.
 */
public class Application {

//    private static Logger eosUsdtCandle60Logger = LogManager.getLogger("EOS-USDT-CANDLE60-LOGGER");
//    private static Logger btcUsdtCandle60Logger = LogManager.getLogger("BTC-USDT-CANDLE60-LOGGER");
//    private static Logger bchUsdtCandle60Logger = LogManager.getLogger("BCH-USDT-CANDLE60-LOGGER");
//    private static Logger ethUsdtCandle60Logger = LogManager.getLogger("ETH-USDT-CANDLE60-LOGGER");
//    private static Logger ltcUsdtCandle60Logger = LogManager.getLogger("LTC-USDT-CANDLE60-LOGGER");
//
//    private static Logger eosUsdtTradeLogger = LogManager.getLogger("EOS-USDT-TRADE-LOGGER");
//    private static Logger btcUsdtTradeLogger = LogManager.getLogger("BTC-USDT-TRADE-LOGGER");
//    private static Logger bchUsdtTradeLogger = LogManager.getLogger("BCH-USDT-TRADE-LOGGER");
//    private static Logger ethUsdtTradeLogger = LogManager.getLogger("ETH-USDT-TRADE-LOGGER");
//    private static Logger ltcUsdtTradeLogger = LogManager.getLogger("LTC-USDT-TRADE-LOGGER");
//
//    private static Logger eosUsdtTickerLogger = LogManager.getLogger("EOS-USDT-TICKER-LOGGER");
//    private static Logger btcUsdtTickerLogger = LogManager.getLogger("BTC-USDT-TICKER-LOGGER");
//    private static Logger bchUsdtTickerLogger = LogManager.getLogger("BCH-USDT-TICKER-LOGGER");
//    private static Logger ethUsdtTickerLogger = LogManager.getLogger("ETH-USDT-TICKER-LOGGER");
//    private static Logger ltcUsdtTickerLogger = LogManager.getLogger("LTC-USDT-TICKER-LOGGER");
//
//    private static Logger eosUsdtDepth5Logger = LogManager.getLogger("EOS-USDT-DEPTH5-LOGGER");
//    private static Logger btcUsdtDepth5Logger = LogManager.getLogger("BTC-USDT-DEPTH5-LOGGER");
//    private static Logger bchUsdtDepth5Logger = LogManager.getLogger("BCH-USDT-DEPTH5-LOGGER");
//    private static Logger ethUsdtDepth5Logger = LogManager.getLogger("ETH-USDT-DEPTH5-LOGGER");
//    private static Logger ltcUsdtDepth5Logger = LogManager.getLogger("LTC-USDT-DEPTH5-LOGGER");

    private static Logger btcUsdFuturesTickerLogger = LogManager.getLogger("BTC-USD-FUTURES-TICKER-LOGGER");
    private static Logger eosUsdFuturesTickerLogger = LogManager.getLogger("EOS-USD-FUTURES-TICKER-LOGGER");
    private static Logger ethUsdFuturesTickerLogger = LogManager.getLogger("ETH-USD-FUTURES-TICKER-LOGGER");
    private static Logger bchUsdFuturesTickerLogger = LogManager.getLogger("BCH-USD-FUTURES-TICKER-LOGGER");

    private static Logger btcUsdFuturesDepth12TbtLogger = LogManager.getLogger("BTC-USD-FUTURES-DEPTH-LOGGER");
    private static Logger eosUsdFuturesDepth12TbtLogger = LogManager.getLogger("EOS-USD-FUTURES-DEPTH-LOGGER");
    private static Logger ethUsdFuturesDepth12TbtLogger = LogManager.getLogger("ETH-USD-FUTURES-DEPTH-LOGGER");
    private static Logger bchUsdFuturesDepth12TbtLogger = LogManager.getLogger("BCH-USD-FUTURES-DEPTH-LOGGER");

    private static Logger btcUsdFuturesTradeBigDealStrategyLevel1Logger = LogManager.getLogger("BTC-USD-FUTURES-TRADE-BIGDEAL-STRATEGY-LOGGER-LEVEL1");
    private static Logger btcUsdFuturesTradeBigDealStrategyLevel2Logger = LogManager.getLogger("BTC-USD-FUTURES-TRADE-BIGDEAL-STRATEGY-LOGGER-LEVEL2");
    private static Logger btcUsdFuturesTradeBigDealStrategyLevel3Logger = LogManager.getLogger("BTC-USD-FUTURES-TRADE-BIGDEAL-STRATEGY-LOGGER-LEVEL3");

//    private static Logger eosUsdFuturesTradeBigDealStrategyLogger = LogManager.getLogger("EOS-USD-FUTURES-TRADE-BIGDEAL-STRATEGY-LOGGER");
//    private static Logger ethUsdFuturesTradeBigDealStrategyLogger = LogManager.getLogger("ETH-USD-FUTURES-TRADE-BIGDEAL-STRATEGY-LOGGER");
//    private static Logger bchUsdFuturesTradeBigDealStrategyLogger = LogManager.getLogger("BCH-USD-FUTURES-TRADE-BIGDEAL-STRATEGY-LOGGER");

    private static String futurePaidTime = "200327";   //合约交割日期

    //------------------btc params----------------------
    private static Double btcBigDealThreshold1 = 150000.0;    //大单判断阈值1
    private static Double btcBidDealThreshold2 = 120000.0;    //大单判断阈值2
    private static Double btcBidDealThreshold3 = 100000.0;    //大单判断阈值3
    private static Double btcOrderDiff = 2.0;               //下单价格与大单价格的差值
    private static Double btcSaleDiff = 1.0;                //平仓价格与大单的差值
    private static Double btcBaseTakeProfitThreshold = 50.0;   //基础止盈阈值
    private static Double btcCleanThreshold = 30000.0;         //大单清盘阈值
    private static Double btcDynamicTakeProfitStepSize = 50.0;    //移动止盈步长
    //----------------------------------------------

//    private static Double eosBigDealThreshold1 = 3780621.0;
//    private static Double eosBigDealThreshold2 = 3024497.0;
//
//    private static Double ethBigDealThreshold1 = 59002.0;
//    private static Double ethBigDealThreshold2 = 47202.0;
//
//    private static Double bchBigDealThreshold1 = 42383.0;
//    private static Double bchBigDealThreshold2 = 33906.0;

    private static Double btcAskLevel1BuildPrice = -1.0;
    private static Double btcAskLevel2BuildPrice = -1.0;
    private static Double btcAskLevel3BuildPrice = -1.0;
    private static Double btcBidLevel1BuildPrice = -1.0;
    private static Double btcBidLevel2BuildPrice = -1.0;
    private static Double btcBidLevel3BuildPrice = -1.0;

    private static Double btcAskLevel1CleanPrice = -1.0;
    private static Double btcAskLevel2CleanPrice = -1.0;
    private static Double btcAskLevel3CleanPrice = -1.0;
    private static Double btcBidLevel1CleanPrice = -1.0;
    private static Double btcBidLevel2CleanPrice = -1.0;
    private static Double btcBidLevel3CleanPrice = -1.0;

    private static Double btcAskLevel1ForceCleanPrice = -1.0;
    private static Double btcAskLevel2ForceCleanPrice = -1.0;
    private static Double btcAskLevel3ForceCleanPrice = -1.0;
    private static Double btcBidLevel1ForceCleanPrice = -1.0;
    private static Double btcBidLevel2ForceCleanPrice = -1.0;
    private static Double btcBidLevel3ForceCleanPrice = -1.0;

    private static Boolean btcAskLevel1HoldState = false;
    private static Boolean btcAskLevel2HoldState = false;
    private static Boolean btcAskLevel3HoldState = false;
    private static Boolean btcBidLevel1HoldState = false;
    private static Boolean btcBidLevel2HoldState = false;
    private static Boolean btcBidLevel3HoldState = false;

    private static Double btcFutureCurrentPrice = -1.0;

    public static void main(String[] args) {

        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("wss://real.okex.com:8443/ws/v3")
                .build();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                List<String> topicList = buildFutureTopicList();

                RequestModel requestModel = new RequestModel();
                requestModel.setOp("subscribe");
                requestModel.setArgs(topicList);
                webSocket.send(JSON.toJSONString(requestModel));
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                saveDate(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                String text = uncompress(bytes.toByteArray());
                saveDate(text);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                System.out.println("closing: " + code + " " + reason);
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                System.out.println("closed: " + code + " " + reason);
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                System.out.println("Filed:" + t.getMessage() + " " + JSON.toJSONString(response));

                super.onFailure(webSocket, t, response);
            }
        });
    }

    private static void saveDate(String text) {
        if(text.contains("subscribe")) {
            return;
        }

//        if(text.contains("spot/candle60s")) {
//            if(text.contains("EOS-USDT")) {
//                eosUsdtCandle60Logger.info(text);
//            } else if(text.contains("BTC-USDT")) {
//                btcUsdtCandle60Logger.info(text);
//            } else if(text.contains("BCH-USDT")) {
//                bchUsdtCandle60Logger.info(text);
//            } else if(text.contains("ETH-USDT")) {
//                ethUsdtCandle60Logger.info(text);
//            } else if(text.contains("LTC-USDT")) {
//                ltcUsdtCandle60Logger.info(text);
//            }
//        } else if(text.contains("spot/trade")) {
//            if(text.contains("EOS-USDT")) {
//                eosUsdtTradeLogger.info(text);
//            } else if(text.contains("BTC-USDT")) {
//                btcUsdtTradeLogger.info(text);
//            } else if(text.contains("BCH-USDT")) {
//                bchUsdtTradeLogger.info(text);
//            } else if(text.contains("ETH-USDT")) {
//                ethUsdtTradeLogger.info(text);
//            } else if(text.contains("LTC-USDT")) {
//                ltcUsdtTradeLogger.info(text);
//            }
//        } else if(text.contains("spot/ticker")) {
//            if(text.contains("EOS-USDT")) {
//                eosUsdtTickerLogger.info(text);
//            } else if(text.contains("BTC-USDT")) {
//                btcUsdtTickerLogger.info(text);
//            } else if(text.contains("BCH-USDT")) {
//                bchUsdtTickerLogger.info(text);
//            } else if(text.contains("ETH-USDT")) {
//                ethUsdtTickerLogger.info(text);
//            } else if(text.contains("LTC-USDT")) {
//                ltcUsdtTickerLogger.info(text);
//            }
//        } else if(text.contains("spot/depth5")) {
//            if(text.contains("EOS-USDT")) {
//                eosUsdtDepth5Logger.info(text);
//            } else if(text.contains("BTC-USDT")) {
//                btcUsdtDepth5Logger.info(text);
//            } else if(text.contains("BCH-USDT")) {
//                bchUsdtDepth5Logger.info(text);
//            } else if(text.contains("ETH-USDT")) {
//                ethUsdtDepth5Logger.info(text);
//            } else if(text.contains("LTC-USDT")) {
//                ltcUsdtDepth5Logger.info(text);
//            }
//        }
//
        try {
            if (text.contains("futures/depth")) {
                FutureDepth12ThtWebSocketResponse response = JSON.parseObject(text, FutureDepth12ThtWebSocketResponse.class);
                for (FutureDepth12ThtModel model : response.getData()) {
                    if (("BTC-USD-" + futurePaidTime).equals(model.getInstrument_id())) {
                        btcCheckBidDeal(model);
                        btcUsdFuturesDepth12TbtLogger.info(JSON.toJSONString(model));
                    } else if (("EOS-USD-" + futurePaidTime).equals(model.getInstrument_id())) {
                        eosUsdFuturesDepth12TbtLogger.info(JSON.toJSONString(model));
                    } else if (("BCH-USD-" + futurePaidTime).equals(model.getInstrument_id())) {
                        bchUsdFuturesDepth12TbtLogger.info(JSON.toJSONString(model));
                    } else if (("ETH-USD-" + futurePaidTime).equals(model.getInstrument_id())) {
                        ethUsdFuturesDepth12TbtLogger.info(JSON.toJSONString(model));
                    }
                }
            }

            if (text.contains("futures/ticker")) {
                FutureTickerWebSocketResponse response = JSON.parseObject(text, FutureTickerWebSocketResponse.class);
                for (FutureTickerModel model : response.getData()) {
                    if (("BTC-USD-" + futurePaidTime).equals(model.getInstrument_id())) {
                        btcFutureCurrentPrice = Double.valueOf(model.getLast());
                        btcCheckCurrentPrice(btcFutureCurrentPrice);
                        btcUsdFuturesTickerLogger.info(JSON.toJSONString(model));
                    } else if (("EOS-USD-" + futurePaidTime).equals(model.getInstrument_id())) {
                        eosUsdFuturesTickerLogger.info(JSON.toJSONString(model));
                    } else if (("BCH-USD-" + futurePaidTime).equals(model.getInstrument_id())) {
                        bchUsdFuturesTickerLogger.info(JSON.toJSONString(model));
                    } else if (("ETH-USD-" + futurePaidTime).equals(model.getInstrument_id())) {
                        ethUsdFuturesTickerLogger.info(JSON.toJSONString(model));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void btcCheckCurrentPrice(Double currentPrice) {
        if(btcAskLevel1BuildPrice > 0) {
            if(btcAskLevel1HoldState) {
                // 持仓
                if(currentPrice > btcAskLevel1ForceCleanPrice ||
                        btcAskLevel1BuildPrice - currentPrice < btcAskLevel1CleanPrice) {
                    // 止损或止盈条件达到
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("检测到止损或者止盈,做空仓位清仓.建仓价格为:" + btcAskLevel1BuildPrice
                            + ".平仓价格为:" + currentPrice
                            + ".毛利润为:" + (btcAskLevel1BuildPrice - currentPrice)
                            + ".手续费为:" + (currentPrice * 0.001)
                            + ".净利润为:" + (btcAskLevel1BuildPrice - currentPrice * 1.001));
                    btcAskLevel1BuildPrice = -1.0;
                    btcAskLevel1CleanPrice = -1.0;
                    btcAskLevel1ForceCleanPrice = -1.0;
                    btcAskLevel1HoldState = false;
                } else {
                    if (btcAskLevel1BuildPrice - currentPrice >= btcBaseTakeProfitThreshold && btcAskLevel1CleanPrice < 0) {
                        btcAskLevel1CleanPrice = btcBaseTakeProfitThreshold;
                    }

                    if (btcAskLevel1CleanPrice > 0 &&
                            (btcAskLevel1BuildPrice - currentPrice - btcDynamicTakeProfitStepSize) > btcAskLevel1CleanPrice) {
                        btcAskLevel1CleanPrice = btcAskLevel1BuildPrice - currentPrice - btcDynamicTakeProfitStepSize;
                    }
                }
            } else {
                // 未持仓
                if(currentPrice >= btcAskLevel1BuildPrice) {
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("做空单建仓成功.建仓价格:" + currentPrice + ".");
                    btcAskLevel1BuildPrice = currentPrice;
                    btcAskLevel1HoldState = true;
                }
            }
        }

        if(btcAskLevel2BuildPrice > 0) {
            if(btcAskLevel2HoldState) {
                // 持仓
                if(currentPrice > btcAskLevel2ForceCleanPrice ||
                        btcAskLevel2BuildPrice - currentPrice < btcAskLevel2CleanPrice) {
                    // 止损或止盈条件达到
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("检测到止损或者止盈,做空仓位清仓.建仓价格为:" + btcAskLevel2BuildPrice
                            + ".平仓价格为:" + currentPrice
                            + ".毛利润为:" + (btcAskLevel2BuildPrice - currentPrice)
                            + ".手续费为:" + (currentPrice * 0.001)
                            + ".净利润为:" + (btcAskLevel2BuildPrice - currentPrice * 1.001));
                    btcAskLevel2BuildPrice = -1.0;
                    btcAskLevel2CleanPrice = -1.0;
                    btcAskLevel2ForceCleanPrice = -1.0;
                    btcAskLevel2HoldState = false;
                } else {
                    if (btcAskLevel2BuildPrice - currentPrice >= btcBaseTakeProfitThreshold && btcAskLevel2CleanPrice < 0) {
                        btcAskLevel2CleanPrice = btcBaseTakeProfitThreshold;
                    }

                    if (btcAskLevel2CleanPrice > 0 &&
                            (btcAskLevel2BuildPrice - currentPrice - btcDynamicTakeProfitStepSize) > btcAskLevel2CleanPrice) {
                        btcAskLevel2CleanPrice = btcAskLevel2BuildPrice - currentPrice - btcDynamicTakeProfitStepSize;
                    }
                }
            } else {
                // 未持仓
                if(currentPrice >= btcAskLevel2BuildPrice) {
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("做空单建仓成功.建仓价格:" + currentPrice + ".");
                    btcAskLevel2BuildPrice = currentPrice;
                    btcAskLevel2HoldState = true;
                }
            }
        }

        if(btcAskLevel3BuildPrice > 0) {
            if(btcAskLevel3HoldState) {
                // 持仓
                if(currentPrice > btcAskLevel3ForceCleanPrice ||
                        btcAskLevel3BuildPrice - currentPrice < btcAskLevel3CleanPrice) {
                    // 止损或止盈条件达到
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("检测到止损或者止盈,做空仓位清仓.建仓价格为:" + btcAskLevel3BuildPrice
                            + ".平仓价格为:" + currentPrice
                            + ".毛利润为:" + (btcAskLevel3BuildPrice - currentPrice)
                            + ".手续费为:" + (currentPrice * 0.001)
                            + ".净利润为:" + (btcAskLevel3BuildPrice - currentPrice * 1.001));
                    btcAskLevel3BuildPrice = -1.0;
                    btcAskLevel3CleanPrice = -1.0;
                    btcAskLevel3ForceCleanPrice = -1.0;
                    btcAskLevel3HoldState = false;
                } else {
                    if (btcAskLevel3BuildPrice - currentPrice >= btcBaseTakeProfitThreshold && btcAskLevel3CleanPrice < 0) {
                        btcAskLevel3CleanPrice = btcBaseTakeProfitThreshold;
                    }

                    if (btcAskLevel3CleanPrice > 0 &&
                            (btcAskLevel3BuildPrice - currentPrice - btcDynamicTakeProfitStepSize) > btcAskLevel3CleanPrice) {
                        btcAskLevel3CleanPrice = btcAskLevel3BuildPrice - currentPrice - btcDynamicTakeProfitStepSize;
                    }
                }
            } else {
                // 未持仓
                if(currentPrice >= btcAskLevel3BuildPrice) {
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("做空单建仓成功.建仓价格:" + currentPrice + ".");
                    btcAskLevel3BuildPrice = currentPrice;
                    btcAskLevel3HoldState = true;
                }
            }
        }

        if(btcBidLevel1BuildPrice > 0) {
            if(btcBidLevel1HoldState) {
                // 持仓
                if(currentPrice < btcBidLevel1ForceCleanPrice ||
                        currentPrice - btcBidLevel1BuildPrice < btcBidLevel1CleanPrice) {
                    // 止损或止盈条件达到
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("检测到止损或者止盈,做多仓位清仓.建仓价格为:" + btcBidLevel1BuildPrice
                            + ".平仓价格为:" + currentPrice
                            + ".毛利润为:" + (currentPrice - btcBidLevel1BuildPrice)
                            + ".手续费为:" + (currentPrice * 0.001)
                            + ".净利润为:" + (currentPrice * 0.999 - btcBidLevel1BuildPrice));
                    btcBidLevel3BuildPrice = -1.0;
                    btcBidLevel3CleanPrice = -1.0;
                    btcBidLevel3ForceCleanPrice = -1.0;
                    btcBidLevel3HoldState = false;
                } else {
                    if (currentPrice - btcBidLevel1BuildPrice >= btcBaseTakeProfitThreshold && btcBidLevel1CleanPrice < 0) {
                        btcBidLevel1CleanPrice = btcBaseTakeProfitThreshold;
                    }

                    if (btcBidLevel1CleanPrice > 0 &&
                            (currentPrice - btcBidLevel1BuildPrice - btcDynamicTakeProfitStepSize) > btcBidLevel1CleanPrice) {
                        btcBidLevel1CleanPrice = currentPrice - btcBidLevel1BuildPrice - btcDynamicTakeProfitStepSize;
                    }
                }
            } else {
                // 未持仓
                if(btcBidLevel1BuildPrice >= currentPrice) {
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("做多单建仓成功.建仓价格:" + currentPrice + ".");
                    btcBidLevel1BuildPrice = currentPrice;
                    btcBidLevel1HoldState = true;
                }
            }
        }

        if(btcBidLevel2BuildPrice > 0) {
            if(btcBidLevel2HoldState) {
                // 持仓
                if(currentPrice < btcBidLevel2ForceCleanPrice ||
                        currentPrice - btcBidLevel2BuildPrice < btcBidLevel2CleanPrice) {
                    // 止损或止盈条件达到
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("检测到止损或者止盈,做多仓位清仓.建仓价格为:" + btcBidLevel2BuildPrice
                            + ".平仓价格为:" + currentPrice
                            + ".毛利润为:" + (currentPrice - btcBidLevel2BuildPrice)
                            + ".手续费为:" + (currentPrice * 0.001)
                            + ".净利润为:" + (currentPrice * 0.999 - btcBidLevel2BuildPrice));
                    btcBidLevel3BuildPrice = -1.0;
                    btcBidLevel3CleanPrice = -1.0;
                    btcBidLevel3ForceCleanPrice = -1.0;
                    btcBidLevel3HoldState = false;
                } else {
                    if (currentPrice - btcBidLevel2BuildPrice >= btcBaseTakeProfitThreshold && btcBidLevel2CleanPrice < 0) {
                        btcBidLevel2CleanPrice = btcBaseTakeProfitThreshold;
                    }

                    if (btcBidLevel2CleanPrice > 0 &&
                            (currentPrice - btcBidLevel2BuildPrice - btcDynamicTakeProfitStepSize) > btcBidLevel2CleanPrice) {
                        btcBidLevel2CleanPrice = currentPrice - btcBidLevel2BuildPrice - btcDynamicTakeProfitStepSize;
                    }
                }
            } else {
                // 未持仓
                if(btcBidLevel2BuildPrice >= currentPrice) {
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("做多单建仓成功.建仓价格:" + currentPrice + ".");
                    btcBidLevel2BuildPrice = currentPrice;
                    btcBidLevel2HoldState = true;
                }
            }
        }

        if(btcBidLevel3BuildPrice > 0) {
            if(btcBidLevel3HoldState) {
                // 持仓
                if(currentPrice < btcBidLevel3ForceCleanPrice ||
                        currentPrice - btcBidLevel3BuildPrice < btcBidLevel3CleanPrice) {
                    // 止损或止盈条件达到
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("检测到止损或者止盈,做多仓位清仓.建仓价格为:" + btcBidLevel3BuildPrice
                            + ".平仓价格为:" + currentPrice
                            + ".毛利润为:" + (currentPrice - btcBidLevel3BuildPrice)
                            + ".手续费为:" + (currentPrice * 0.001)
                            + ".净利润为:" + (currentPrice * 0.999 - btcBidLevel3BuildPrice));
                    btcBidLevel3BuildPrice = -1.0;
                    btcBidLevel3CleanPrice = -1.0;
                    btcBidLevel3ForceCleanPrice = -1.0;
                    btcBidLevel3HoldState = false;
                } else {
                    if (currentPrice - btcBidLevel3BuildPrice >= btcBaseTakeProfitThreshold && btcBidLevel3CleanPrice < 0) {
                        btcBidLevel3CleanPrice = btcBaseTakeProfitThreshold;
                    }

                    if (btcBidLevel3CleanPrice > 0 &&
                            (currentPrice - btcBidLevel3BuildPrice - btcDynamicTakeProfitStepSize) > btcBidLevel3CleanPrice) {
                        btcBidLevel3CleanPrice = currentPrice - btcBidLevel3BuildPrice - btcDynamicTakeProfitStepSize;
                    }
                }
            } else {
                // 未持仓
                if(btcBidLevel3BuildPrice >= currentPrice) {
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("做多单建仓成功.建仓价格:" + currentPrice + ".");
                    btcBidLevel3BuildPrice = currentPrice;
                    btcBidLevel3HoldState = true;
                }
            }
        }
    }

    private static void btcCheckBidDeal(FutureDepth12ThtModel model) {
        Double askLevel1Price = -1.0;
        Double askLevel2Price = -1.0;
        Double askLevel3Price = -1.0;
        Boolean askCleanFlag = true;
        for(List<String> ask : model.getAsks()) {
            Double btcNum = Double.valueOf(ask.get(1));
            if(btcNum >= btcBigDealThreshold1) {
                askLevel1Price = Double.valueOf(ask.get(0));
            }

            if (btcNum >= btcBidDealThreshold2) {
                askLevel2Price = Double.valueOf(ask.get(0));
            }

            if (btcNum >= btcBidDealThreshold3) {
                askLevel3Price = Double.valueOf(ask.get(0));
            }

            if(btcNum > btcCleanThreshold) {
                askCleanFlag = false;
            }
        }

        if(askCleanFlag) {
            if(btcAskLevel1BuildPrice > 0) {
                if(btcAskLevel1HoldState){
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("检测到大单撤销或被吃,做空仓位强平.建仓价格为:" + btcAskLevel1BuildPrice
                            + ".平仓价格为:" + btcFutureCurrentPrice
                            + ".毛利润为:" + (btcAskLevel1BuildPrice - btcFutureCurrentPrice)
                            + ".手续费为:" + (btcFutureCurrentPrice * 0.001)
                            + ".净利润为:" + (btcAskLevel1BuildPrice - btcFutureCurrentPrice * 1.001));
                    btcAskLevel1BuildPrice = -1.0;
                    btcAskLevel1ForceCleanPrice = -1.0;
                    btcAskLevel1CleanPrice = -1.0;
                    btcAskLevel1HoldState = false;
                } else {
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("检测到大单撤销或被吃,取消挂单价格为:" + btcAskLevel1BuildPrice + "的做空挂单.");
                    btcAskLevel1BuildPrice = -1.0;
                    btcAskLevel1ForceCleanPrice = -1.0;
                    btcAskLevel1CleanPrice = -1.0;
                }
            }
            if(btcAskLevel2BuildPrice > 0) {
                if (btcAskLevel2HoldState) {
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("检测到大单撤销或被吃,做空仓位强平.建仓价格为:" + btcAskLevel2BuildPrice
                            + ".平仓价格为:" + btcFutureCurrentPrice
                            + ".毛利润为:" + (btcAskLevel2BuildPrice - btcFutureCurrentPrice)
                            + ".手续费为:" + (btcFutureCurrentPrice * 0.001)
                            + ".净利润为:" + (btcAskLevel2BuildPrice - btcFutureCurrentPrice * 1.001));
                    btcAskLevel2BuildPrice = -1.0;
                    btcAskLevel2ForceCleanPrice = -1.0;
                    btcAskLevel2CleanPrice = -1.0;
                    btcAskLevel2HoldState = false;
                } else {
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("检测到大单撤销或被吃,取消挂单价格为:" + btcAskLevel2BuildPrice + "的做空挂单.");
                    btcAskLevel2BuildPrice = -1.0;
                    btcAskLevel2ForceCleanPrice = -1.0;
                    btcAskLevel2CleanPrice = -1.0;
                }
            }
            if(btcAskLevel3BuildPrice > 0) {
                if (btcAskLevel3HoldState) {
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("检测到大单撤销或被吃,做空仓位强平.建仓价格为:" + btcAskLevel3BuildPrice
                            + ".平仓价格为:" + btcFutureCurrentPrice
                            + ".毛利润为:" + (btcAskLevel3BuildPrice - btcFutureCurrentPrice)
                            + ".手续费为:" + (btcFutureCurrentPrice * 0.001)
                            + ".净利润为:" + (btcAskLevel3BuildPrice - btcFutureCurrentPrice * 1.001));
                    btcAskLevel3BuildPrice = -1.0;
                    btcAskLevel3ForceCleanPrice = -1.0;
                    btcAskLevel3CleanPrice = -1.0;
                    btcAskLevel3HoldState = false;
                } else {
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("检测到大单撤销或被吃,取消挂单价格为:" + btcAskLevel3BuildPrice + "的做空挂单.");
                    btcAskLevel3BuildPrice = -1.0;
                    btcAskLevel3ForceCleanPrice = -1.0;
                    btcAskLevel3CleanPrice = -1.0;
                }
            }
        } else {
            if (askLevel1Price > 0) {
                btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("检测到卖方大于" + btcBigDealThreshold1 + "张合约.大单挂单价格为:" + askLevel1Price);
                if (btcAskLevel1HoldState) {
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("已经持仓不挂单.");
                } else {
                    btcAskLevel1BuildPrice = askLevel1Price - btcOrderDiff;
                    btcAskLevel1ForceCleanPrice = askLevel1Price + btcSaleDiff;
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("做空挂单,挂单价格为:" + btcAskLevel1BuildPrice + ",强平价格为:" + btcAskLevel1ForceCleanPrice);
                }
            }
            if (askLevel2Price > 0) {
                btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("检测到卖方大于" + btcBidDealThreshold2 + "张合约.大单挂单价格为:" + askLevel2Price);
                if (btcAskLevel2HoldState) {
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("已经持仓不挂单.");
                } else {
                    btcAskLevel2BuildPrice = askLevel2Price - btcOrderDiff;
                    btcAskLevel2ForceCleanPrice = askLevel2Price + btcSaleDiff;
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("做空挂单,挂单价格为:" + btcAskLevel2BuildPrice + ",强平价格为:" + btcAskLevel2ForceCleanPrice);
                }
            }
            if (askLevel3Price > 0) {
                btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("检测到卖方大于" + btcBidDealThreshold3 + "张合约.大单挂单价格为:" + askLevel3Price);
                if (btcAskLevel3HoldState) {
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("已经持仓不挂单.");
                } else {
                    btcAskLevel3BuildPrice = askLevel3Price - btcOrderDiff;
                    btcAskLevel3ForceCleanPrice = askLevel3Price + btcSaleDiff;
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("做空挂单,挂单价格为:" + btcAskLevel3BuildPrice + ",强平价格为:" + btcAskLevel3ForceCleanPrice);
                }
            }
        }

        Double bidLevel1Price = -1.0;
        Double bidLevel2Price = -1.0;
        Double bidLevel3Price = -1.0;
        Boolean bidCleanFlag = true;
        for(List<String> bid : model.getBids()) {
            Double btcNum = Double.valueOf(bid.get(1));
            if(btcNum >= btcBigDealThreshold1) {
                bidLevel1Price = Double.valueOf(bid.get(0));
            }

            if(btcNum >= btcBidDealThreshold2) {
                bidLevel2Price = Double.valueOf(bid.get(0));
            }

            if(btcNum >= btcBidDealThreshold3) {
                bidLevel3Price = Double.valueOf(bid.get(0));
            }

            if(btcNum > btcCleanThreshold) {
                bidCleanFlag = false;
            }
        }

        if(bidCleanFlag) {
            if(btcBidLevel1BuildPrice > 0) {
                if(btcBidLevel1HoldState){
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("检测到大单撤销或被吃,做多仓位强平.建仓价格为:" + btcBidLevel1BuildPrice
                            + ".平仓价格为:" + btcFutureCurrentPrice
                            + ".毛利润为:" + (btcFutureCurrentPrice - btcBidLevel1BuildPrice)
                            + ".手续费为:" + (btcFutureCurrentPrice * 0.001)
                            + ".净利润为:" + (btcFutureCurrentPrice * 0.999 - btcBidLevel1BuildPrice));
                    btcBidLevel1BuildPrice = -1.0;
                    btcBidLevel1ForceCleanPrice = -1.0;
                    btcBidLevel1CleanPrice = -1.0;
                    btcBidLevel1HoldState = false;
                } else {
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("检测到大单撤销或被吃,取消挂单价格为:" + btcBidLevel1BuildPrice + "的做多挂单.");
                    btcBidLevel1BuildPrice = -1.0;
                    btcBidLevel1ForceCleanPrice = -1.0;
                    btcBidLevel1CleanPrice = -1.0;
                }
            }
            if(btcBidLevel2BuildPrice > 0) {
                if (btcBidLevel2HoldState) {
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("检测到大单撤销或被吃,做多仓位强平.建仓价格为:" + btcBidLevel2BuildPrice
                            + ".平仓价格为:" + btcFutureCurrentPrice
                            + ".毛利润为:" + (btcFutureCurrentPrice - btcBidLevel2BuildPrice)
                            + ".手续费为:" + (btcFutureCurrentPrice * 0.001)
                            + ".净利润为:" + (btcFutureCurrentPrice * 0.999 - btcBidLevel2BuildPrice));
                    btcBidLevel2BuildPrice = -1.0;
                    btcBidLevel2ForceCleanPrice = -1.0;
                    btcBidLevel2CleanPrice = -1.0;
                    btcBidLevel2HoldState = false;
                } else {
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("检测到大单撤销或被吃,取消挂单价格为:" + btcBidLevel2BuildPrice + "的做多挂单.");
                    btcBidLevel2BuildPrice = -1.0;
                    btcBidLevel2ForceCleanPrice = -1.0;
                    btcBidLevel2CleanPrice = -1.0;
                }
            }
            if(btcBidLevel3BuildPrice > 0) {
                if (btcBidLevel3HoldState) {
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("检测到大单撤销或被吃,做多仓位强平.建仓价格为:" + btcBidLevel3BuildPrice
                            + ".平仓价格为:" + btcFutureCurrentPrice
                            + ".毛利润为:" + (btcFutureCurrentPrice - btcBidLevel3BuildPrice)
                            + ".手续费为:" + (btcFutureCurrentPrice * 0.001)
                            + ".净利润为:" + (btcFutureCurrentPrice * 0.999 - btcBidLevel3BuildPrice));
                    btcBidLevel3BuildPrice = -1.0;
                    btcBidLevel3ForceCleanPrice = -1.0;
                    btcBidLevel3CleanPrice = -1.0;
                    btcBidLevel3HoldState = false;
                } else {
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("检测到大单撤销或被吃,挂单建仓价格为:" + btcBidLevel3BuildPrice + "的做多挂单.");
                    btcBidLevel3BuildPrice = -1.0;
                    btcBidLevel3ForceCleanPrice = -1.0;
                    btcBidLevel3CleanPrice = -1.0;
                }
            }
        } else {
            if (bidLevel1Price > 0) {
                btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("检测到买方大于" + btcBigDealThreshold1 + "张合约.大单挂单价格为:" + bidLevel1Price);
                if (btcBidLevel1HoldState) {
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("已经持仓不挂单.");
                } else {
                    btcBidLevel1BuildPrice = bidLevel1Price + btcOrderDiff;
                    btcBidLevel1ForceCleanPrice = bidLevel1Price - btcSaleDiff;
                    btcUsdFuturesTradeBigDealStrategyLevel1Logger.info("做多挂单,挂单价格为:" + btcBidLevel1BuildPrice + ",强平价格为:" + btcBidLevel1ForceCleanPrice);
                }
            }
            if (bidLevel2Price > 0) {
                btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("检测到买方大于" + btcBidDealThreshold2 + "小于" + btcBigDealThreshold1 + "张合约.大单挂单价格为:" + askLevel2Price);
                if (btcBidLevel2HoldState) {
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("已经持仓不挂单.");
                } else {
                    btcBidLevel2BuildPrice = bidLevel2Price + btcOrderDiff;
                    btcBidLevel2ForceCleanPrice = bidLevel2Price - btcSaleDiff;
                    btcUsdFuturesTradeBigDealStrategyLevel2Logger.info("做多挂单,挂单价格为:" + btcBidLevel2BuildPrice + ",强平价格为:" + btcBidLevel2ForceCleanPrice);
                }
            }
            if (bidLevel3Price > 0) {
                btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("检测到买方大于" + btcBidDealThreshold3 + "小于" + btcBidDealThreshold2 + "张合约的大单.大单挂单价格为:" + askLevel3Price);
                if (btcBidLevel3HoldState) {
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("已经持仓不挂单.");
                } else {
                    btcBidLevel3BuildPrice = bidLevel3Price - btcOrderDiff;
                    btcBidLevel3ForceCleanPrice = bidLevel3Price + btcSaleDiff;
                    btcUsdFuturesTradeBigDealStrategyLevel3Logger.info("做多挂单,挂单价格为:" + btcBidLevel3BuildPrice + ",强平价格为:" + btcBidLevel3ForceCleanPrice);
                }
            }
        }

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

//    private static List<String> buildSpotTopicList() {
//        List<String> topicList = new ArrayList<>();
//        topicList.add("spot/candle60s:EOS-USDT");
//        topicList.add("spot/candle60s:BTC-USDT");
//        topicList.add("spot/candle60s:LTC-USDT");
//        topicList.add("spot/candle60s:BCH-USDT");
//        topicList.add("spot/candle60s:ETH-USDT");
//
//        topicList.add("spot/trade:EOS-USDT");
//        topicList.add("spot/trade:BTC-USDT");
//        topicList.add("spot/trade:LTC-USDT");
//        topicList.add("spot/trade:BCH-USDT");
//        topicList.add("spot/trade:ETH-USDT");
//
//        topicList.add("spot/ticker:EOS-USDT");
//        topicList.add("spot/ticker:BTC-USDT");
//        topicList.add("spot/ticker:LTC-USDT");
//        topicList.add("spot/ticker:BCH-USDT");
//        topicList.add("spot/ticker:ETH-USDT");
//
//        topicList.add("spot/depth5:EOS-USDT");
//        topicList.add("spot/depth5:BTC-USDT");
//        topicList.add("spot/depth5:LTC-USDT");
//        topicList.add("spot/depth5:BCH-USDT");
//        topicList.add("spot/depth5:ETH-USDT");
//        return topicList;
//    }

    private static List<String> buildFutureTopicList() {
        List<String> topicList = new ArrayList<>();

        topicList.add("futures/depth:EOS-USD-" + futurePaidTime);
        topicList.add("futures/depth:BTC-USD-" + futurePaidTime);
        topicList.add("futures/depth:ETH-USD-" + futurePaidTime);
        topicList.add("futures/depth:BCH-USD-" + futurePaidTime);

        topicList.add("futures/ticker:BTC-USD-" + futurePaidTime);
        topicList.add("futures/ticker:EOS-USD-" + futurePaidTime);
        topicList.add("futures/ticker:ETH-USD-" + futurePaidTime);
        topicList.add("futures/ticker:BCH-USD-" + futurePaidTime);

        return topicList;
    }

}
