package mohamednasser.jojo;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Jojo {

    private static String workDir = "/home/nasser/.jojo_wd/";

    public static void run() throws IOException {

        Files.createDirectory(Paths.get(workDir));
        ByteCodeGenerator bcg = new ByteCodeGenerator(workDir);
        JFrame frame = new JFrame("JoJo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        ByteCodeViewer byteCodeViewer = new ByteCodeViewer();
        JEditor jeditor = new JEditor(
                code -> {
                    String result = bcg.getByteCode(code);
                    byteCodeViewer.getTextArea().setText(result);
                }
        );

        JScrollPane codeScrollPane = new JScrollPane(jeditor);
        JScrollPane bytecodeScrollPane = new JScrollPane(byteCodeViewer);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, codeScrollPane, bytecodeScrollPane);
        splitPane.setDividerLocation(500);
        frame.add(splitPane);

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    FileUtils.deleteDirectory(new File(workDir));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.setVisible(true);
    }
}