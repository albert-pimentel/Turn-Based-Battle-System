/*************************************************************************
 *  Compilation:  javac Battle.java
 *  Execution:    java Battle
 *
 * Tertiary atomic object. Allows for a battle to be conducted
 * between two parties of four heroes each. Damage and stat calculation
 * already have been done within the Hero and Party classes, thus this
 * class mainly sets up the battle, aesthetically and functionally.
 * 
 * To run a test battle, press Run. Use the WASD keys to switch between moves
 * or targets, and the enter key to select.
 *  
 *  Todo
 *  ----
 *    - Decrease music volume on startup
 *    - Add a restart function after a battle is won or lost
 *    - Draw respective death images when a hero dies
 * 
 *  Version 1.0
 *************************************************************************/
import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.embed.swing.JFXPanel; 

public class Battle {
  private final Party controllable;
  private final Party enemy;
  private final double screenWidth;
  private final double screenHeight;
  
  /**
   * Constructor
   * @param controllable, the party which the player is to be in control of
   * @param enemy, the party which is to battle against the player's party
   */
  public Battle(Party controllable, Party enemy) {
    this.controllable = controllable;
    this.enemy = enemy;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.screenWidth = (int) screenSize.getWidth();
    this.screenHeight = (int) screenSize.getHeight();
    PennDraw.setCanvasSize((int) this.screenWidth, (int) this.screenHeight); 
    PennDraw.setXscale(0, screenWidth);
    PennDraw.setYscale(0, screenHeight);
    
    JFXPanel fxPanel = new JFXPanel();
    
  }
  
  /**
   * Retrieves the controllable party for the given battle.
   */
  public Party getControllableParty() {
    return this.controllable;
  }
  
  /**
   * Retrieves the enemy party for the given battle.
   */
  public Party getEnemyParty() {
    return this.enemy;
  }
  
  
  // Main testing
  public static void main(String[] args) {
    Hero knight = new Hero(95, 45, 85, 25, 10, 30, 25, "knight.png", "Knight");
    Hero mage = new Hero(65, 10, 45, 65, 90, 70, 55, "mage.png", "Mage");
    Hero thief = new Hero(55, 50, 60, 45, 25, 60, 80, "thief.png", "Thief");
    Hero assassin = new Hero(55, 70, 30, 60, 15, 30, 95, "assassin.png", "Assassin");
    
    Hero skeletonKing = new Hero(100, 80, 80, 30, 80, 70, 80, "skeleton.png", "Skeleton King");
    Hero zombie = new Hero(85, 85, 60, 30, 60, 60, 25, "zombie.png", "Zombie");
    Hero fireElemental = new Hero(90, 70, 60, 65, 85, 75, 60, "fireelemental.png",
                                  "Fire Elemental");
    Hero ghost = new Hero(85, 65, 50, 70, 75, 65, 30, "ghost.png", "Ghost");
    
    Hero[] heroes = new Hero[4];
    Hero[] enemies = new Hero[4];
    
    heroes[0] = knight; 
    heroes[1] = thief; 
    heroes[2] = mage; 
    heroes[3] = assassin; 
    
    enemies[0] = skeletonKing; 
    enemies[1] = zombie; 
    enemies[2] = fireElemental;
    enemies[3] = ghost; 
    
    // Testing by-speed party ordering
    int speedTest0 = printSpeeds(heroes);
    
    Party party0 = new Party(heroes);
    
    int speedTest1 = printSpeeds(party0.getHeroes());
    
    int speedTest2 = printSpeeds(enemies);
    
    Party party1 = new Party(enemies);
    
    int speedTest3 = printSpeeds(party1.getHeroes());
    
    Battle battle = new Battle(party0, party1);
    battle.startBattle();
  }
  
