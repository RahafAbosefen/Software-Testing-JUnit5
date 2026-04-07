package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.UserService;
public class UserServiceSimpleTest {
	UserService U;
	
	@BeforeEach
	void setUp() throws Exception {
      U=new UserService();
	}
	
	@Test
	 @DisplayName("Valid email")
		void testValidEmail() {
			assertTrue(U.isValidEmail("ahmad@gmail.com"));
		}
	 
	 @Test
	 @DisplayName("Null email")
		void testNullEmail() {
		 assertFalse(U.isValidEmail(null));
		}
	 
	@ParameterizedTest
	@ValueSource(strings= {"ahmadgmail.com","ahmad@gmailcom","ahmadgmailcom"
	})
	@Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
	 @DisplayName("inValid email")
		void testInValidEmail(String email) {
			assertFalse(U.isValidEmail(email));
		}

	 @Test
	 @DisplayName("Correct username and password")
	 void testAuthantcate() {
		 assertTrue(U.authenticate("admin", "1234"));
	 }
	 
	 @ParameterizedTest
	 @CsvSource({
		 "ahmad , 190@4",
		 "admin , 190@4",
		 "ahmad , 1234"
		})
	 @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
	 @DisplayName("UnCorrect username and password")
	 void testwrongAuthantcate(String username, String password) {
		 assertFalse(U.authenticate(username, password));
	 }
	 
}
