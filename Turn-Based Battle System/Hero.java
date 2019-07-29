/*************************************************************************
 *  Compilation:  javac Hero.java
 *  Execution:    java Hero
 *
 *  Primary atomic object. A hero is essentially a character with various
 *  stats, enumerated in the fields below, similar to those found in
 *  classical turn-based RPGs. Heroes have four moves they can perform in battle:
 *  attack (a single target attack, whose damage is based on the attack stat),
 *  magic  (a single target attack, whose damage is based on the magic stat),
 *  cluster (a group attack, targeting every enemy hero, based on the attack stat),
 *  defend (a defensive maneuver, doubling a hero's defense and magic resistance for a turn).
 *  Each stat must be an integer from 1 to 100. If currentHealth reaches 0, the hero is 
 *  considered dead.
 *  
 *  Todo
 *  ----
 *    -  Balance attack, magic, and cluster damage. Make attack and defense
 *       stats more meaningful in terms of damage calculation.
 *    -  Balance defend, in order to make it useful at least situationally
 * 
 *  Version 1.0
 *************************************************************************/
public class Hero {
  private int currentHealth; 
  private int maxHealth;
  private int attack;
  private int defense;
  private int evasion;
  private int speed;
  private int magic;
  private int magicResist;
  private boolean alive;
  private boolean defending;
  private String aliveImg;
  private String deadImg;
  private String name;
  
  // Retrieves given hero's current health
  public int getCurrentHealth() {
    return this.currentHealth;
  }
  
  // Sets given hero's current health
  public void setCurrentHealth(int currentHealth) {
    this.currentHealth = currentHealth;
  }
  
  // Retrieves given hero's maximum health
  public int getMaxHealth() {
    return this.maxHealth;
  }
  
  // Retrieves given hero's attack points
  public int getAttack() {
    return this.attack;
  }
  
  // Sets given hero's attack points
  public void setAttack(int attack) {
    this.attack = attack;
  }
  
  // Retrieves given hero's defense points
  public int getDefense() {
    return this.defense;
  }
  
  // Sets given hero's defense points
  public void setDefense(int defense) {
    this.defense = defense;
  }
  
  // Retrieves given hero's evasion points
  public int getEvasion() {
    return this.evasion;
  }
  
  // Retrieves given hero's speed points
  public int getSpeed() {
    return this.speed;
  }
  
  // Sets given hero's speed points
  public void setSpeed(int speed) {
    this.speed = speed;
  }
  
  // Retrieves given hero's magic points
  public int getMagic() {
    return this.magic;
  }
  
  // Retrieves given hero's magic resistance points
  public int getMagicResist() {
    return this.magicResist; 
  }
  
  // Sets given hero's magic resistance points
  public void setMagicResist(int magicResist) {
    this.magicResist = magicResist;
  }
  
  // Retrieves whether or not given hero is alive
  public boolean getAlive() {
    return this.alive;
  }
  
  // Sets whether or not given hero is alive
  public void setAlive(boolean alive) {
    this.alive = alive;
  }
  
  // Retrieves whether or not given hero is defending
  public boolean getDefending() {
    return this.defending;
  }
  
  // Sets whether or not given hero is defending
  public void setDefending(boolean defending) {
    this.defending = defending;
  }
  
  // Retrieves the image displayed when the battle starts
  public String getAliveImg() {
    return this.aliveImg;
  }
  
  // Retrieves the image displayed when the hero dies
  public String getDeadImg() {
    return this.deadImg;
  }
  
  // Retrieves hero's name
  public String getName() {
    return this.name;
  }
  