  /**
   * Determines which party is to move first
   * and begins the battle.
   */
  private void startBattle() {
    Party firstParty = this.determineFirstParty();
    Party secondParty;
    if(firstParty.equals(this.controllable)) {
      secondParty = this.enemy;
    }
    else {
      secondParty = this.controllable;
    }
    
    this.setStage();
    this.drawText("Welcome to Albert's battle sim! Use WASD + enter");
    
    while(!(this.controllable.isDefeated()) && !(this.enemy.isDefeated())) {
      for(int i = 0; i < 4; i++) {
        if(!(firstParty.getHeroes()[i].getAlive())) {
          continue;
        }
        drawControllablePartyPointer(i);
        
        String command = this.checkCommand();
        
        if(command.equals("attack")) {
          int target = this.checkTarget();
          drawTextbox();
          drawText(firstParty.getHeroes()[i].attack(secondParty.getHeroes()[target], false));
        }
        
        if(command.equals("magic")) {
          int target = this.checkTarget();
          drawTextbox();
          drawText(firstParty.getHeroes()[i].magic(secondParty.getHeroes()[target]));
        }
        
        if(command.equals("cluster")) {
          for(int j = 0; j < 4; j++) {
            drawEnemyPointer(j);
          }
          drawTextbox();
          drawText(firstParty.getHeroes()[i].cluster(secondParty));
        }
        
        if(command.equals("defend")) {
          for(int j = 0; j < 4; j++) {
            drawEnemyPointer(j);
          }
          drawTextbox();
          drawText(firstParty.getHeroes()[i].defend());
        }
        
        eraseControllablePartyPointers();
        eraseEnemyPointers();
        clearHealth();
        drawAllHealth();
      }
      
      secondParty.randomAttack(firstParty);
      clearHealth();
      drawAllHealth();
      drawTextbox();  
    }
    
    drawTextbox();
    if(this.controllable.isDefeated()) {
      drawText("You lost!");
    }
    else {
      drawText("You won!");
    }
  }
  
  /**
   * Draws a black image over where both parties' health appears,
   * effectively erasing all health images.
   */
  private void clearHealth() {
    PennDraw.picture(0.05 * this.screenWidth, 0.635 * this.screenHeight,
                     "background.png", 0.10 * this.screenWidth, 0.63 * this.screenHeight);
    PennDraw.picture(0.95 * this.screenWidth, 0.635 * this.screenHeight,
                     "background.png", 0.10 * this.screenWidth, 0.63 * this.screenHeight);                
  }
  
  /**
   * Draws a pointer pointing to a hero in the controllable party.
   * @param heroIndex, the index of the hero in the controllable party's hero array.
   */
  private void drawControllablePartyPointer(int heroIndex) {
    if(heroIndex < 0 || heroIndex > 3) {
      return;
    }
    PennDraw.picture(0.25 * this.screenWidth, (0.89 - (0.17 * heroIndex)) * this.screenHeight,
                     "pointerreversed.png", 0.06 * this.screenWidth, 0.075 * this.screenHeight);  
  }
  
  /**
   * Draws a black image over the area where pointers point to the controllable party's icons,
   * effectively erasing all pointers pointing to the controllable party.
   */
  private void eraseControllablePartyPointers() {
    PennDraw.picture(0.25 * this.screenWidth, 0.635 * this.screenHeight,
                     "background.png", 0.06* this.screenWidth, 0.63 * this.screenHeight);
  }
  
  /**
   * Checks user key input and retrieves the target the user
   * intends to attack.
   */
  private int checkTarget() {
    int targetIndex = 0;
    String enter0 = ((char) 10) + "";
    String enter1 = ((char) 13) + "";
    this.drawEnemyPointer(targetIndex);
    while(true) {
      if(PennDraw.hasNextKeyTyped()) {
        String input = PennDraw.nextKeyTyped() + "";
        if(input.equals(enter0) || input.equals(enter1)) {
          eraseEnemyPointers();
          return targetIndex;
        }
        if(input.equals("s") || input.equals("S")) {
          if(targetIndex == 3) {
            targetIndex = 0;
            eraseEnemyPointers();
            drawEnemyPointer(targetIndex);
          }
          else {
            if(targetIndex < 3) {
              targetIndex++;
              eraseEnemyPointers();
              drawEnemyPointer(targetIndex);
            }
          }      
        }
        if(input.equals("w") || input.equals("W")) {
          if(targetIndex == 0) {
            targetIndex = 3;
            eraseEnemyPointers();
            drawEnemyPointer(targetIndex);
          }
          else {
            if(targetIndex > 0) {
              targetIndex--;
              eraseEnemyPointers();
              drawEnemyPointer(targetIndex);       
            }
          }
        }
      }
    }
  }
  
