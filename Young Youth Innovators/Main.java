import java.util.Scanner;
import java.util.*;
import java.io.*;
class Main {
   public static void main(String[] args) {
      Scanner myObj = new Scanner(System.in);
      System.out.println("Enter first text file without .txt");
      String fileName1 = myObj.nextLine();
      System.out.println("Enter second text file without .txt");
      String fileName2 = myObj.nextLine();
      myObj.close();
      String file1 = fileName1+".txt";
      String file2 = fileName2+".txt";
      PlagiarizeCheck pc = new PlagiarizeCheck(file1, file2);
      double percent = Math.round(pc.compareFiles()*100.0);
      System.out.print(percent + "% match");
      if(percent<25.0)
      {
         System.out.print(": no plagiarism detected");
      }
      if(percent>=25.0 && percent<50.0)
      {
         System.out.print(": likely chance of plagiarism");
      }
      if(percent >= 50.0)
      {
         System.out.print(": plagiarism detected");
      }
   }
}
class PlagiarizeCheck
{
   private static ArrayList<String> ogFile = new ArrayList<>();
   private static ArrayList<String> plagiarizeFile = new ArrayList<>();
   int plagCount = 0;

   public PlagiarizeCheck(String file1, String file2) 
   {
      Scanner inputFile = null;
      try
      {
         inputFile = new Scanner(new File(file1));
      }
      catch (Exception e)
      {
         return;
      }   
    
      while(inputFile.hasNextLine())
         ogFile.add(inputFile.nextLine());
   
      Scanner inFile = null;
      try
      {
         inFile = new Scanner(new File(file2));
      }
      catch (Exception e)
      {
         return;
      }
      while(inFile.hasNextLine())
         plagiarizeFile.add(inFile.nextLine());
    /*make scanner and add lines to arraylist*/
   }
   
   public double compareFiles()//compares if lines are similar overall
   {
      ArrayList<String> compared = new ArrayList<>();
      for(String s : ogFile)
      {
         for(String k: plagiarizeFile)
         {
            if(compareLines(s,k)>=0.30)
            {
               if(!compared.contains(k))
               {
                  plagCount++;
                  compared.add(k);
               }
            }
         }
      }
      double oSize = ogFile.size();
      double percent = plagCount/oSize;
      return percent;
   }
   
   public double compareLines(String og, String p)
   {
      String[] ogCop = og.trim().split(" ");
      String[] pCop = p.trim().split(" ");
      ArrayList<String> ogAl = new ArrayList<>();
      ArrayList<String> pAl = new ArrayList<>();
      for(String s : ogCop)
         ogAl.add(s);
      for(String s : pCop)
         pAl.add(s);
      int simCount = 0;
      boolean inQuote = false;
      ArrayList mcw = commonWords();
      for(int i = 0; i<ogAl.size(); i++)
      {
         if(i<pAl.size())
         {
            if(!mcw.contains(ogAl.get(i)))
            {
               if(inQuote==true && ogAl.get(i).substring(0,1).equals("\""))
               {
                  inQuote = false;
               }
               if(inQuote==false && ogAl.get(i).substring(0,1).equals("\""))
               {
                  inQuote = true;
               }
               if(inQuote == true)
               {
                  continue;
               }
               if(pAl.contains(ogAl.get(i)))
               {
                  simCount++;
               }
            }
         } 
      }
      double oSize = ogAl.size();
      double sCount = simCount;
      double percent = (sCount/oSize);
      return percent;
    
   }
   public ArrayList<String> commonWords()
   {
    
      Scanner inFile = null;
      try
      {
         inFile = new Scanner(new File("mcw.txt"));
      }
      catch (Exception e)
      {
         return null;
      }
      ArrayList<String> thing = new ArrayList<String>();
      while(inFile.hasNextLine())
         thing.add(inFile.nextLine());
      return thing;
   }
}