package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sourabh on 26-Feb-17.
 */
public class Utility {

     static Map<String,List<String>> map = new HashMap<String,List<String>>();
     static ArrayList<String> charList = new ArrayList<String>();
     static Map<String, ArrayList<String>> m = new HashMap<>();
     static Map<String, ArrayList<String>> follow = new HashMap<>();
    static ArrayList<String> firstSet(String pr)
    {
        ArrayList<String> tempFirst = new ArrayList<>();
        if(charList.contains(pr.charAt(0)+""))
        {
            int i,j,k,sum;
            i=0;
            int flag=1;
            while(i<pr.length() && charList.contains(pr.charAt(i)+"") && flag==1)
            {
                char current = pr.charAt(i);
                ArrayList<String> first = m.get(current+"");
                flag=0;
                for(j=0;j<first.size();j++)
                {
                    if(!first.get(j).equals("e"))
                        tempFirst.add(first.get(j));
                    else
                        flag=1;
                }
                i++;
            }
            if(i == pr.length() && flag==1)
                tempFirst.add("e");
            else if(flag==1)
            {
                tempFirst.add(pr.charAt(i)+"");
            }

        }
        else
        {
            tempFirst.add(pr.charAt(0)+"");
        }
        return  tempFirst;
    }

    static ArrayList<String> followSet(String variable)
    {
        return follow.get(variable);
    }
}