  /**
   * Constructor 1 - Utilizes default deadImg field.
   * @params maxHealth, hero's maximum hit points
   * @params attack, hero's attack points for calculating damage given
   * @params defense, hero's defense points for calculating damage taken
   * @params evasion, hero's evasion points for calculating dodge probability
   * @params magic, hero's magic points for calculating magic damage dealt
   * @params magicResist, hero's magic resist points for calculating magic damage taken
   * @params speed, hero's speed points for calculating move order
   * @params aliveImg, the image to be displayed upon starting the battle
   * 
   */
  public Hero(int maxHealth, int attack, int defense, int evasion, int magic, 
              int magicResist, int speed, String aliveImg, String name) {
    
    if(maxHealth <= 0 || attack <= 0 || defense <= 0 || evasion <= 0 ||
       speed <= 0 || magic <= 0 || magicResist <= 0) {
      throw new RuntimeException("Character must have at least 1 of each stat.");
    }
    
    this.currentHealth = maxHealth;
    this.maxHealth = maxHealth;
    this.attack = attack;
    this.defense = defense;
    this.evasion = evasion;
    this.speed = speed;
    this.magic = magic;
    this.magicResist = magicResist;
    
    this.aliveImg = aliveImg;
    this.deadImg = "skullandbones.png";
    this.name = name;
    
    this.alive = true;
    this.defending = false;
  }
  
  /**
   * Constructor 2 - Params same as previous, except this constructor allows
   * a hero to be constructed with a custom dead image.
   * @params maxHealth, hero's maximum hit points
   * @params attack, hero's attack points for calculating damage given
   * @params defense, hero's defense points for calculating damage taken
   * @params evasion, hero's evasion points for calculating dodge probability
   * @params magic, hero's magic points for calculating magic damage dealt
   * @params magicResist, hero's magic resist points for calculating magic damage taken
   * @params speed, hero's speed points for calculating move order
   * @params aliveImg, the image to be displayed upon starting the battle
   * @params deadImg, image to be displayed upon hero's death
   * 
   */
  public Hero(int maxHealth, int attack, int defense, int evasion, int magic, 
              int magicResist, int speed, String aliveImg, String deadImg, 
              String name) {
    
    if(maxHealth <= 0 || attack <= 0 || defense <= 0 || evasion <= 0 ||
       speed <= 0 || magic <= 0 || magicResist <= 0) {
      throw new RuntimeException("Character must have at least 1 of each stat.");
    }
    
    this.currentHealth = maxHealth;
    this.maxHealth = maxHealth;
    this.attack = attack;
    this.defense = defense;
    this.evasion = evasion;
    this.speed = speed;
    this.magic = magic;
    this.magicResist = magicResist;
    
    this.aliveImg = aliveImg;
    this.deadImg = deadImg;
    this.name = name;
    
    this.alive = true;
    this.defending = false;
  }
  
  /**
   * Attacks an opposing hero with a normal attack.
   * @param enemy, the opposing hero to be attacked
   * @param cluster, since attack is when using cluster,
   * this parameter ensures that less damage dealt when
   * cluster is being used
   */
  public String attack(Hero enemy, boolean cluster) {
    String msg;
    double dodgeChance = enemy.getEvasion() * 0.25;
    if(Math.random() * 100 <= dodgeChance) {
      msg = enemy.getName() + " dodged the attack!";
      return msg;
    }
    
    int damageDealt;
    if(!(cluster)) {
    if(this.getAttack() >= enemy.getDefense()) {
      damageDealt = 20 + ((this.getAttack() - enemy.getDefense()) / 3);
    }
    else {
      damageDealt = 20 + ((enemy.getDefense() - this.getAttack()) / 3);
    }
    
    if(damageDealt >= enemy.getCurrentHealth()) {
      enemy.die(); 
      msg = enemy.getName() + " took " + damageDealt + " damage and died!";
      return msg;
    }
    else {
      enemy.setCurrentHealth(enemy.getCurrentHealth() - damageDealt);
      if(enemy.getDefending()) {
        enemy.setDefense(enemy.getDefense() / 2);
        enemy.setMagicResist(enemy.getMagicResist() / 2);
        enemy.setDefending(false);
        msg = enemy.getName() + " was defending and took " + damageDealt + " damage!";
        return msg;
      }
      msg = enemy.getName() + " took " + damageDealt + " damage!";
      return msg;
    }
    }
    else {
      if(this.getAttack() >= enemy.getDefense()) {
        damageDealt = 3 + ((this.getAttack() - enemy.getDefense()) / 3);
      }
      else {
        damageDealt = 3 + ((enemy.getDefense() - this.getAttack()) / 3);
      }
      
      if(damageDealt >= enemy.getCurrentHealth()) {
        enemy.die(); 
        msg = enemy.getName() + " took " + damageDealt + " damage and died!";
        return msg;
      }
      else {
        enemy.setCurrentHealth(enemy.getCurrentHealth() - damageDealt);
        if(enemy.getDefending()) {
          enemy.setDefense(enemy.getDefense() / 2);
          enemy.setMagicResist(enemy.getMagicResist() / 2);
          enemy.setDefending(false);
          msg = enemy.getName() + " was defending and took " + damageDealt + " damage!";
          return msg;
        }
        msg = enemy.getName() + " took " + damageDealt + " damage!";
        return msg;
      }
    }
  }
  
