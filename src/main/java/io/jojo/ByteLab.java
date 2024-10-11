package io.jojo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author nasser
 */
public class ByteLab {

    static final Path BASE_DIR = Path.of(
            System.getProperty("user.home"),
            ".jojo",
            "labs"
    );

    private String name;

    private Path path;

    private ByteLab(String name) throws IOException {
        this.name = name;
        init(name);
    }

    public static ByteLab createLab(String name, Path dir) throws IOException {
        return new ByteLab(name);
    }

    private void init(String name) throws IOException {
        path = BASE_DIR.resolve(name);
        if (Files.exists(path)) {
            throw new RuntimeException("A lab with the name " + name + " already exist.");
        }
        Files.createDirectory(path);
        Files.createFile(path.resolve("snap"));
    }

    public static boolean exists(String name) {
        return Files.exists(BASE_DIR.resolve(name));
    }

    public JTree getTree() throws IOException {
        var root = new DefaultMutableTreeNode(name);
        return new JTree(root);
    }

    void buildTree(TreeNode root) {

    }
}
