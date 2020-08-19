package uk.co.compendiumdev.restlisticator.domain;

import org.junit.Assert;
import org.junit.Test;
import uk.co.compendiumdev.restlisticator.automating.ApiUser;

/**
 * Created by Alan on 23/08/2017.
 */
public class UserTest {

    @Test
    public void canCreateAnApiUSer(){
        ApiUser aUser = new ApiUser("bob", "dobbs");
        Assert.assertEquals("bob", aUser.getUsername());
        Assert.assertEquals("dobbs", aUser.getPassword());
    }
}
