package org.twelve.presenters;


import javax.swing.text.html.ListView;
import java.util.List;

public interface TradeListPresenter {

    List<String> getAllTradeStatus();
    List<String> getStatsTypes();
    List<String> getTradesShown();
    List<String> getStatsShown();
    void setTradesShown(List<String> tradesShown);
    void setStatsShown(List<String> statsShown);

//    List<String> getAllUsers();
//
//    void setAllUsers(List<String> allUsers);
//
//    List<String> getAllTrades();
//
//    void setAllTrades(List<String> allTrades);
//
//    int getHourChosen();
//
//    void setHourChosen(int i);
//
//    int getMinuteChosen();
//
//    void setMinuteChosen(int i);
//
//    String getLocationChosen();
//
//    void setLocationChosen(String location);
//
//    String getDateChosen();
//
//    void setDateChosen(String date);
//
//    List<String> getRecentOneWays();
//
//    void setRecentOneWays(List<String> recentOneWays);
//
//    List<String> getRecentTwoWays();
//
//    void setRecentTwoWays(List<String> recentTwoWays);
//
//    String getSelectedUser();
//
//    void setSelectedUser(String user);
//
//    String getTradingPartners();
//
//    void setTradingPartners(String tradingPartners);
//
//    String getYourItems();
//
//    void setYourItems(List<String> yourItems);
//
//    String getTheirItems();
//
//    void setTheirItems(List<String> theirItems);
//
//    String getTopTradingPartners();
//
//    void setTopTradingPartners(List<String> topTradingPartners);
//
//    String getSelectedTrade();
//
//    void setSelectedTrade(String selectedTrade);

}
