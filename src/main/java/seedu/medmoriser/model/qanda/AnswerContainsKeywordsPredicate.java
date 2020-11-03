package seedu.medmoriser.model.qanda;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Tests that a {@code QAndA}'s {@code Answer} matches any of the keywords given.
 */
public class AnswerContainsKeywordsPredicate implements Predicate<QAndA> {
    private final List<String> keywords;

    public AnswerContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(QAndA qAndA) {
        return keywords.stream()
                .anyMatch(keyword -> qAndA.getAnswer().answer.toLowerCase()
                        .matches((".*\\b" + Pattern.quote(keyword) + "\\b.*".toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnswerContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AnswerContainsKeywordsPredicate) other).keywords)); // state check
    }

}
