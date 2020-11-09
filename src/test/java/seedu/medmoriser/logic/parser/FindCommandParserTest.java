package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.logic.commands.FindCommand;
import seedu.medmoriser.model.qanda.AnswerContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.QAndAContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsQAndAContainsKeywordsPredicate_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new QAndAContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice, Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice, \n Bob ", expectedFindCommand);
    }

    @Test
    public void parse_validArgsAnswerContainsKeywordsPredicate_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new AnswerContainsKeywordsPredicate(Arrays.asList("Chronic Diseases", "Immune")));
        assertParseSuccess(parser, "a/Chronic Diseases, Immune", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "a/ \n Chronic Diseases, \n \t Immune  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsQuestionContainsKeywordsPredicate_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new QuestionContainsKeywordsPredicate(Arrays.asList("Diabetic Condition",
                        "glucose level")));
        assertParseSuccess(parser, "q/Diabetic Condition, glucose level", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "q/ Diabetic Condition ,  glucose level  ", expectedFindCommand);
    }

    @Test
    public void parse_validArgsTagContainsKeywordsPredicate_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("Immunology", "Chronic Diseases")));
        assertParseSuccess(parser, "t/Immunology, Chronic Diseases", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "t/ \n Immunology, \n \t Chronic Diseases  \t", expectedFindCommand);
    }

    @Test
    public void parse_emptyArgsTagContainsKeywordsPredicate_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("t")));
        assertParseSuccess(parser, "t/", expectedFindCommand);

        // multiple whitespaces after t/
        assertParseSuccess(parser, "t/  ", expectedFindCommand);
    }

    @Test
    public void parse_multipleDifferentArgs_throwsParseException() {
        assertParseFailure(parser, "q/heart a/blood t/body",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleSameArgs_throwsParseException() {
        // multiple q/
        assertParseFailure(parser, "q/heart q/blood",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_ONE_PREFIX));

        // multiple a/
        assertParseFailure(parser, "a/pump blood a/breathe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_ONE_PREFIX));

        // multiple t/
        assertParseFailure(parser, "t/immune system t/cardiovascular t/skeleton",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_ONE_PREFIX));
    }
}