  /**
   * Draws a pointer pointing to a given hero in the enemy (right) party.
   * @param targetIndex, the index of the hero to be drawn in the enemy party's
   * hero array.
   */
  private void drawEnemyPointer(int targetIndex) {
    if(targetIndex < 0 || targetIndex > 3) {
      return;
    }
    PennDraw.picture(0.745 * this.screenWidth, (0.89 - (0.17 * targetIndex)) * this.screenHeight,
                     "pointer.png", 0.06 * this.screenWidth, 0.075 * this.screenHeight);                   
  }
  
  /**
   * Draws a black image over where the enemy pointers are drawn, effectively
   * erasing all previously enemy pointers.
   */
  private void eraseEnemyPointers() {
    PennDraw.picture(0.75 * this.screenWidth, 0.635 * this.screenHeight,
                     "background.png", 0.06* this.screenWidth, 0.63 * this.screenHeight);
  }
  
  /**
   * Checks user key input and retrieves the move the user
   * intends to use, i.e. "attack", "magic", "cluster", "defend", 
   * as a String.
   */
  private String checkCommand() {
    int commandSpotsRight = 0;
    int commandSpotsUp = 1;
    String enter0 = ((char) 10) + "";
    String enter1 = ((char) 13) + "";                  
    this.drawPointer("attack");
    while(true) {
      if(PennDraw.hasNextKeyTyped()) {
        String input = PennDraw.nextKeyTyped() + "";
        if(input.equals(enter0) || input.equals(enter1)) {
          eraseGUIPointers();
          return getCommand(commandSpotsRight, commandSpotsUp);
        }
        
        if(input.equals("w") || input.equals("W")) {
          if(commandSpotsUp == 1) {
            commandSpotsUp = 0;
            eraseGUIPointers();
            drawPointer(getCommand(commandSpotsRight, commandSpotsUp));
          }
          else {
            if(commandSpotsUp == 0) {
              commandSpotsUp++;
              eraseGUIPointers();
              drawPointer(getCommand(commandSpotsRight, commandSpotsUp));
            }
          }   
        }
        
        if(input.equals("a") || input.equals("A")) {
          if(commandSpotsRight == 0) {
            commandSpotsRight = 1;
            eraseGUIPointers();
            drawPointer(getCommand(commandSpotsRight, commandSpotsUp));
          }
          else {
            if(commandSpotsRight == 1) {
              commandSpotsRight--; 
              eraseGUIPointers();
              drawPointer(getCommand(commandSpotsRight, commandSpotsUp));
            }
          }    
        }
        
        if(input.equals("s") || input.equals("S")) {
          if(commandSpotsUp == 0) {
            commandSpotsUp = 1;
            eraseGUIPointers();
            drawPointer(getCommand(commandSpotsRight, commandSpotsUp));     
          }
          else {
            if(commandSpotsUp == 1) {
              commandSpotsUp--;
              eraseGUIPointers();
              drawPointer(getCommand(commandSpotsRight, commandSpotsUp));     
            }
          }   
        }
        
        if(input.equals("d") || input.equals("D")) {
          if(commandSpotsRight == 1) {
            commandSpotsRight = 0;
            eraseGUIPointers();
            drawPointer(getCommand(commandSpotsRight, commandSpotsUp));
          }
          else {
            if(commandSpotsRight == 0) {
              commandSpotsRight++;
              eraseGUIPointers();
              drawPointer(getCommand(commandSpotsRight, commandSpotsUp));
            }
          }
        }
      }
    }
  } 
  
  /**
   * Retrieves what move the user intends to perform, based on two variables:
   * the number of slots to the right (0 being left, 1 being right), and
   * the number of slots up (0 being down, 1 being up). For example, the "cluster"
   * icon (lower left) is located at (0,0) whereas the "magic icon" is located at (1,1)
   * (upper left).
   * @param right, the number of GUI slots to the right
   * @param left, the number of GUI slots up
   */
  public String getCommand(int right, int up) {
    if(right + up == 0) {
      return "cluster"; 
    }
    if(right + up == 2) {
      return "magic";
    }
    if(right == 1) {
      return "defend";
    }
    return "attack";
  }
  
