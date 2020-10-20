package seedu.medmoriser.logic.parser;

import seedu.medmoriser.logic.commands.QuizCommand;
import seedu.medmoriser.logic.parser.exceptions.ParseException;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class QuizCommandParser {

    public QuizCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new QuizCommand(new QuestionContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
