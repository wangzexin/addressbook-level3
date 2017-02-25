package seedu.addressbook.commands;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.addressbook.data.person.ReadOnlyPerson;

/**
 * @author yanxiaoxuan
 * Finds and lists all persons in address book whose name contains any of the argument keywords (tags).
 * Keyword matching is case sensitive.
 */
public class ViewByTagCommand extends Command {
    public static final String COMMAND_WORD = "viewbytag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows the non-private details of the person "
            + "identified by the tag(s) specified by user.\n\t"
            + "Parameters: TAG\n\t"
            + "Example: " + COMMAND_WORD + " friends colleage";

    public static final String MESSAGE_VIEW_PERSON_DETAILS = "Viewing person: %1$s";

    private final Set<String> keywords;

    public ViewByTagCommand(Set<String> keywords) {
        this.keywords = keywords;
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
     * Retrieve all persons in the address book whose tag lists contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInTags = new HashSet<>(person.getTags().getWordsInTags());
            if (!Collections.disjoint(wordsInTags, keywords)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }

}
