package com.company;

import java.util.*;

/**
 * Created by Sourabh on 22-Feb-17.
 */
public class LL1Check {
    private ArrayList<String> charList = new ArrayList<>();
    private Map<String,List<String>> map = new HashMap<>();
    public LL1Check(ArrayList<String> list,Map<String,List<String>> mapping)
    {
        charList = list;
        map = mapping;
    }

    boolean isLL1()
    {
        int i,j,k,l,m,x,y,sum;
        boolean res = true;
        System.out.println(Utility.firstSet("ET"));
        System.out.println(Utility.followSet("E"));
        for(i=0;i<charList.size();i++)
        {
            String currentVariable = charList.get(i);
            List<String> productionAi = map.get(currentVariable);
            for(j=0;j<productionAi.size();j++)
            {
                String alphaClause = productionAi.get(j);
                ArrayList<String> firstAlpha = Utility.firstSet(alphaClause);
                HashSet<String> set = new HashSet<String>();
                set.addAll(firstAlpha);
                for(k=0;k<productionAi.size() && k!=j;k++)
                {
                    String betaClause = productionAi.get(k);
                    ArrayList<String> firstBeta = Utility.firstSet(betaClause);
                    for(x=0;x<firstBeta.size();x++)
                    {
                        if(set.contains(firstBeta.get(x)))
                        {
                            res =false;
                            break;
                        }
                    }
                    if(firstAlpha.contains("e"))
                    {
                        ArrayList<String> followSet = Utility.followSet(currentVariable);
                        HashSet<String> set1 = new HashSet<>();
                        set1.addAll(followSet);
                        for(x=0;x<firstBeta.size();x++)
                        {
                            if(set1.contains(firstBeta.get(x)))
                            {
                                res=false;
                                break;
                            }
                        }
                    }
                    if(firstBeta.contains("e"))
                    {
                        ArrayList<String> followSet = Utility.followSet(currentVariable);
                        HashSet<String> set1 = new HashSet<>();
                        set1.addAll(followSet);
                        for(x=0;x<firstAlpha.size();x++)
                        {
                            if(set1.contains(firstBeta.get(x)))
                            {
                                res=false;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return  res;
    }

}
