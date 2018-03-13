package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DescriptionCommand;
import seedu.address.model.person.Description;

public class DescriptionCommandParserTest {
    private DescriptionCommandParser parser = new DescriptionCommandParser();
    private final String nonEmptyDescription = "Some Description.";

    @Test
    public void parse_indexSpecified_success() throws Exception {
        // have description
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DESCRIPTION.toString() + nonEmptyDescription;
        DescriptionCommand expectedCommand = new DescriptionCommand(INDEX_FIRST_PERSON, new Description(nonEmptyDescription));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no description
        userInput = targetIndex.getOneBased() + " " + PREFIX_DESCRIPTION.toString();
        expectedCommand = new DescriptionCommand(INDEX_FIRST_PERSON, new Description(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DescriptionCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, DescriptionCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, DescriptionCommand.COMMAND_WORD + " " + nonEmptyDescription, expectedMessage);
    }
}
