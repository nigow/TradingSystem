package presenters;

import java.util.List;

public class MockMenuPresenter implements MenuPresenter {
    private String[] options;
    private int counter;

    public MockMenuPresenter() {
         options = new String[]{"1", "2", "3"};
    }

    @Override
    public String displayMenu(List<String> menuOptions) {
        return options[counter++];
    }

    @Override
    public void invalidInput() {
        //pass
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}
