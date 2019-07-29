/*************************************************************************
 *  Compilation:  javac Party.java
 *  Execution:    java Party
 *
 *  Secondary atomic object. Object containg array of four heroes to be utilized 
 *  in a battle. A party instance stores four heroes and subsequently is meant to be stored
 *  in a Battle instance, alongside another party instance, to begin battling.
 *
 *  Version 1.0
 *************************************************************************/
public class Party {
  private Hero[] heroes = new Hero[4];
  
  /**
   * Constructor
   * @param heroes, the array of 4 heroes that will make up the party
   */
  public Party(Hero [] heroes) {
    if(heroes.length != 4) {
      throw new RuntimeException("A party must have 4 filled hero slots.");
    }
    this.heroes = sortAscendingSpeed(heroes);
  }
  
  /**
   * Returns the object's current hero array.
   */
  public Hero[] getHeroes() {
    return this.heroes; 
  }
  
  /**
   * Sets this object's hero array to a given hero array.
   * @param heroes, the hero array to be set as this object's hero array 
   */
  public void setHeroes(Hero[] heroes) {
    if(heroes.length != 4) {
      throw new RuntimeException("A party must have 4 filled hero slots.");
    }
    this.heroes = heroes;     
  }
  
  /**
   * Makes each member of a given party choose a random move
   * and use it on a random opposing party member.
   * @param opposing, the party to be attacked
   */
  public void randomAttack(Party opposing) {
    for(int i = 0; i < 4; i++) {
      if(!(this.heroes[i].getAlive())) {
        continue;
      }
      
      String attack = getAttack();
      
      if(attack.equals("cluster")) {
        this.heroes[i].cluster(opposing);
        continue;
      }
      if(attack.equals("defend")) {
        this.heroes[i].defend();
        continue;
      }
      
      Hero randomHero = getRandomAliveHero(opposing);
      if(attack.equals("attack")) {
        this.heroes[i].attack(randomHero, false);
        continue;
      }
      if(attack.equals("magic")) {
        this.heroes[i].magic(randomHero);
        continue;
      }
    }
  }
  
  /**
   * Randomly choses an alive party member from a given party
   * @param party, any party with at least 1 alive hero
   */
  private Hero getRandomAliveHero(Party party) {
    while(true) {
      double rndm = Math.random() * 100;
      for(int i = 0; i < 4; i++) {
        if(rndm <= 25) {
          if(party.heroes[i].getAlive()) {
            return party.heroes[i];
          }
        } 
      } 
    }
  }
  
  /**
   * Randomly choses an attack for an enemy party member to make.
   * Attack - 35% chance. Magic - 35% chance. Cluster - 25% chance
   * Defend - 5% chance
   */
  private String getAttack() {
    double rndm = Math.random() * 100;
    if(rndm <= 35) {
      return "attack";
    }
    if(rndm <= 70) {
      return "magic";
    }
    if(rndm <= 95) {
      return "cluster";
    }
    if(rndm <= 100) {
      return "defend";
    }
    return "";
  }
  
  /**
   * Returns a given hero array sorted in order of ascending speed
   * @param heroes, a hero array of size 4
   */
  public static Hero[] sortAscendingSpeed(Hero [] heroes) {
    Hero[] sortedHeroes = new Hero[4];
    int[] unsortedSpeeds = new int[4];
    for(int i = 0; i < 4; i++) {
      unsortedSpeeds[i] = heroes[i].getSpeed();
    }
    int[] sortedSpeeds = sortArray(unsortedSpeeds);
    for(int i = 0; i < 4; i++) {
      for(int j = 0; j < 4; j++) {
        if(sortedSpeeds[i] == heroes[j].getSpeed() &&
           heroes[j].getSpeed() != -1) {
          sortedHeroes[i] = new Hero(heroes[j].getMaxHealth(),
                                     heroes[j].getAttack(), heroes[j].getDefense(), 
                                     heroes[j].getEvasion(), heroes[j].getMagic(), 
                                     heroes[j].getMagicResist(), heroes[j].getSpeed(), 
                                     heroes[j].getAliveImg(), heroes[j].getDeadImg(), 
                                     heroes[j].getName());
          heroes[j].setSpeed(-1);
          break;
        }
      }
    }
    return sortedHeroes;
  }
  
  /**
   * Returns an inputted array sorted in ascending order.
   * @param nonSortedArray, any integer array
   */
  public static int[] sortArray(int[] nonSortedArray) {
    int[] sortedArray = new int[nonSortedArray.length];
    int temp;
    for (int i = 0; i < nonSortedArray.length - 1; i++) {
      for (int j = 0; j < nonSortedArray.length - 1; j++) {
        if (nonSortedArray[j] > nonSortedArray[j + 1]) {
          temp = nonSortedArray[j];
          nonSortedArray[j] = nonSortedArray[j + 1];
          nonSortedArray[j + 1] = temp;
          sortedArray = nonSortedArray;
        }
      }
    }
    return sortedArray;
  }
  
  /**
   * Returns true if all party members at 0 health, false otherwise.
   */
  public boolean isDefeated() {
    int deathCount = 0;
    for(int i = 0; i < 4; i++) {
      if(!(this.heroes[i].getAlive())) {
        deathCount++;
      }
    }
    if(deathCount == 4) {
      return true;
    }
    return false;
  }
  
  /**
   * Returns the array index of a hero within the party.
   * @param hero, the hero to be searched for
   */
  public int getIndexOfHero(Hero hero) {
    for(int i = 0; i < 4; i++) {
      if(this.heroes[i].equals(hero)) {
        return i;
      } 
    }
    return -1;
  }
}