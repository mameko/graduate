package assign3;
/*****************************************************************
 * Memorial University of Newfoundland<br>
 * 7894 Concurrent Programming<br>
 * Assignment 3, Question 1 - Chopstick class
 *
 * @author Dennis Peters ($Author: dpeters $)
 * @version $Revision: 1173 $ $Date: 2010-06-27 23:02:11 -0230 (Sun, 27 Jun 2010) $
 * 
 * $Id: Chopstick.java 1173 2010-06-28 01:32:11Z dpeters $
 ****************************************************************/
class Chopstick {
  boolean inUse;

  Chopstick()
  {
    inUse = false;
  }

  public void up()
  {
    if (inUse) {
      System.out.println("ERROR: chopstick already up.");
    }
    inUse = true;
  }

  public void down()
  {
    if (!inUse) {
      System.out.println("ERROR: chopstick already down.");
    }
    inUse = false;
  }
}

