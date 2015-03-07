package pl.openkp.business.uzytkownicy.control;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Assert;
import org.junit.Test;

public class PasswordEncryptionServiceTest {

    @Test
    public void szyfrowanie() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "alamakota";

        PasswordEncryptionService passwordEncryptionService = new PasswordEncryptionService();
        byte[] salt = passwordEncryptionService.generateSalt();
        byte[] encryptedPassword = passwordEncryptionService.getEncryptedPassword(password, salt);

        Assert.assertTrue(passwordEncryptionService.authenticate(password, encryptedPassword, salt));

        String newPassword = "test";
        encryptedPassword = passwordEncryptionService.getEncryptedPassword(newPassword, salt);
        Assert.assertTrue(passwordEncryptionService.authenticate(newPassword, encryptedPassword, salt));
    }
}
