import java.util.*;

enum Directions {
  NORTH, SOUTH, WEST, EAST;

  public static boolean contains(String str) {
    for (Directions direction : values()) {
      if (direction.toString().equalsIgnoreCase(str))
        return true;
    }
    return false;
  }

}

enum Commands {
  MOVE, LEFT, RIGHT, REPORT, ROBOT;

  public static boolean contains(String str) {
    for (Commands command : values()) {
      if (command.toString().equalsIgnoreCase(str))
        return true;
    }
    return false;
  }
}

class Robot {

  private int x;
  private int y;
  private Directions facingDirection;
  private boolean active;
  private int ID;

  public Robot() {
    this.x = -1;
    this.y = -1;
    this.active = false;
    this.ID = -1;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Directions getFacingDirection() {
    return facingDirection;
  }

  public boolean getActive() {
    return active;
  }

  public int getID() {
    return ID;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setFacingDirection(Directions direction) {
    this.facingDirection = direction;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  //move robot function
  public void move() {
    switch (this.facingDirection) {
      case NORTH:
        if (this.y < Table.POSITION_MAX) {
          ++this.y;
          System.out.println("Robot " + ID + " move successfully.\n");
        } else {
          System.out.println("Cannot move. " + "Robot " + ID + " is about to fall.\n");
        }
        break;
      case SOUTH:
        if (this.y > Table.POSITION_MIN) {
          --this.y;
          System.out.println("Robot " + ID + " move successfully.\n");
        } else {
          System.out.println("Cannot move. " + "Robot " + ID + " is about to fall.\n");
        }
        break;
      case EAST:
        if (this.x < Table.POSITION_MAX) {
          ++this.x;
          System.out.println("Robot " + ID + " move successfully.\n");
        } else {
          System.out.println("Cannot move. " + "Robot " + ID + " is about to fall.\n");
        }
        break;
      case WEST:
        if (this.x > Table.POSITION_MIN) {
          --this.x;
          System.out.println("Robot " + ID + " move successfully.\n");
        } else {
          System.out.println("Cannot move. " + "Robot " + ID + " is about to fall.\n");
        }
        break;
      default:
        System.out.println("Move failed.");
    }
  }

  //rotate robot to right
  public void rotateRight() {
    boolean flag = false;
    switch (this.facingDirection) {
      case NORTH:
        this.setFacingDirection(Directions.EAST);
        break;
      case SOUTH:
        this.setFacingDirection(Directions.WEST);
        break;
      case EAST:
        this.setFacingDirection(Directions.SOUTH);
        break;
      case WEST:
        this.setFacingDirection(Directions.NORTH);
        break;
      default:
        flag = true;
        System.out.println("Rotate Right failed.");
    }
    if (flag)
      System.out.println("Robot " + ID + " turn right successfully!");
  }

//rotate robot to left
  public void rotateLeft() {
    boolean flag = false;
    switch (this.facingDirection) {
      case NORTH:
        this.setFacingDirection(Directions.WEST);
        break;
      case SOUTH:
        this.setFacingDirection(Directions.EAST);
        break;
      case EAST:
        this.setFacingDirection(Directions.NORTH);
        break;
      case WEST:
        this.setFacingDirection(Directions.SOUTH);
        break;
      default:
        flag = true;
        System.out.println("Rotate left failed.");
    }
    if (flag)
      System.out.println("Robot " + ID + " turn left successfully!");
  }

  //print report out
  public void report() {
    System.out.print(this.x + "," + this.y + "," + this.facingDirection.toString().toUpperCase() + " Status: "
        + (this.active ? "active" : "inactive") + "\n");
  }
}

class Table {
  static final int POSITION_MAX = 5;
  static final int POSITION_MIN = 0;
  private ArrayList<Robot> robots;
  private ArrayList<Integer> activeRobotsID;

  public Table() {
    this.robots = new ArrayList<Robot>();
    this.activeRobotsID = new ArrayList<Integer>();
    this.activeRobotsID.add(1);
  }

  public ArrayList<Robot> getRobotsList() {
    return this.robots;
  }

  public ArrayList<Integer> getActiveRobotsIDList() {
    return this.activeRobotsID;
  }

  //move robots function
  public void moveRobots() {
    for (Robot robot : this.robots) {
      if (robot.getActive()) {
        robot.move();
      }
    }
  }

//rotate robots to left function
  public void rotateRobotsLeft() {
    for (Robot robot : this.robots) {
      if (robot.getActive()) {
        robot.rotateLeft();
      }
    }
  }

//rotate robots to right function
  public void rotateRobotsRight() {
    for (Robot robot : this.robots) {
      if (robot.getActive()) {
        robot.rotateRight();
      }
    }
  }

//print report out
  public void printReport() {
    System.out.println("\nOutput: There are " + this.robots.size() + " robots in total.\n");
    int i = 0;
    for (int ID : this.activeRobotsID) {
      if (i < this.activeRobotsID.size() - 1) {
        System.out.print("ROBOT " + ID + ", ");
      } else {
        System.out.print("ROBOT " + ID + " ");
      }
      i++;
    }
    System.out.print("is active.\n\n");
    for (int j = 0; j < this.robots.size(); j++) {
      System.out.print("ROBOT " + (j + 1) + ": ");
      this.robots.get(j).report();
    }
    System.out.println();
  }

}

class Main {

  public static boolean checkIsPlaceCommand(String command) {
    String[] commands = command.split("\\s+");
    if (commands[0].equalsIgnoreCase("PLACE")) {
      if (commands.length > 1) {
        String[] robotPositionCommands = commands[1].split(",");
        if (robotPositionCommands.length >= 3) {
          if (robotPositionCommands[0].matches("\\d+") && robotPositionCommands[1].matches("\\d+")
              && Directions.contains(robotPositionCommands[2])) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static boolean checkIsRobotActiveCommand(String command) {
    String[] commands = command.split("\\s+");
    if (commands[0].equalsIgnoreCase("ROBOT")) {
      if (commands.length > 1) {
        if (commands[1].matches("\\d+")) {
          return true;
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {

    System.out.println("Please place a robot with position and direction.");
    try (Scanner input = new Scanner(System.in)) {
      Table table = new Table();
      ArrayList<Robot> robotsList = table.getRobotsList();
      ArrayList<Integer> activeRobotsID = table.getActiveRobotsIDList();
      while (true) {
        String command = input.nextLine().trim();
        //check if userinput is place command
        if (checkIsPlaceCommand(command)) {
          String[] robotArgs = command.substring(6).split(",");
          int originX = Integer.parseInt(robotArgs[0]);
          int originY = Integer.parseInt(robotArgs[1]);
          //prevent user put robot out of table surface
          if (originX >= Table.POSITION_MIN && originX <= Table.POSITION_MAX && originY >= Table.POSITION_MIN
              && originY <= Table.POSITION_MAX) {
            Robot robot = new Robot();
            robot.setX(originX);
            robot.setY(originY);
            robot.setFacingDirection(Directions.valueOf(robotArgs[2].toUpperCase()));
            robot.setID(robotsList.size() + 1);
            robotsList.add(robot);
            // check if the robot is the first robot
            if (robotsList.size() == 1) {
              robotsList.get(0).setActive(true);
            }
            System.out.println("Place robot successfully!\n");
          } else {
            System.out.println("Place robot failed! Please put robot between " + Table.POSITION_MIN + " and "
                + Table.POSITION_MAX + ".\n");
          }
          //check if userinput is active robot command
        } else if (checkIsRobotActiveCommand(command)) {
          int robotID = Integer.parseInt(command.substring(6));
          //check if robotID is existed
          if (robotID > robotsList.size() || robotID == 0) {
            System.out.println("No such a robot.Please try another robot.\n");
          } else {
            //active robot if robot is not activated
            if (!activeRobotsID.contains(robotID)) {
              activeRobotsID.add(robotID);
              robotsList.get(robotID - 1).setActive(true);
              System.out.println("Robot " + robotID + " has been activated!\n");
            }

          }
        } else if (Commands.contains(command)) {
          if (robotsList.size() > 0) {
            switch (Commands.valueOf(command.toUpperCase())) {
              case MOVE:
                table.moveRobots();
                break;
              case RIGHT:
                table.rotateRobotsRight();
                break;
              case LEFT:
                table.rotateRobotsLeft();
                break;
              case REPORT:
                table.printReport();
                break;
              default:
                System.out.println("Please input a correct command.\n");
            }
          } else {
            System.out.println("Please place a robot first.\n");
          }

        } else {
          System.out.println("Please input a correct command.\n");
        }
      }
    } catch (Exception e) {

    }
  }
}
