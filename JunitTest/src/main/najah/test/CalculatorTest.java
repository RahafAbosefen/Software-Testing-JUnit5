package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Calculator;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Calculator Tests")
public class CalculatorTest {

  Calculator calc;
  
  @BeforeAll
  static void initAll() {
      System.out.println("--- Calculator test started ---");
  }


	@BeforeEach
	void setUp() throws Exception {
		calc= new Calculator();
		System.out.println("Setup: Calculator ready for next operation.");
	}

	@Test
	@Order(3)
	@DisplayName("Addition with valid numbers")
	void testAddValid() {
	    assertEquals(12,calc.add(5,3,4));
	}
	
	@Test
	@Order(8)
	@Disabled("This test fails intentionally. Fix by changing expected result to 4")
	@DisplayName("Intentional failing test")
	void testFailingExample() {
	    assertEquals(5, calc.add(2, 2)); 
	}

	@ParameterizedTest
	@CsvSource({
	    "1,1,2",
	    "-1,1,0",
	    "0,1,1",
	    "2,1,3"
	})
	@Order(6)
	@DisplayName("Addition with multiple inputs")
	@Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
	void testAdd(int x, int y, int result) {
	    assertEquals(result, calc.add(x, y));
	}
	
	@Test
	@Order(5)
	@DisplayName("Division with valid numbers")
	void testDivideValid() {
	    assertEquals(2, calc.divide(6,3));
	}
	
	@Test
	@Order(2)
	@DisplayName("Division with invalid numbers")
	void testDivisionByZero() {
	    assertThrows(ArithmeticException.class, () -> calc.divide(5, 0));
	}
	
	@Test
	@Order(4)
	@DisplayName("Factorial of zero")
	void testFactorialZero() {
		assertEquals(1, calc.factorial(0));	
		 }
	

	@Test
	@Order(7)
	@DisplayName("Factorial with negative number")
	void testFactorialNegative() {
		assertThrows(IllegalArgumentException.class,()->calc.factorial(-1));
	}
	
	
	@Test
	@Order(1)
	@DisplayName("Factorial with positive number")
	@Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
	void testFactorialPositive() {
	    assertEquals(6, calc.factorial(3));
	}
	

    @AfterEach
    void tearDown() {
        System.out.println("Cleanup: Calculator Operation finished.");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("--- Calculator all tests finished ---");
    }
	
}
