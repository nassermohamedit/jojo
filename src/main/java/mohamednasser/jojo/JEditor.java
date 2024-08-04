package mohamednasser.jojo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * @author nasser
 */
public class JEditor extends JScrollPane {
    private final JTextArea textArea;

    public JEditor(CodeChangeListener changeListener) {
        super();
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        textArea.setText("// Write Java code here..\n\n");
        textArea.getDocument().addDocumentListener(null);
        textArea.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        changeListener.codeChanged(textArea.getText());
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        changeListener.codeChanged(textArea.getText());
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        changeListener.codeChanged(textArea.getText());
                    }
                }
        );
        super.setViewportView(textArea);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
