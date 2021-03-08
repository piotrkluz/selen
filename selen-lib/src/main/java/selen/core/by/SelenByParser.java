package selen.core.by;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;

@RequiredArgsConstructor
public class SelenByParser {
    /**
     * matches quotes, allow escape quote mark:
     *  - QUOTES1 -> 'some \'text\' "q" '
     *  - QUOTES2 -> "some 'text' \"q\" "
     */
    private final static String QUOTES1 = "'(?:[^']|(?<=\\\\)')*'";
    private final static String QUOTES2 = "\"(?:[^\"]|(?<=\\\\)\")*\"";

    /**
     * match filters at the end of string, require space before and no spaces inside:
     * /some/selector[2] [filter1:filter][3][filter2=lol]
     * will match:
     * [filter1:filter][3][filter2=lol]
     */
    private final static String ALL_FILTERS = " (\\[[^ ]*\\])$";

    /**
     * Decompose filter into parts:
     * [filter1:filter][3][filter2=lol]
     * will decompose to:
     * "filter1:filter", "3", "filter2=lol"
     */
    private final static String DECOMPOSE_FILTERS = "\\[([^\\]\\[]+)\\]";

    private final String input;

    public SelenBy parse() {
        //extractquotes
        //recreateQuotes(parseFilters(decomposeFilters(allFilters(clearspaces()))))
        //recreateQuotes(restOfSelector)

        throw new NotImplementedException("");
    }
}
