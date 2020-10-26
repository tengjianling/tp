package seedu.medmoriser.logic.commands;

import seedu.medmoriser.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model) {
        for (int i = 0; i < model.getFilteredQAndAList().size(); i++) {
            model.getFilteredQAndAList().get(i).setNotQuiz();
        }
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
