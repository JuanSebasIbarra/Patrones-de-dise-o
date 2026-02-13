// Main de ejercicio ATM



public class ATMApplication {

    public static void main(String[] args) {
        ATMApplication application = new ATMApplication();
        application.start();
    }

    private void start() {
        ATMController atmController = ATMController.getInstance();
        performTransactions(atmController);
        validateSingletonBehavior(atmController);
    }

    private void performTransactions(ATMController atmController) {
        atmController.deposit(500.00);
        atmController.withdraw(200.00);
        atmController.checkBalance();
    }

    private void validateSingletonBehavior(ATMController firstInstance) {
        ATMController secondInstance = ATMController.getInstance();

        if (firstInstance == secondInstance) {
            System.out.println("Singleton validation successful: Both references point to the same instance.");
        } else {
            System.out.println("Singleton validation failed: Different instances detected.");
        }
    }
}
