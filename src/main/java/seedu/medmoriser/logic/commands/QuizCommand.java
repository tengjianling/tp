package seedu.medmoriser.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.medmoriser.commons.core.Messages;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.TagContainsKeywordsPredicate;

/**
 * Starts a new Quiz for the user.
 */
public class QuizCommand extends Command {

    public static final String COMMAND_WORD = "quiz";

    public static final String MESSAGE_SUCCESS = "Quiz time! Here's a question...";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Quizzes the user on a question based on the keywords"
            + " provided by the user. Quiz by tag or keyword in question. \n"
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION_KEYWORD [MORE_KEYWORDS]... " + "OR "
            + PREFIX_TAG + "TAG [MORE_TAGS]...  \n"
            + "Example: " + COMMAND_WORD + " t/Human Anatomy, Nervous System";

    public static final String MESSAGE_NO_QUESTION_WITH_KEYWORD = "No question with this tag/keyword";

    public static final String MESSAGE_ONE_PREFIX = "There are multiple prefixes present. Ensure that q/ is before the"
            + "question keyword or t/ is before the tag keyword";

    private static boolean isQuiz = false;

    private static List<QAndA> currentList = new ArrayList<>();

    private final Predicate<QAndA> predicate;

    public QuizCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public QuizCommand(QuestionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public static QAndA getRandomQuestion(List<QAndA> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public static boolean getIsQuiz() {
        return isQuiz;
    }

    public static void setIsQuiz(boolean ongoingQuiz, Model model) {
        isQuiz = ongoingQuiz;
        if (!isQuiz) {
            for (int i = 0; i < model.getFilteredQAndAList().size(); i++) {
                model.getFilteredQAndAList().get(i).setNotQuiz();
            }
        } else {
            model.getFilteredQAndAList().get(0).setAsQuiz();
        }
    }

    public static List<QAndA> getCurrentList() {
        return currentList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (getIsQuiz()) {
            throw new CommandException(Messages.MESSAGE_ONGOING_QUIZ);
        } else {
            model.updateFilteredQAndAList(predicate);
            ObservableList<QAndA> filteredList = model.getFilteredQAndAList();

            for (QAndA q : filteredList) {
                currentList.add(q);
            }

            if (filteredList.size() > 0) {
                QAndA question = getRandomQuestion(currentList);

                currentList.remove(question);

                model.updateFilteredQAndAList(x -> x.equals(question));
                setIsQuiz(true, model);

                return new CommandResult(MESSAGE_SUCCESS);
            } else {
                throw new CommandException(MESSAGE_NO_QUESTION_WITH_KEYWORD);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuizCommand // instanceof handles nulls
                && predicate.equals(((QuizCommand) other).predicate)) // state check
                && (isQuiz == ((QuizCommand) other).isQuiz);
    }
}
