package quoridor.graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Verifier extends InputVerifier implements ActionListener {

    private int MIN_TOTALWALLS = 2;
    private int MAX_TOTALWALLS = 20;

    private int MIN_WALLDIMENSION = 1;
    private int MAX_WALLDIMENSION = 4;

    private int MIN_BOARDDIMENSION = 4;
    private int MAX_BOARDDIMENSION = 11;
    private JTextField jtext;

    protected boolean checkField(JComponent input, boolean changeIt) {
        if (input ) {
            return checkAmountField(changeIt);
        } else if (input == rateField) {
            return checkRateField(changeIt);
        } else if (input == numPeriodsField) {
            return checkNumPeriodsField(changeIt);
        } else {
            return true; //shouldn't happen
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public boolean verify(JComponent input) {
            boolean isValid = false;
            String text = ((JTextField) input).getText();
            jtext = new JTextField(5);
            jtext.setText("Wrong input");

        if (text != null && text.matches("\\d+")) {
                int numRounds = Integer.parseInt(text);
                isValid = (numRounds > 0);
            }
            if (!isValid) {

                WindowUtil.messageError(
                        "'Number of Rounds' must be an integer value > 0.\n" + "Default value is 10.");
                jtext.setText("");
            }
            return isValid;

    };


}
