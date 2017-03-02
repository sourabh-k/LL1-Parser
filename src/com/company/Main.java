package com.company;

import java.io.*;
import java.util.*;

import static javafx.application.Platform.exit;
import static javafx.application.Platform.isImplicitExit;

/**
 * Created by Sourabh on 8-Feb-17.
 * Main is oriented to remove left recursion from the productions both immediate and non-immediate
 * other classes does operations similar as of their name and are invoked by main when required.
 */

public class Main {

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        int i,j,k,n;
        ArrayList<String> production =new ArrayList<String>();
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        ArrayList<String> charList = new ArrayList<String>();
       // System.out.println("Enter the number of production");
       // Scanner scanner =new Scanner("input.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        n=0;
        if (scanner != null) {
            n= scanner.nextInt();
        }
        for(i=0;i<n;i++)
        {
            production.add(scanner.next());
            ArrayList<String> tempProduction = new ArrayList<String>();
            String current = production.get(i);
            String c= current.charAt(0)+"";
            charList.add(c);
            StringBuilder stringBuilder = new StringBuilder();
            j=3;
            while(j<current.length())
            {
                if(current.charAt(j) == '|')
                {
                    tempProduction.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
                else
                {
                    stringBuilder.append(current.charAt(j));
                }
                j++;
            }
            tempProduction.add(stringBuilder.toString());
            map.put(c,tempProduction);

        }
        LeftFactoring Lf = new LeftFactoring(map,charList);
        Lf.leftFactor();
        //System.out.println("return");
        map = Lf.getMap();
        charList = Lf.getCharList();
        //Non immediate
       for(i=0;i<charList.size();i++)
       {
           String ai = charList.get(i);
         // System.out.println("ai "+ai);
           for (j = 0; j < i; j++)
           {
               String aj = charList.get(j);
              // System.out.println("aj " + aj);
               List<String> tempProductionAi = map.get(ai);
               int len= tempProductionAi.size();
               List<String> temp = new ArrayList<>();
               List<String> chk =map.get(aj+"");
               int move=0;
               for(k=0;k<chk.size();k++)
               {
                   if(ai.equals(chk.get(k).charAt(0)+""))
                   {
                       move=1;
                   }
               }
               if(move==1) {
                   for (k = 0; k < len; k++) {
                  /* System.out.println("temp"+tempProductionAi.get(k));*/
                       if (aj.equals(tempProductionAi.get(k).charAt(0) + "")) {
                           String gama = tempProductionAi.get(k);
                           gama = gama.substring(1, gama.length());
                           List<String> tempProductionAj = map.get(aj);
                           temp.add(tempProductionAj.get(0) + gama);
                           for (int ii = 1; ii < tempProductionAj.size(); ii++) {
                               temp.add(tempProductionAj.get(ii) + gama);
                           }
                       } else {
                           temp.add(tempProductionAi.get(k));
                       }
                   }
                   map.remove(ai);
                   map.put(ai, temp);
               }
           }
       }
       int len= charList.size();
       int key=200;
       for(i=0;i<len;i++)
       {
           ArrayList<String> beta = new ArrayList<>();
           ArrayList<String> alpha = new ArrayList<>();
           char o;
           String ai = charList.get(i);
           List<String> productionAi = map.get(ai);
           for(j=0;j<productionAi.size();j++)
           {
               String temp = productionAi.get(j);
               if(ai.equals(temp.charAt(0)+""))
               {
                   String tempKey = (char)key+"";
                     alpha.add(temp.substring(1,temp.length())+ tempKey);
               }
               else
               {
                   String tempKey = (char)key+"";
                   if(temp.equals("e"))
                       temp="";
                   beta.add(temp+tempKey);
               }

           }
           if(beta.size() <productionAi.size())
           {
               String tempKey = (char)key+"";
               alpha.add("e");
               charList.add(tempKey);
               map.put(tempKey,alpha);
               map.remove(ai);
               if(beta.size()==0)
                   beta.add(tempKey);
               map.put(ai,beta);
           }
           key++;
       }
       System.out.println("Result after removing Left Recursion");
        for(i=0;i<charList.size();i++)
        {
            System.out.print(charList.get(i)+"-> ");
            List<String> out = map.get(charList.get(i));
            for(j=0;j< out.size();j++)
            {
                System.out.print(out.get(j));
                if(j<out.size()-1)
                    System.out.print(" | ");
            }
            System.out.println();
        }
        Utility.charList = charList;
        Utility.map = map;
        Utility.initialiseTerminals();
        FirstAndFollowSet Fs = new FirstAndFollowSet(map,charList);
        Fs.firstAndFollowSet();
        Map<String, ArrayList<String>> first = new HashMap<>();                                 // First Set
        Map<String, ArrayList<String>> follow = new HashMap<>();                                // Follow Set
        first = Fs.getFirst();
        follow = Fs.getFollow();
        System.out.println("First Set for each non terminal");
        for (i = 0; i < charList.size(); i++) {
            System.out.print("First(" + charList.get(i) + ") = ");
            System.out.println(first.get(charList.get(i)));
        }
        System.out.println("Follow Set for each non terminal");
        for(i=0;i<charList.size();i++)
        {
            System.out.print("Follow("+charList.get(i)+") = ");
            System.out.println(follow.get(charList.get(i)));
        }

        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Want to parse string (Y/N)?");
        String  move = scanner1.next();
        if (move.equals("N"))
            isImplicitExit();
        LL1Check ll1Check = new LL1Check(charList,map);
        if(ll1Check.isLL1())
        {
            System.out.println("Grammer is LL1");
            System.out.println("Enter input string");
            String string = scanner1.next();
            PredictiveParser predictiveParser = new PredictiveParser(charList,map);
            predictiveParser.computeParseTable();
            System.out.println(predictiveParser.isParseAble(string));
        }
        else
        {
            System.out.println("Grammer is not LL1");
        }

    }
}
