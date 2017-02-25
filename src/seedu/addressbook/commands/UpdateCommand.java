package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.*;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class UpdateCommand extends Command {
	
	public enum UpdateWhich {
		EMAIL, PHONE
	};

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Update the email/phone of one specified person "
            + "and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [EMAIL/PHONE] [NEW EMAIL or PHONE]...\n\t"
            + "Example: " + COMMAND_WORD + " alice bob charlie" + " EMAIL" + " blablabla@u.nus.edu";

    private final Set<String> keywords;

	private UpdateWhich updateWhich;

	private String updateString;

    public UpdateCommand(Set<String> keywords, UpdateWhich updateWhich, String updateString) {
        this.keywords = keywords;
        this.updateWhich = updateWhich;
        this.updateString = updateString;
    }

    /**
     * Returns copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithNameContainingAnyKeyword(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }

}
