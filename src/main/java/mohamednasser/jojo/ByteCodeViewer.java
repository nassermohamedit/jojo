package mohamednasser.jojo;

import javax.swing.*;
import java.awt.*;

/**
 * @author nasser
 */
public class ByteCodeViewer extends JScrollPane {

    private final JTextArea textArea;

    public ByteCodeViewer() {
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        super.setViewportView(textArea);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
