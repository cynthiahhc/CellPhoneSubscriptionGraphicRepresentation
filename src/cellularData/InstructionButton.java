package cellularData;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * A class creates an InstructionButton object implementing ActionListener.
 * @author Hsin Hsien (Cynthia) Chung, Zhongqiu Liu
 *
 */
public class InstructionButton extends JButton implements ActionListener {
    
    /**
     * The constructor of the class to create a button with text "Instruction" on it.
     */
    public InstructionButton() {
        super("Instruction");
        setPreferredSize(new Dimension(50,50));
        addActionListener(this);
    }
    
    /**
     * When the button is clicked, a dialog will pop out and show the instruction.
     */
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
                "TO SHOW A SINGLE COUNTRY DATA\n"
                + "- Click a country in the right panel.\n"
                + "- Click any point to show the detail of that point.\n"
                + "- The graph will update accordingly.\n\n"
                + "TO CALCULATE THE TOTAL SUBSCRIPTION OF A SINGLE COUNTRY\n"
                + "- Click a country in the right panel.\n"
                + "- Input the starting year in the first input box.\n"
                + "- Input the ending year in the second input box.\n"
                + "- Click the \"Calculate\" button.\n"
                + "- A message will pop out with the total number of subscriptions.\n\n"
                + "TO SHOW THE DATA OF ALL COUNTRIES\n"
                + "- Right click anywhere in the graph or the right panel.",
                "Instruction", JOptionPane.QUESTION_MESSAGE);
    }
}
