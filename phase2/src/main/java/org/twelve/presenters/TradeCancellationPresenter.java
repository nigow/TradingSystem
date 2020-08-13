package org.twelve.presenters;

import java.util.List;

public interface TradeCancellationPresenter{

    public List<String> getAllTrades();

    public void setAllTrades(List<String> trades);
}