  /**
   * Attacks an opposing hero with a magic attack, often dealing
   * more damage than a normal attack.
   * @param enemy, the opposing hero to be attacked with magic
   */
  public String magic(Hero enemy) {
    String msg;
    double dodgeChance = enemy.getEvasion() * 0.25;
    if(Math.random() * 100 <= dodgeChance) {
      msg = enemy.getName() + " dodged the attack!";
      return msg;
    }
    
    int magicDealt;
    if(this.getMagic() >= enemy.getMagicResist()) {
      magicDealt = 25 + ((this.getMagic() - enemy.getMagicResist()) / 3);
    }
    else {
      magicDealt = 25 + ((enemy.getMagicResist() - this.getMagic()) / 3);
    }
    
    if(magicDealt >= enemy.getCurrentHealth()) {
      enemy.die(); 
      msg = enemy.getName() + " took " + magicDealt + " magic damage and died!";
      return msg;
    }
    else {
      enemy.setCurrentHealth(enemy.getCurrentHealth() - magicDealt);
      if(enemy.getDefending()) {
        enemy.setDefense(enemy.getDefense() / 2);
        enemy.setMagicResist(enemy.getMagicResist() / 2);
        enemy.setDefending(false);
        msg = enemy.getName() + " was defending and took " + magicDealt + " magic damage!";
        return msg;
      }
      msg = enemy.getName() + " took " + magicDealt + " magic damage!";
      return msg;
    }
  }
  
  /**
   * Increases both defense and magic resistance until after the hero
   * is attacked.
   */
  public String defend() {
    this.setDefense(2 * this.getDefense());
    this.setMagicResist(2 * this.getMagicResist());
    this.setDefending(true);
    String msg = this.getName() + " braced itself for incoming damage!";
    return msg;
  }
  
  /**
   * Attacks all of an opposing party's members at once, but
   * with reduced attack.
   * @param enemy, the opposing party to be group attacked
   */
  public String cluster(Party enemy) {
    for(int i = 0; i < enemy.getHeroes().length; i++) {
      this.attack(enemy.getHeroes()[i], true);
    }
    String msg = this.getName() + " attacked the entire party in one fell swoop!";
    return msg;
  }
  
  /**
   * Sets this hero to 0 health and alive to false.
   */
  public void die() {
    this.setCurrentHealth(0);
    this.setAlive(false);
  }
  
  /**
   * Draws this object's alive image at the given location.
   * @param xCenterCoord, the x-coordinate of the image's intended center
   * @param yCenterCoord, the y-coordinate of the image's intended center
   * @param screenWidth, the width of the user's screen in pixels
   * @param screenHeight, the height of the user's screen in pixels
   */
  public void drawAlive(double xCenterCoord, double yCenterCoord,
                        double screenWidth, double screenHeight) {
    PennDraw.picture(xCenterCoord, yCenterCoord, this.getAliveImg(), 
                     0.12 * screenWidth, 0.12 * screenHeight);
  }
  
  /**
   * Draws this object's dead image at a given location.
   * @param xCenterCoord, the x-coordinate of the image's intended center
   * @param yCenterCoord, the y-coordinate of the image's intended center
   */
  public void drawDead(double xCenterCoord, double yCenterCoord) {
    PennDraw.picture(xCenterCoord, yCenterCoord, this.getDeadImg());
  }
  
}