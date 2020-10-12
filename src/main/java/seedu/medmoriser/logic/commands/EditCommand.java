package seedu.medmoriser.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.medmoriser.model.Model.PREDICATE_SHOW_ALL_QUESTIONSETS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.medmoriser.commons.core.Messages;
import seedu.medmoriser.commons.core.index.Index;
import seedu.medmoriser.commons.util.CollectionUtil;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.questionset.Answer;
import seedu.medmoriser.model.questionset.Email;
import seedu.medmoriser.model.questionset.Phone;
import seedu.medmoriser.model.questionset.Question;
import seedu.medmoriser.model.questionset.QuestionSet;
import seedu.medmoriser.model.tag.Tag;

/**
 * Edits the details of an existing QuestionSet in the question bank.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the questionSet identified "
            + "by the index number used in the displayed questionSet list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_QUESTION + "QUESTION] "
            + "[" + PREFIX_ANSWER + "ANSWER] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUESTION + "What causes pimples? "
            + PREFIX_ANSWER + "triggered by androgen hormones and, in some cases, genetics";

    public static final String MESSAGE_EDIT_QUESTIONSET_SUCCESS = "Edited questionSet: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_QUESTIONSET = "This questionSet already exists in the question bank.";

    private final Index index;
    private final EditQuestionSetDescriptor editQuestionSetDescriptor;

    /**
     * @param index of the questionSet in the filtered questionSet list to edit
     * @param editQuestionSetDescriptor details to edit the questionSet with
     */
    public EditCommand(Index index, EditQuestionSetDescriptor editQuestionSetDescriptor) {
        requireNonNull(index);
        requireNonNull(editQuestionSetDescriptor);

        this.index = index;
        this.editQuestionSetDescriptor = new EditQuestionSetDescriptor(editQuestionSetDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<QuestionSet> lastShownList = model.getFilteredQuestionSetList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTIONSET_DISPLAYED_INDEX);
        }

        QuestionSet questionSetToEdit = lastShownList.get(index.getZeroBased());
        QuestionSet editedQuestionSet = createEditedQuestionSet(questionSetToEdit, editQuestionSetDescriptor);

        if (!questionSetToEdit.isSameQuestionSet(editedQuestionSet) && model.hasQuestionSet(editedQuestionSet)) {
            throw new CommandException(MESSAGE_DUPLICATE_QUESTIONSET);
        }

        model.setQuestionSet(questionSetToEdit, editedQuestionSet);
        model.updateFilteredQuestionSetList(PREDICATE_SHOW_ALL_QUESTIONSETS);
        return new CommandResult(String.format(MESSAGE_EDIT_QUESTIONSET_SUCCESS, editedQuestionSet));
    }

    /**
     * Creates and returns a {@code QuestionSet} with the details of {@code questionSetToEdit}
     * edited with {@code editQuestionSetDescriptor}.
     */
    private static QuestionSet createEditedQuestionSet(QuestionSet questionSetToEdit,
                                                       EditQuestionSetDescriptor editQuestionSetDescriptor) {
        assert questionSetToEdit != null;

        Question updatedQuestion = editQuestionSetDescriptor.getQuestion().orElse(questionSetToEdit.getQuestion());
        Phone updatedPhone = editQuestionSetDescriptor.getPhone().orElse(questionSetToEdit.getPhone());
        Email updatedEmail = editQuestionSetDescriptor.getEmail().orElse(questionSetToEdit.getEmail());
        Answer updatedAnswer = editQuestionSetDescriptor.getAnswer().orElse(questionSetToEdit.getAnswer());
        Set<Tag> updatedTags = editQuestionSetDescriptor.getTags().orElse(questionSetToEdit.getTags());

        return new QuestionSet(updatedQuestion, updatedPhone, updatedEmail, updatedAnswer, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editQuestionSetDescriptor.equals(e.editQuestionSetDescriptor);
    }

    /**
     * Stores the details to edit the questionSet with. Each non-empty field value will replace the
     * corresponding field value of the questionSet.
     */
    public static class EditQuestionSetDescriptor {
        private Question question;
        private Phone phone;
        private Email email;
        private Answer answer;
        private Set<Tag> tags;

        public EditQuestionSetDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditQuestionSetDescriptor(EditQuestionSetDescriptor toCopy) {
            setQuestion(toCopy.question);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAnswer(toCopy.answer);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(question, phone, email, answer, tags);
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public Optional<Question> getQuestion() {
            return Optional.ofNullable(question);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAnswer(Answer answer) {
            this.answer = answer;
        }

        public Optional<Answer> getAnswer() {
            return Optional.ofNullable(answer);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditQuestionSetDescriptor)) {
                return false;
            }

            // state check
            EditQuestionSetDescriptor e = (EditQuestionSetDescriptor) other;

            return getQuestion().equals(e.getQuestion())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAnswer().equals(e.getAnswer())
                    && getTags().equals(e.getTags());
        }
    }
}
