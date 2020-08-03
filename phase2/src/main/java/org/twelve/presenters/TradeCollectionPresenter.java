package org.twelve.presenters;

import org.twelve.entities.Account;

import java.util.List;

public interface TradeCollectionPresenter {
    List<String> getAllTradesOfAccount(int accountID);
}
