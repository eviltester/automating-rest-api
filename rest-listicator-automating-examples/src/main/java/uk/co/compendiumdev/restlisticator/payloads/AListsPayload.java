package uk.co.compendiumdev.restlisticator.payloads;

import java.util.List;

/**
 * An example simple payload with public fields
 * This is vulnerable to payload changes i.e. the tests need to change if the format of the message changes
 * this was refactored to ListPayload and you should use that instead.
 */
public class AListsPayload {
    public List<AListPayload> lists;

    public List<AListPayload> getLists() {
        return lists;
    }
}
