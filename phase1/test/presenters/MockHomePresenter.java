package presenters;

import java.util.ArrayList;
import java.util.List;

public class MockHomePresenter implements HomePresenter {
    private String[] actions;
    private int counter;

    public MockHomePresenter() {
        // Attempting to login with an invalid account
        actions = new String[]{"1", "2"};

    }

    @Override
    public String displayHomeOptions(List<String> homeOptions) {
        return actions[counter++];
    }

    @Override
    public String[] logIn() {
        String[] login = new String[2];
        login[0] = "testUsername";
        login[1] = "testPassword";
        return login;
    }

    @Override
    public String[] newAccount() {
        String[] newAccount = new String[2];
        newAccount[0] = "testUsername";
        newAccount[1] = "testPassword";
        return newAccount;
    }

    @Override
    public void invalidInput() {
        // pass
    }
}