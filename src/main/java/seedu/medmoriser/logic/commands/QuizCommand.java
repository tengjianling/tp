package seedu.medmoriser.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;

import java.util.List;
import java.util.Random;

public class QuizCommand extends Command {

    public static final String COMMAND_WORD = "quiz";

    public static final String MESSAGE_USAGE = "Quiz time!";

    public static final String MESSAGE_SUCCESS = "Heres a question...";

    private final QuestionContainsKeywordsPredicate predicate;

    public QuizCommand(QuestionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public QAndA getRandomQuestion(List<QAndA> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredQAndAList(predicate);
        ObservableList<QAndA> filteredList = model.getFilteredQAndAList();
        QAndA question = getRandomQuestion(filteredList);
        model.updateFilteredQAndAList(x -> x.equals(question));
        System.out.println(question.getQuestion());

        return new CommandResult(MESSAGE_USAGE);
    }
}
