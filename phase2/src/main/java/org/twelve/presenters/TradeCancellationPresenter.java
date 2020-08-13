package org.twelve.presenters;

import java.util.List;

public interface TradeCancellationPresenter{

    List<String> getAllTrades();

    void setAllTrades(List<String> trades);
}
