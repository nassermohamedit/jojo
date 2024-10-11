package io.jojo.utils;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author nasser
 */
public class TextUtils {


    /**
     * Computes the number of indentations at a line specified by index n, based on curly brackets.
     * @param n the 0-based index of the line at which to calculate the indentation.
     * @param content a String object representing the text content.
     * @return the number of indentation at line n
     */
    public static int indentation(int n, String content) {
        int indent = 0;
        Deque<Integer> stack = new LinkedList<>();
        Iterator<String> lines = content.lines()
                .limit(n + 1)
                .iterator();
        for (int i = 0; i < n && lines.hasNext(); ++i) {
            String line = lines.next();
            int count = 0;
            for (int j = 0; j < line.length(); ++j) {
                char c = line.charAt(j);
                if (c == '{') count++;
                else if (c == '}') count--;
            }
            if (count > 0) {
                indent++;
                for (int k = 0; k < count; ++k) {
                    stack.addFirst(indent);
                }
            } else if (count < 0) {
                count = -count;
                for (int k = 0; k < count; ++k) {
                    stack.pop();
                }
            }
        }
        if (stack.isEmpty()) return 0;
        String line = lines.next().stripLeading();
        indent = stack.getFirst();
        return (!line.isEmpty() && line.charAt(0) == '}') ? indent - 1 : indent;
    }

    public static int lineOf(int pos, String content) {
        if (pos > content.length() - 1)
            throw new IndexOutOfBoundsException();
        int lineNumber = 0;
        for (int i = 0; i < pos; ++i) {
            if (content.charAt(i) == '\n') lineNumber++;
        }
        return lineNumber;
    }


    public static boolean isWhiteSpace(char c) {
        return c == ' ' || c == '\t' || c == '\n';
    }

    public static boolean isWhiteSpace(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!isWhiteSpace(s.charAt(i)))
                return false;
        }
        return true;
    }
}
