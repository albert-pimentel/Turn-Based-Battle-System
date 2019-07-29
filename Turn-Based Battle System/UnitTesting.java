/*************************************************************************
 *  Compilation:  javac UnitTesting.java
 *  Execution:    java UnitTesting
 *
 * Contains various JUnit tests pertaining to hero's stats and functionalities, 
 * as well as the functionality of the battle system itself.
 * 
 * Select Test to begin.
 * 
 *  Version 1.0
 *************************************************************************/
import static org.junit.Assert.*;
import org.junit.*;  

public class UnitTesting {
  Hero hero0;
  Hero hero1;
  Hero hero2;
  Hero hero3;
  
  Hero hero4;
  Hero hero5;
  Hero hero6;
  Hero hero7;
  
  Hero[] arr0;
  Hero[] arr1;
  Hero[] arr2;
  Hero[] arr3;
  
  Party party0;
  Party party1;
  
  Battle battle0;
  
  @Before
  public void setup() {
    hero0 = new Hero(95, 45, 85, 25, 10, 30, 25, "knight.png", "Hero 0");
    hero1 = new Hero(65, 10, 45, 65, 90, 70, 55, "mage.png", "death.png", "Hero 1");
    hero2 = new Hero(55, 50, 60, 45, 25, 60, 80, "thief.png", "Hero 2");
    hero3 = new Hero(55, 70, 30, 60, 15, 30, 95, "assassin.png", "Hero 3");
    
    hero4 = new Hero(100, 80, 80, 30, 80, 70, 80, "skeleton.png", "Hero 4");
    hero5 = new Hero(85, 85, 60, 30, 60, 60, 25, "zombie.png", "Hero 5");
    hero6 = new Hero(90, 70, 60, 65, 85, 75, 60, "fireelemental.png", "Hero 6");
    hero7 = new Hero(85, 65, 50, 70, 75, 65, 30, "ghost.png", "Hero 7");
    
    arr0 = new Hero[4];
    arr1 = new Hero[4];
    arr2 = new Hero[3];
    arr3 = new Hero[5];
    
    arr0[0] = hero0;
    arr0[1] = hero1; 
    arr0[2] = hero2; 
    arr0[3] = hero3; 
    
    arr1[0] = hero4; 
    arr1[1] = hero5; 
    arr1[2] = hero6;
    arr1[3] = hero7; 
    
    arr2[0] = hero0;
    arr2[1] = hero1; 
    arr2[2] = hero2;
    
    arr3[0] = hero0;
    arr3[1] = hero1;
    arr3[2] = hero2;
    arr3[3] = hero3;
    arr3[4] = hero4;
      
    party0 = new Party(arr0);
    party1 = new Party(arr1);
    
    battle0 = new Battle(party0, party1);
    
  }
  
  // Tests first constructor within Hero class.
  @Test
  public void testHeroConstructor1() {
    assertEquals(95, hero0.getCurrentHealth());
    assertEquals(95, hero0.getMaxHealth());
    assertEquals(45, hero0.getAttack());
    assertEquals(85, hero0.getDefense());
    assertEquals(25, hero0.getEvasion());
    assertEquals(10, hero0.getMagic());
    assertEquals(30, hero0.getMagicResist());
    assertEquals(25, hero0.getSpeed());
    
    assertEquals("knight.png", hero0.getAliveImg());
    assertEquals("Hero 0", hero0.getName());
    assertEquals("skullandbones.png", hero0.getDeadImg());
    
  }
  
  // Tests second constructor within Hero class.
  @Test
  public void testHeroConstructor2() {
    assertEquals(65, hero1.getCurrentHealth());
    assertEquals(65, hero1.getMaxHealth());
    assertEquals(10, hero1.getAttack());
    assertEquals(45, hero1.getDefense());
    assertEquals(65, hero1.getEvasion());
    assertEquals(90, hero1.getMagic());
    assertEquals(70, hero1.getMagicResist());
    assertEquals(55, hero1.getSpeed());
    
    assertEquals("mage.png", hero1.getAliveImg());
    assertEquals("Hero 1", hero1.getName());
    assertEquals("death.png", hero1.getDeadImg());
    
  }
  
  // Tests die function within Hero class.
  @Test                            
  public void testHeroDie() {
    hero0.die();
    assertEquals(0, hero0.getCurrentHealth());
    assertEquals(false, hero0.getAlive());
    
  }
  
  // Tests Party object constructor.
  @Test
  public void testPartyConstructor() {
    assertArrayEquals(party0.sortAscendingSpeed(arr0), party0.getHeroes());
  }
  
  // Tests for an exception when a party not consisting of four heroes
  // is created.
  @Test(expected = RuntimeException.class) 
  public void testPartyConstructoException() {
    Party error0 = new Party(arr2);
    Party error1 = new Party(arr3);
    
  }
  
  // Tests setHeroes command within Party class.
  @Test 
  public void testPartySetHeroes() {
    party0.setHeroes(arr1);
    party1.setHeroes(arr0);
    
    assertArrayEquals(arr1, party0.getHeroes());
    assertArrayEquals(arr0, party1.getHeroes());
    
  }
  
  // Tests sortAscendingSpeed function within Party class.
  @Test
  public void testPartySortAscendingSpeed() {
    int[] expectedSpeeds = {25, 30, 60, 80};
    int[] actualSpeeds = new int[4];
    
    Hero[] test = party1.getHeroes();
    for(int i = 0; i < 4; i++) {
      actualSpeeds[i] = test[i].getSpeed();
    }
    
    assertArrayEquals(expectedSpeeds, actualSpeeds);
    
  }
  
  // Tests sortArray function within Party class.
  @Test
  public void testPartySortIntArray() {
    int[] unsorted = {56, 10, 87, 42};
    int[] sorted = {10, 42, 56, 87};
    
    assertArrayEquals(sorted, party0.sortArray(unsorted));
    
  }
  
  // Tests isDefeated function within Party class.
  @Test
  public void testPartyIsDefeated() {
    Hero[] test = party1.getHeroes();
    for(int i = 0; i < 4; i++) {
      test[i].setAlive(false);
    }
    
    assertEquals(true, party1.isDefeated());
    
  }
  
  // Tests getIndexOfHero function within Party class.
  @Test
  public void testPartyGetIndexOfHero() {
    assertEquals(0, party1.getIndexOfHero(party1.getHeroes()[0]));
    assertEquals(1, party1.getIndexOfHero(party1.getHeroes()[1]));
    assertEquals(2, party1.getIndexOfHero(party1.getHeroes()[2]));
    assertEquals(3, party1.getIndexOfHero(party1.getHeroes()[3]));
  }
  
  // Tests Battle object constructor.
  @Test
  public void testBattleConstructor() {
    assertEquals(party0, battle0.getControllableParty());
    assertEquals(party1, battle0.getEnemyParty());
    
  }
  
  // Tests user input to command matching.
  @Test
  public void testBattleGetCommand() {
    assertEquals("attack", battle0.getCommand(0, 1));
    assertEquals("magic", battle0.getCommand(1, 1));
    assertEquals("cluster", battle0.getCommand(0, 0));
    assertEquals("defend", battle0.getCommand(1, 0));
    
  }
}