package com.company;

import java.util.*;

/**
 * Created by Sourabh on 27-Feb-17.
 */
public class PredictiveParser {

    private HashMap<String,HashMap<String,String>> table = new HashMap<>();
    private Map<String,List<String>> map = new HashMap<String,List<String>>();
    private ArrayList<String> charList = new ArrayList<String>();

    public PredictiveParser(ArrayList<String> charList, Map<String, List<String>> map) {
        this.map = map;
        this.charList = charList;
    }
    void computeParseTable()
    {
        int i,j,k,l,m,n,sum,res;
        for(i=0;i<charList.size();i++)
        {
            String currentAi = charList.get(i);
            List<String> productionAi = map.get(currentAi);
            for (j = 0; j < productionAi.size(); j++)
            {
                HashMap<String,String> tempArray = new HashMap<>();
                if(table.containsKey(currentAi))
                    tempArray = table.get(currentAi);
                String currentClause = productionAi.get(j);
                ArrayList<String> firstSet = Utility.firstSet(currentClause);
                for (k=0;k<firstSet.size();k++)
                {
                    if(!firstSet.get(k).equals("e"))
                    tempArray.put(firstSet.get(k),currentClause);
                }
                if (firstSet.contains("e"))
                {
                    ArrayList<String> followSet = Utility.followSet(currentAi);
                    for(k=0;k<followSet.size();k++)
                    {
                        if(!charList.contains(followSet.get(k)))
                        {
                            tempArray.put(followSet.get(k),currentClause);
                        }
                    }
                }
                table.put(currentAi,tempArray);
            }
            System.out.print(currentAi+"");
            System.out.println(table.get(currentAi));
        }

    }

    boolean isParseAble(String input)
    {
        int i,j,k,l,m,sum,res;
        boolean out =false;
        Stack<String> stack = new Stack<>();
        stack.push("$");
        stack.push(charList.get(0));
        input = input+"$";
        i=0;
        while (i<input.length())
        {
            String current = input.charAt(i)+"";
            String topStack = stack.peek();
            if(topStack.equals("$") && current.equals("$"))
            {
                out = true;
                break;
            }
            else if(topStack.equals(current))
            {
                stack.pop();
                i++;
            }
            else
            {
                if(table.containsKey(topStack))
                {
                    if(table.get(topStack).containsKey(current))
                    {
                        String tempProduction = table.get(topStack).get(current);
                        StringBuilder stringBuilder = new StringBuilder(tempProduction);
                        stringBuilder =stringBuilder.reverse();
                        tempProduction = stringBuilder.toString();
                        stack.pop();
                        if(!tempProduction.equals("e"))
                        {
                            for (j=0;j<tempProduction.length();j++)
                            {
                                //stack.add(tempProduction);
                                stack.push(tempProduction.charAt(j)+"");
                            }
                        }
                    }
                    else
                    {
                        out = false;
                        break;
                    }
                }
                else
                {
                    out = false;
                    break;
                }
            }


            System.out.print(stack);
            System.out.println("\t"+input);
        }
        return out;
    }
}