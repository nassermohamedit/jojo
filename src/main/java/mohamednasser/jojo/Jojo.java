package mohamednasser.jojo;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Jojo extends JFrame {

    private static final int DEFAULT_HEIGHT = 1000;
    private static final int DEFAULT_WIDTH = 800;

    private final String workDir = "/home/nasser/.jojo_wd/";
    private final ByteCodeGenerator bcg;
    private final ByteCodeViewer bcv;
    private final JEditor jeditor;

    public Jojo() {
        super();
        prepareWorkDir();
        bcg = new ByteCodeGenerator(workDir);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        bcv = createByteCodeViewer();
        jeditor = createJEditor();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jeditor, bcv);
        splitPane.setDividerLocation(this.getSize().width/2);
        this.add(splitPane);
        this.addWindowListener(new JojoWindowListener());
        this.setVisible(true);
    }

    private ByteCodeViewer createByteCodeViewer() {
        ByteCodeViewer bcv = new ByteCodeViewer();
        bcv.getTextArea().setEditable(false);
        return bcv;
    }

    private JEditor createJEditor() {
        return new JEditor(
                code -> {
                    String result = bcg.getByteCode(code);
                    bcv.getTextArea().setText(result);
                }
        );
    }

    private void prepareWorkDir() {
        try {
            Path path = Paths.get(workDir);
            if (Files.exists(path)) {
                FileUtils.deleteDirectory(new File(workDir));
            }
            Files.createDirectory(path);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void cleanResources() {
        try {
            FileUtils.deleteDirectory(new File(workDir));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private class JojoWindowListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            cleanResources();
        }
    }
}