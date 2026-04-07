package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import main.najah.code.RecipeException;

@DisplayName("Tests RecipeBook")
@Execution(value = ExecutionMode.CONCURRENT)
public class TestRecipeBook {
    RecipeBook RB;
    Recipe R;

    @BeforeAll
    static void initAll() {
        System.out.println("--- Starting RecipeBook Logic Tests ---");
    }

    @BeforeEach
    void setUp() throws Exception {
        RB = new RecipeBook();
        R = new Recipe();
        R.setName("Mocha");
        R.setPrice("20");
        R.setAmtCoffee("3");
        R.setAmtMilk("1");
        R.setAmtSugar("1");
        R.setAmtChocolate("5");
        System.out.println("Setting up a new RecipeBook test case...");
    }

    @Test
    @DisplayName("Verify valid recipe fields initialization")
		void testValidPrices() {
		    assertEquals("Mocha",R.getName());
		    assertEquals(20,R.getPrice());
		    assertEquals(3,R.getAmtCoffee());
		    assertEquals(1,R.getAmtMilk());
		    assertEquals(1,R.getAmtSugar());
		    assertEquals(5,R.getAmtChocolate());
		}
    
    @ParameterizedTest
	@ValueSource(strings = {"abc", "-1"," "})
	@DisplayName("Reject invalid recipe numeric inputs")	
	@Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
	   void testInvalidPrices(String invalidPrice) {
			assertAll(
		   ()-> assertThrows(RecipeException.class, () -> R.setPrice(invalidPrice)),
		   ()-> assertThrows(RecipeException.class, () -> R.setAmtCoffee(invalidPrice)),
		   ()-> assertThrows(RecipeException.class, () -> R.setAmtMilk(invalidPrice)),
		   ()-> assertThrows(RecipeException.class, () -> R.setAmtSugar(invalidPrice)),
		   ()-> assertThrows(RecipeException.class, () -> R.setAmtChocolate(invalidPrice))
		    );
		}
   
    @Test
    @DisplayName("Verify all recipes are retrieved correctly")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS) 
    void testGetRecipes() {
        Recipe[] expected = new Recipe[4];
        for (int i = 0; i < 4; i++) {
            Recipe temp = new Recipe();
            temp.setName("Recipe " + i);
            RB.addRecipe(temp);
            expected[i] = temp;
        }

        assertArrayEquals(expected, RB.getRecipes());
    } 
   
    @Test
    @DisplayName("Confirm successful addition of new recipe")
    void testAdd() {
        assertTrue(RB.addRecipe(R));      
    }

    @Test
    @DisplayName("Prevent adding duplicate recipe names")
    void testDuplicateRecipe() {
        RB.addRecipe(R);
        assertFalse(RB.addRecipe(R));
    }
    
    @Test
    @DisplayName("Enforce maximum limit of four recipes")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void testAddBeyondLimit() {
        for (int i = 0; i < 4; i++) {
            Recipe temp = new Recipe();
            temp.setName("Recipe " + i);
            RB.addRecipe(temp);
        }
        Recipe extraRecipe = new Recipe();
        extraRecipe.setName("Extra");
        assertFalse(RB.addRecipe(extraRecipe));
    }

    @Test
    @DisplayName("Handle recipe deletion and verify return value")
    void testDeleteRecipe() {
        RB.addRecipe(R);
        assertEquals("Mocha", RB.deleteRecipe(0));
        }
    
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    @DisplayName("Verify deletion returns null for empty slots")
    void testDeleteEmptySlots(int slot) {
        assertNull(RB.deleteRecipe(slot), "Empty slot " + slot + " should return null on delete");
    }
    
    @Disabled("Bug identified: deleteRecipe replaces recipe with new Recipe instead of null. Fix: Set recipeArray[recipeToDelete] = null to truly delete it.")
    @Test
    @DisplayName("Bug: deleteRecipe should remove the recipe completely")
    void testDeleteBug() {
        RB.addRecipe(R); 
        RB.deleteRecipe(0);
        assertNull(RB.getRecipes()[0], "Recipe slot should be null after deletion");
    }

    @Test
    @DisplayName("Verify edit operation returns original name")
    void testEditRecipe() {
        RB.addRecipe(R);
        Recipe newR = new Recipe();
        newR.setName("Latte");
        assertEquals("Mocha", RB.editRecipe(0, newR));
    }   

	@Test
    @DisplayName("Return null when editing empty recipe slot")
	void testEditNullRecipe(){
		RB.addRecipe(R);
		Recipe newR= new Recipe();
		newR.setName("Lattee");
		assertNull(RB.editRecipe(1, newR));
	} 
	
	@Disabled("Bug identified: Logic does not persist the updated recipe object correctly. Fix: Remove newRecipe.setName(\"\") any line that modifies newRecipe's name inside editRecipe method")
    @Test
    @DisplayName("Bug: New recipe object should persist after edit")
    void testEditBug() {
        RB.addRecipe(R);
        Recipe newR = new Recipe();
        newR.setName("Latte");
        RB.editRecipe(0, newR);
        assertEquals("Latte", RB.getRecipes()[0].getName());
    }

    @AfterEach
    void tearDown() {
    	R=null;
        RB = null;
        System.out.println("RecipeBook Clean up complete.");
    }
    
    
    @AfterAll
    static void tearDownAll() {
    System.out.println("--- All Tests RecipeBook Completed Successfully ---");
    }
}
