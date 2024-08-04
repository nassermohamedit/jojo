package mohamednasser.jojo;

import javax.swing.*;
import java.awt.*;

/**
 * @author nasser
 */
public class ByteCodeViewer extends JScrollPane {

    private final JTextPane textArea;

    public ByteCodeViewer() {
        super();
        textArea = new JTextPane();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        super.setViewportView(textArea);
    }

    public JTextPane getTextArea() {
        return textArea;
    }
}
