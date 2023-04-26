package epf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epf.rentmanager.modeles.Client;
import org.junit.Test;

import java.time.LocalDate;

public class ClientTest {

    @Test

    public void isLegal_should_return_true_when_age_is_over_18() {

        // Given

        Client client = new Client("John", "Doe", "john.doe@ensta.fr", LocalDate.parse("2001-12-12"));

        // Then

        assertTrue(Client.isLegal(client));

    }
    @Test

    public void isLegal_should_return_false_when_age_is_under_18() {

        // Given

        Client client = new Client("John", "Doe", "john.doe@ensta.fr", LocalDate.parse("2011-12-12"));

        // Then

        assertFalse(Client.isLegal(client));

    }
}
