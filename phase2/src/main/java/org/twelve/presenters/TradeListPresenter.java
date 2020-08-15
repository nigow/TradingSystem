package org.twelve.presenters;


import java.util.List;

public interface TradeListPresenter {

    List<String> getAllTradeStatus();
    List<String> getStatsTypes();
    List<String> getTradesShown();
    List<String> getStatsShown();
    void setTradesShown(List<String> tradesShown);
    void setStatsShown(List<String> statsShown);

}
