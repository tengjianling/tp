package seedu.medmoriser.model;

import static java.util.Objects.requireNonNull;
import static seedu.medmoriser.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.medmoriser.commons.core.GuiSettings;
import seedu.medmoriser.commons.core.LogsCenter;
import seedu.medmoriser.model.questionset.QuestionSet;

/**
 * Represents the in-memory model of the medmoriser data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Medmoriser medmoriser;
    private final UserPrefs userPrefs;
    private final FilteredList<QuestionSet> filteredQuestionSets;

    /**
     * Initializes a ModelManager with the given medmoriser and userPrefs.
     */
    public ModelManager(ReadOnlyMedmoriser medmoriser, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(medmoriser, userPrefs);

        logger.fine("Initializing with medmoriser: " + medmoriser + " and user prefs " + userPrefs);

        this.medmoriser = new Medmoriser(medmoriser);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredQuestionSets = new FilteredList<>(this.medmoriser.getQuestionSetList());
    }

    public ModelManager() {
        this(new Medmoriser(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getMedmoriserFilePath() {
        return userPrefs.getMedmoriserFilePath();
    }

    @Override
    public void setMedmoriserFilePath(Path medmoriserFilePath) {
        requireNonNull(medmoriserFilePath);
        userPrefs.setMedmoriserFilePath(medmoriserFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setMedmoriser(ReadOnlyMedmoriser medmoriser) {
        this.medmoriser.resetData(medmoriser);
    }

    @Override
    public ReadOnlyMedmoriser getMedmoriser() {
        return medmoriser;
    }

    @Override
    public boolean hasQuestionSet(QuestionSet questionSet) {
        requireNonNull(questionSet);
        return medmoriser.hasQuestionSet(questionSet);
    }

    @Override
    public void deleteQuestionSet(QuestionSet target) {
        medmoriser.removeQuestionSet(target);
    }

    @Override
    public void addQuestionSet(QuestionSet questionSet) {
        medmoriser.addQuestionSet(questionSet);
        updateFilteredQuestionSetList(PREDICATE_SHOW_ALL_QUESTIONSETS);
    }

    @Override
    public void setQuestionSet(QuestionSet target, QuestionSet editedQuestionSet) {
        requireAllNonNull(target, editedQuestionSet);

        medmoriser.setQuestionSet(target, editedQuestionSet);
    }

    //=========== Filtered QuestionSet List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code QuestionSet} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<QuestionSet> getFilteredQuestionSetList() {
        return filteredQuestionSets;
    }

    @Override
    public void updateFilteredQuestionSetList(Predicate<QuestionSet> predicate) {
        requireNonNull(predicate);
        filteredQuestionSets.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return medmoriser.equals(other.medmoriser)
                && userPrefs.equals(other.userPrefs)
                && filteredQuestionSets.equals(other.filteredQuestionSets);
    }

}