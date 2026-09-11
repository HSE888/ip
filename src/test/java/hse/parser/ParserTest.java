package hse.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hse.command.FindDateCommand;
import hse.command.SortCommand;

class ParserTest {
    @Test
    void parse_findDateCommand_createsFindDateCommand() {
        assertInstanceOf(FindDateCommand.class, Parser.parse("finddate 3/9/2026"));
    }

    @Test
    void parse_sort_createsSortCommand() {
        assertInstanceOf(SortCommand.class, Parser.parse("sort"));
    }

    @Test
    void getIndex_firstAndLastTask_returnsZeroBasedIndexes() {
        assertEquals(0, Parser.getIndex("mark 1", 3));
        assertEquals(2, Parser.getIndex("delete 3", 3));
    }

    @Test
    void getIndex_zeroOrPastLastTask_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> Parser.getIndex("mark 0", 3));
        assertThrows(IndexOutOfBoundsException.class, () -> Parser.getIndex("delete 4", 3));
    }

    @Test
    void getIndex_nonNumericTaskNumber_throwsNumberFormatException() {
        assertThrows(NumberFormatException.class, () -> Parser.getIndex("mark one", 3));
    }
}
