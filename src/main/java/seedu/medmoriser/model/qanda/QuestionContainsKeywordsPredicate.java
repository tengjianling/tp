package seedu.medmoriser.model.qanda;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Tests that a {@code QAndA}'s {@code Question} matches any of the keywords given.
 */
public class QuestionContainsKeywordsPredicate implements Predicate<QAndA> {
    private final List<String> keywords;

    public QuestionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(QAndA qAndA) {
        return keywords.stream()
                .anyMatch(keyword -> qAndA.getQuestion().question.toLowerCase()
                        .matches((".*\\b" + Pattern.quote(keyword) + "\\b.*".toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((QuestionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