  /**
   * Draws a sword pointing to what move the user is currently hovering over.
   * @param command, a String indicating what move is being hovered over
   */
  private void drawPointer(String command) {
    if(command.equals("attack")) {
      PennDraw.picture(0.38 * this.screenWidth, 0.9 * this.screenHeight,
                       "pointerupsidedown.png", 0.06 * this.screenWidth, 0.1 * this.screenHeight);
    }
    if(command.equals("magic")) {
      PennDraw.picture(0.62 * this.screenWidth, 0.9 * this.screenHeight,
                       "pointerupsidedown.png", 0.06 * this.screenWidth, 0.1 * this.screenHeight);
    }
    if(command.equals("cluster")) {
      PennDraw.picture(0.38 * this.screenWidth, 0.55 * this.screenHeight,
                       "pointervertical.png", 0.06 * this.screenWidth, 0.1 * this.screenHeight);
    }
    if(command.equals("defend")) {
      PennDraw.picture(0.62 * this.screenWidth, 0.55 * this.screenHeight,
                       "pointervertical.png", 0.06 * this.screenWidth, 0.1 * this.screenHeight);
    }
  }
  
  /**
   * Draws a black image over a portion of the screen's center and then
   * redraws the move GUI, effectively
   * erasing all GUI pointers.
   */
  private void eraseGUIPointers() {
    PennDraw.picture(0.5 * this.screenWidth, 0.7 * this.screenHeight,
                     "background.png", 0.40 * this.screenWidth, 0.8 * this.screenHeight);
    this.drawGUI();
  }
  
  /**
   * Draws a single hero's health.
   * @param party, the party in which that hero is in
   * @param heroInParty, the hero in question
   */
  private void drawHealth(Party party, Hero heroInParty) {
    PennDraw.setPenColor(255, 255, 255);
    PennDraw.setFont("Cambria Math");
    PennDraw.setFontSize(40);
    int index = party.getIndexOfHero(heroInParty);
    if(party.equals(this.controllable)) {
      PennDraw.text(0.05 * this.screenWidth,
                    0.38 * this.screenHeight + (index * 0.17 * this.screenHeight),
                    controllable.getHeroes()[Math.abs(index - 3)].getCurrentHealth() +
                    "/" + controllable.getHeroes()[Math.abs(index - 3)].getMaxHealth());
      
    }
    
    if(party.equals(this.enemy)) {
      PennDraw.text(0.94 * this.screenWidth,
                    0.38 * this.screenHeight + (index * 0.17 * this.screenHeight),
                    enemy.getHeroes()[Math.abs(index - 3)].getCurrentHealth() +
                    "/" + enemy.getHeroes()[Math.abs(index - 3)].getMaxHealth());
      
    }
  }
  
  /**
   * Draws both parties' health, displayed as currentHealth / maxHealth.
   */
  private void drawAllHealth() {
    PennDraw.setPenColor(255, 255, 255);
    PennDraw.setFont("Cambria Math");
    PennDraw.setFontSize(40);
    for(int i = 0; i < 4; i++) {
      this.drawHealth(this.controllable, this.controllable.getHeroes()[i]);
      this.drawHealth(this.enemy, this.enemy.getHeroes()[i]);
    }
  }
  
  /**
   * For testing purposes. Prints speeds of all heroes in a hero array.
   * @param heroes, the array of heroes whose speeds are to be printed
   */
  private static int printSpeeds(Hero[] heroes) {
    System.out.println("");
    for(int i = 0; i < heroes.length; i++) {
      System.out.print(heroes[i].getSpeed() + " ");
      System.out.println(heroes[i].getAliveImg());
    }
    return -1;
  }
  
  /**
   * Makes the background black, draws both parties, draws move GUI, draws a 
   * blank textbox, and plays music.
   */
  private void setStage() {
    PennDraw.clear(0, 0, 0);
    this.drawParties();
    this.drawAllHealth();
    this.drawGUI();
    this.drawTextbox();
    this.playMusic();
  }
  
