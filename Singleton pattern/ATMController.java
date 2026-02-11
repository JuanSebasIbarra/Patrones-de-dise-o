
class ATMController {
    private static ATMController instance;

    // Private constructor
    private ATMController() {
        System.out.println("ATM initialized.");
    }
    public static ATMController getInstance () {
        if (instance == null) {
            instance = new ATMController ();
        }
        return instance;
    }

    // Functionalities
    public void withdraw (double amount){
        System.out.println("Withdrawing: " + amount);
    }
    public void deposit (double amount){
        System.out.println("Depositing: " + amount);
    }
    public void checkBalance () {
        System.out.println("Checking balance...");
        }
}