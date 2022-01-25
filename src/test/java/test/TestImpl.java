package test;


import com.entity.RegistrationForm;
import com.entity.validator.UsernameValidator;
import org.junit.Assert;
import org.junit.Test;

public class TestImpl {

    @Test
    public void testUsername(){

        String username = "?><";

        Assert.assertFalse(UsernameValidator.isUserNameValid(username));
    }

    @Test
    public void testUsernameSecond(){
        String username = "Daniel";

        Assert.assertTrue(UsernameValidator.isUserNameValid(username));
    }

    @Test
    public void testUsernameNullPnt(){
        String username = null;

        Assert.assertFalse(UsernameValidator.isUserNameValid(username));
    }

    @Test
    public void testRegistrationForm(){

        String actual = Assert.assertThrows(IllegalStateException.class, () -> {
            new RegistrationForm("", "", "", "", "");
        }).getMessage();

        String expected = "Username or password cannot be empty";

        Assert.assertTrue(actual.contains(expected));
    }


}
