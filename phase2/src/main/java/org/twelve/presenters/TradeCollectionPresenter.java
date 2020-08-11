package org.twelve.presenters;


import java.util.List;

public interface TradeCollectionPresenter {

    List<String> getAllUsers();

    void setAllUsers(List<String> allUsers);

    List<String> getAllTrades();

    void setAllTrades(List<String> allTrades);

    int getHourChosen();

    void setHourChosen(int i);

    int getMinuteChosen();

    void setMinuteChosen(int i);

    String getLocationChosen();

    void setLocationChosen(String location);

    String getDateChosen();

    void setDateChosen(String date);

    List<String> getRecentOneWays();

    void setRecentOneWays(List<String> recentOneWays);

    List<String> getRecentTwoWays();

    void setRecentTwoWays(List<String> recentTwoWays);

    String getSelectedUser();

    void setSelectedUser(String user);

    String getTradingPartner();

    void setTradingPartner(String tradingPartner);

    String getYourItems();

    void setYourItems(List<String> yourItems);

    String getTheirItems();

    void setTheirItems(List<String> theirItems);

    String getTopTradingPartners();

    void setTopTradingPartners(List<String> topTradingPartners);

    String getSelectedTrade();

    void setSelectedTrade(String selectedTrade);







}
