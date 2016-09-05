package assign3;
/*****************************************************************
 * Memorial University of Newfoundland<br>
 * 7894 Concurrent Programming<br>
 * Assignment 3, Question 1 - Philosopher class
 *
 * @author Dennis Peters ($Author: dpeters $)
 * @version $Revision: 1173 $ $Date: 2010-06-27 23:02:11 -0230 (Sun, 27 Jun 2010) $
 * 
 * $Id: Philosopher.java 1173 2010-06-28 01:32:11Z dpeters $
 ****************************************************************/
class Philosopher implements Runnable {
  int seat;        // position at the table 0 <= seat < n.
  Assign3_Waiter waiter;   // Controls access to the chopsticks.
  Chopstick left;  // The chopstick to my left 
  Chopstick right; // The chopstick to my right

  Philosopher(int s, Assign3_Waiter josh, Chopstick l, Chopstick r)
  {
    seat = s;
    waiter = josh; // Hi I'm Josh, and I'll be your server tonight
    left = l;
    right = r;
  }

  public void run() 
  {
    try {
      while (true) {
        Thread.sleep(Math.round(Math.random()*1000)); // Sleep some time
        waiter.request(seat); // Request permission to eat
        left.up();            // Pick up the chopsticks
        right.up();
        eat();                // Eat
        left.down();          // Put down the chopsticks
        right.down();
        waiter.done(seat);    // Inform the waiter that I'm done for now.
      }
    }
    catch (InterruptedException e) { } // Simply exit.
  }

  private void eat()
    throws InterruptedException
  {
    Thread.sleep(Math.round(Math.random()*1000)); // Sleep some time
  }
}

