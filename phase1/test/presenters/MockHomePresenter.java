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
    public String logInUsername() {
        return "TestUsername";
    }

    @Override
    public String logInPassword() {
        return "TestPassword";
    }

    @Override
    public String newAccountUsername() {
        return "testUsername";
    }
    @Override
    public String newAccountPassword() {
        return "testPassword";
    }

    @Override
    public void invalidInput() {
        // pass
    }

    @Override
    public void showMessage(String message) {
        // pass
    }
}
