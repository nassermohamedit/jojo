package io.jojo.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.spi.ToolProvider;

/**
 * @author nasser
 */
public class ByteCodeGen {

    private final ToolProvider javac;
    private final ToolProvider javap;
    public final String wd;
    private final String className;

    public ByteCodeGen(String workDir) {
        wd = workDir;
        className = "Main";
        javac = ToolProvider.findFirst("javac").orElseThrow();
        javap = ToolProvider.findFirst("javap").orElseThrow();

    }

    public String getByteCode(String sourceCode) {
        try {
            compile(sourceCode);
            StringWriter out = new StringWriter();
            PrintWriter stdout = new PrintWriter(out);
            StringWriter err = new StringWriter();
            PrintWriter stderr = new PrintWriter(err);
            javap.run(stdout, stderr, "-c", "-l", wd + "Main.class");
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void compile(String sourceCode) throws IOException {
        Path source = Paths.get(wd + className + ".java");
        Files.write(source, sourceCode.getBytes());

        StringWriter out = new StringWriter();
        PrintWriter stdout = new PrintWriter(out);
        StringWriter err = new StringWriter();
        PrintWriter stderr = new PrintWriter(err);

        javac.run(stdout, stderr, "-d", wd, wd + "Main.java");
    }
}