  /**
   * Draws a blank textbox, as well as erases the text previously there.
   */
  private void drawTextbox() {
    PennDraw.picture(0.5 * this.screenWidth, 0.16 * this.screenHeight,
                     "textbox.png", 0.8 * this.screenWidth, 0.22 * this.screenHeight);
  }
  
  /**
   * Draws the "attack", "magic", "cluster", and "defend" icons.
   */
  private void drawGUI() {
    PennDraw.picture(0.38 * this.screenWidth, 0.815 * this.screenHeight,
                     "attack.png", 0.2 * this.screenWidth, 0.15 * this.screenHeight);
    PennDraw.picture(0.62 * this.screenWidth, 0.815 * this.screenHeight,
                     "magic.png", 0.2 * this.screenWidth, 0.15 * this.screenHeight);   
    PennDraw.picture(0.38 * this.screenWidth, 0.65 * this.screenHeight,
                     "cluster.png", 0.2 * this.screenWidth, 0.15 * this.screenHeight);
    PennDraw.picture(0.62 * this.screenWidth, 0.65 * this.screenHeight,
                     "defend.png", 0.2 * this.screenWidth, 0.15 * this.screenHeight);
  }
  
  /**
   * Plays chiptune music as the battle starts.
   */
  private void playMusic() {
    String music = "battlemusic.mp3";
    Media file = new Media(new File(music).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(file);
    mediaPlayer.play();
  }
  
  /**
   * Draws both parties.
   */
  private void drawParties() {
    this.drawControllableParty();
    this.drawEnemyParty();
  }
  
  /**
   * Draws all images of the controllable party in their respective places.
   */
  private void drawControllableParty() {
    for(int i = 0; i < 4; i++) {
      this.controllable.getHeroes()[Math.abs(i - 3)].drawAlive(0.16 * this.screenWidth,
                                         (0.38 * this.screenHeight) + (0.17 * i * this.screenHeight),
                                                               this.screenWidth, this.screenHeight);
    }
  }
  
  /**
   * Draws all images of the enemy party in their respective places.
   */
  private void drawEnemyParty() {
    for(int i = 0; i < 4; i++) {
      this.enemy.getHeroes()[Math.abs(i - 3)].drawAlive(0.84 * this.screenWidth,
                                                        (0.38 * this.screenHeight) + (0.17 * i * this.screenHeight),
                                                        this.screenWidth, this.screenHeight);
    }
  }
  
  /**
   * Draws text in the textbox at the bottom of the screen.
   * @param text, the text to be drawn
   */
  private void drawText(String text) {
    PennDraw.setPenColor(255, 255, 255);
    PennDraw.setFont("Cambria Math");
    PennDraw.setFontSize(60);
    if(text.length() > 60) {
      PennDraw.textLeft(0.14 * this.screenWidth, 0.16 * this.screenHeight,
                        "Printed text cannot exceed 60 characters");
      return;
    }
    PennDraw.textLeft(0.14 * this.screenWidth, 0.16 * this.screenHeight,
                      text);
  }
  
  /**
   * Decides which party will go first, based on speed totals.
   * If one party has greater total speed, they go first.
   * If both parties have the same speed, each party has a 50% chance
   * of going first.
   */
  public Party determineFirstParty() {
    int controllablePartySpeed = 0;
    int enemyPartySpeed = 0;
    
    for(int i = 0; i < 4; i++) {
      controllablePartySpeed = controllablePartySpeed +
        this.getControllableParty().getHeroes()[i].getSpeed();  
    }
    
    for(int i = 0; i < 4; i++) {
      enemyPartySpeed = enemyPartySpeed +
        this.getEnemyParty().getHeroes()[i].getSpeed();  
    }
    
    if(controllablePartySpeed > enemyPartySpeed) {
      return this.getControllableParty();
    }
    
    if(controllablePartySpeed < enemyPartySpeed) {
      return this.getEnemyParty();
    }
    
    if(controllablePartySpeed == enemyPartySpeed) {
      double rndm = Math.random();
      if(rndm < 0.5) {
        return this.getControllableParty();
      }
      else {
        return this.getEnemyParty();
      }
    }
    // Unreachable return statement; present for function completion  
    return this.getControllableParty();
  }
  
}