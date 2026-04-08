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
