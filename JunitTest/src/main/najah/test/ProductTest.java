package main.najah.test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Product;

public class ProductTest {
    Product p;

	@BeforeEach
	void setUp() throws Exception {
		p = new Product("Ahmad",40);
	}
	
	@Disabled
	@Test
	void test() {
		fail("Not yet implemented");
	}
    
	@Test
	@DisplayName("Verify product name initialization")
	void ValidApplyName() {
		assertEquals("Ahmad",p.getName());	
	}
	

	@Test
	@DisplayName("Verify initial product price")
	void ValidApplyDiscount () {
		assertEquals(40,p.getPrice());
	}
	
    @Test
    @DisplayName("Handle negative price in constructor")
    void testNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Test", -10));
    }
    
    
	@Test
	@DisplayName("Calculate discount percentage correctly")
	void ValidDiscount() {
		p.applyDiscount(50.0);
		assertEquals(50.0,p.getDiscount());	
	}
	
	@Test
	@DisplayName("Reject negative discount values")
	void testNegativeDiscount() {
	    assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(-1));
	}
	
	@Test
	@DisplayName("Restrict discount to maximum 50%")
	void testHighDiscount() {
	    assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(55));
	}
	
	 @ParameterizedTest
	 @CsvSource({
	        "0,40",
	        "10,36",
	        "25,30",
	        "50,20"
	    })
	    @DisplayName("Calculate final price for various discount rates")
	 @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
	    void testMultipleValidDiscounts(double discount, double expectedPrice) {
	        p.applyDiscount(discount);
	        assertEquals(expectedPrice, p.getFinalPrice());
	    } 
}
