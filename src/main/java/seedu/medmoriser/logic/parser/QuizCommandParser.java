package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.medmoriser.logic.commands.QuizCommand;
import seedu.medmoriser.logic.parser.exceptions.ParseException;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new QuizCommand object
 */
public class QuizCommandParser implements Parser<QuizCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the QuizCommand
     * and returns a QuizCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public QuizCommand parse(String args) throws ParseException {
        assert args != null;
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCommand.MESSAGE_USAGE));
        }

        if (args.contains("q/") && args.contains("t/") && args.contains("a/")
                || args.contains("q/") && args.contains("a/") || args.contains("q/") && args.contains("t/")
                || args.contains("t/") && args.contains("a/")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCommand.MESSAGE_USAGE));
        }

        if (!args.contains("q/")) {
            if (!args.contains("t/")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCommand.MESSAGE_USAGE));
            }
        }

        if (!containsMaximumOnce(args, PREFIX_QUESTION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCommand.MESSAGE_ONE_PREFIX));
        }
        if (!containsMaximumOnce(args, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCommand.MESSAGE_ONE_PREFIX));
        }

        String[] keywordsArray = trimmedArgs.split("/|, ");
        String[] excludeType = keywordsArray;

        //trim whitespaces
        keywordsArray = trimArg(keywordsArray);

        String findType = keywordsArray[0];
        if (keywordsArray.length > 1) {
            excludeType = Arrays.copyOfRange(keywordsArray, 1, keywordsArray.length);
        }

        switch (findType) {
        case "t":
            return new QuizCommand(new TagContainsKeywordsPredicate(Arrays.asList(excludeType)));
        case "q":
            return new QuizCommand(new QuestionContainsKeywordsPredicate(Arrays.asList(excludeType)));
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCommand.MESSAGE_USAGE));
        }
    }

    private String[] trimArg(String[] args) {
        String[] toReturn = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            toReturn[i] = args[i].trim();
        }
        return toReturn;
    }

    /**
     * Returns true if prefix only appears once in the string
     * @param s The string to compare with.
     * @param prefix The prefix to compare with.
     * @return a boolean to indicate if prefix only appears in s once.
     */
    private boolean containsMaximumOnce(String s, Prefix prefix) {
        String prefixString = prefix.toString();
        int i = s.indexOf(prefixString);
        return i == s.lastIndexOf(prefixString);
    }
}
