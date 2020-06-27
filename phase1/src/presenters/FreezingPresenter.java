package presenters;

/**
 * interface used by administrator to freeze and unfreeze account
 * @author Catherine
 */
interface FreezingPresenter {

    public void displayPossibleFreeze() {
        /**
         * displays list of users that should be frozen
         */
    }

    public void displayPossibleUnfreeze() {
        /**
         * displays list of users that requests to be unfrozen
         */
    }

    public int[] freeze() {
        /**
         * returns indexes of users to freeze
         */
    }

    public int[] unfreeze() {
        /**
         * returns indexes of users to unfreeze
         */
    }

    public void returnToMenu() {
        /**
         * returns user to main menu
         */
    }
}