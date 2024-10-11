package io.jojo.editor;

import io.jojo.CodeChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static io.jojo.utils.TextUtils.indentation;
import static io.jojo.utils.TextUtils.lineOf;

/**
 * @author nasser
 */
public class JEditor extends JTextPane {

    private final static String INDENT = "    ";


    public JEditor(CodeChangeListener changeListener) {
        super();
        setFont(new Font("Monospaced", Font.PLAIN, 20));
        addKeyListener(new EditorKeyListener(this));
        getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        changeListener.codeChanged(getText());
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        changeListener.codeChanged(getText());
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        changeListener.codeChanged(getText());
                    }
                }
        );

        Action newLineAction = new NewLineAction(this);
        getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "breakAction");
        getActionMap().put("breakAction", newLineAction);

        getInputMap().put(KeyStroke.getKeyStroke("TAB"), "tab");
        getActionMap().put("tab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertText(INDENT);
            }
        });
    }

    private void insertText(String s) {
        try {
            getStyledDocument().insertString(getCaretPosition(), s, null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    static class NewLineAction extends AbstractAction {

        private final JEditor editor;

        private NewLineAction(JEditor editor) {
            this.editor = editor;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int cp = editor.getCaretPosition();
            String content = editor.getText();
            int line = lineOf(cp - 1, content);
            String str = "\n";
            if (cp > 0 && content.charAt(cp - 1) == '{') {
                str = "\n" + INDENT.repeat(indentation(line, content) + 1);
                if (cp < content.length() && content.charAt(cp) == '}') {
                    str += "\n" + INDENT.repeat(indentation(line, content));
                }
            }

            editor.insertText(str);
        }
    }



    static class EditorKeyListener implements KeyListener {

        private final JEditor editor;

        EditorKeyListener(JEditor editor) {
            this.editor = editor;
        }

        @Override
        public void keyTyped(KeyEvent e) {
            char k = e.getKeyChar();
            if (Character.isISOControl(k)) return;
            String str = switch (k) {
                case '{' -> "{}";
                case '(' -> "()";
                case '[' ->"[]";
                default -> k + "";
            };
            e.consume();
            editor.insertText(str);
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
