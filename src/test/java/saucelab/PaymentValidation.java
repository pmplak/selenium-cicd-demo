package saucelab;

import java.util.logging.Logger;

public class PaymentValidation {

	private static final Logger LOGGER =
            Logger.getLogger(PaymentValidation.class.getName());

    public static void main(String[] args) {

        printTrainingMessages();

    }

    private static void printTrainingMessages() {

        LOGGER.info("Git Core Learning");
        LOGGER.info("Adding a Branch divergence");
        LOGGER.info("Main branch after feature creation");
        LOGGER.info("Learning Git Rebase");

    }

}