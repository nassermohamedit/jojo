package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.jojo.utils.TextUtils.indentation;
import static io.jojo.utils.TextUtils.lineOf;

/**
 * @author nasser
 */
public class TextUtilsTest {

    @Test
    void noIndentationAtFirstLineTest() {
        String content = "{\n\n}";
        int actual = indentation(0, content);
        int expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void singleLevelIndentationAfterOpeningBracketTest() {
        String content = "{\n\n}";
        int actual = indentation(1, content);
        int expected = 1;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void noIndentationAfterClosingBracketTest() {
        String content = "{\n\n}";
        int actual = indentation(2, content);
        int expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void nestedBracketsWithOneLevelIndentationTest() {
        String content = "{{\n\n}}";
        int actual = indentation(1, content);
        int expected = 1;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void nestedBracketsWithNoIndentationTest() {
        String content = "{{\n\n}}";
        int actual = indentation(2, content);
        int expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void complexMultiLineIndentationTest() {
        String content = """
                { 0 indent
                    1 indent
                    {{ 1 indent
                        2 indents
                    } 1 indent
                    } 1 indent
                } 0 indent
                """;
        Assertions.assertEquals(0, indentation(0, content));
        Assertions.assertEquals(1, indentation(1, content));
        Assertions.assertEquals(1, indentation(2, content));
        Assertions.assertEquals(2, indentation(3, content));
        Assertions.assertEquals(1, indentation(4, content));
        Assertions.assertEquals(1, indentation(5, content));
        Assertions.assertEquals(0, indentation(6, content));
    }

    @Test
    void lineOfTest() {
        String text = "012\n456\n";
        Assertions.assertEquals(0, lineOf(0, text));
        Assertions.assertEquals(0, lineOf(1, text));
        Assertions.assertEquals(0, lineOf(3, text));
        Assertions.assertEquals(1, lineOf(4, text));
        Assertions.assertEquals(1, lineOf(7, text));
    }
}
