// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

  public static Results Run(int runtime, Vector processVector, Results result) {
    Comparator comparator = new Comparator() {
      @Override
      public int compare(Object o1, Object o2) {
        int t = (((sProcess)o1).cputime - ((sProcess)o1).cpudone) - (((sProcess)o2).cputime - ((sProcess)o2).cpudone);
        return t ;
      }
    };

    // Check sorting
    //    for (int i = 0; i<processVector.size(); i++){
//      System.out.println(((sProcess)processVector.get(i)).cputime-
//              ((sProcess)processVector.get(i)).cpudone);
//    }
//    System.out.println();
//    Collections.sort(processVector, comparator);
//    for (int i = 0; i<processVector.size(); i++){
//      System.out.println(((sProcess)processVector.get(i)).cputime-
//              ((sProcess)processVector.get(i)).cpudone);
//    }
//    System.out.println();
    //

    int i = 0;
    int comptime = 0;
    int currentProcess = 0;
    int previousProcess = 0;
    int size = processVector.size();
    int completed = 0;
    String resultsFile = "Summary-Processes";
    result.schedulingType = "Batch (Nonpreemptive)";
    result.schedulingName = "Shortest Remaining Time First ";
    try {

      //BufferedWriter out = new BufferedWriter(new FileWriter(resultsFile));
      //OutputStream out = new FileOutputStream(resultsFile);
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
      sProcess process = (sProcess) processVector.elementAt(currentProcess);
      out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
      while (comptime < runtime) {
        Collections.sort(processVector, comparator);
        if (process.cpudone == process.cputime) {
          completed++;
          out.println("Process: " + currentProcess + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
          if (completed == size) {
            result.compuTime = comptime;
            out.close();
            return result;
          }
          for (i = size - 1; i >= 0; i--) {
            process = (sProcess) processVector.elementAt(i);
            if (process.cpudone < process.cputime) { 
              currentProcess = i;
            }
          }
          process = (sProcess) processVector.elementAt(currentProcess);
          out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
        }      
        if (process.ioblocking == process.ionext) {
          out.println("Process: " + currentProcess + " I/O blocked... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
          process.numblocked++;
          process.ionext = 0; 
          previousProcess = currentProcess;
          for (i = size - 1; i >= 0; i--) {
            process = (sProcess) processVector.elementAt(i);
            if (process.cpudone < process.cputime && previousProcess != i) {
              currentProcess = i;
            }
          }
          process = (sProcess) processVector.elementAt(currentProcess);
          out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
        }
        process.cpudone++;
        if (process.ioblocking > 0) {
          process.ionext++;
        }
        comptime++;
      }
      out.close();
    } catch (IOException e) { /* Handle exceptions */ }
    result.compuTime = comptime;
    return result;
  }
}
