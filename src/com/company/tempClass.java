package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sourabh on 21-Feb-17.
 */
public class tempClass {

    private Map<String,List<String>> map = new HashMap<String,List<String>>();
    private ArrayList<String> charList = new ArrayList<String>();
    public tempClass(Map<String,List<String>> m,ArrayList<String> a)
    {
        map =m;
        charList = a;
    }

    public Map<String, List<String>> getMap() {
        return map;
    }

    public ArrayList<String> getCharList() {
        return charList;
    }
    public  void firstSet() {
        Map<String, ArrayList<String>> m = new HashMap<>();
        int i, j, k, sum, n;
        for (i = 0; i < charList.size(); i++) {
            String ai = charList.get(i);
            List<String> productionAi = map.get(ai);
            ArrayList<String> tempFirst = new ArrayList<>();
            for (j = 0; j < productionAi.size(); j++) {
                // if(productionAi.get(j).charAt(0) >='a' && productionAi.get(j).charAt(0) <='z')
                if (!charList.contains(productionAi.get(j).charAt(0) + "")) {
                    tempFirst.add(productionAi.get(j).charAt(0) + "");
                }
            }
            m.put(ai, tempFirst);
        }
        ArrayList<Integer> pending = new ArrayList<>();
        while (true) {
            int flag = 1;
            for (i = 0; i < charList.size(); i++) {
                String ai = charList.get(i);
                List<String> productionAi = map.get(ai);
                for (j = 0; j < productionAi.size(); j++) {
                    int con = 0;
                    while (con >= 0 && con < productionAi.get(j).length()) {
                        char start = productionAi.get(j).charAt(con);
                        int tempCon = con;
                        if (!(start >= 'a' && start <= 'z'))
                        //if(charList.contains(start+""))
                        {
                            String tempString = start + "";
                            if (m.containsKey(tempString)) {
                                ArrayList<String> firstAj = m.get(tempString);
                                ArrayList<String> firstAi = m.get(ai);
                                for (k = 0; k < firstAj.size(); k++) {
                                    if (firstAj.contains("e")) {
                                        tempCon++;
                                        if (con == productionAi.get(j).length() - 1) {
                                            if (!firstAi.contains("e")) {
                                                firstAi.add("e");
                                                flag = 0;
                                            }
                                        }
                                    }
                                    if (!firstAi.contains(firstAj.get(k))) {
                                        if (!firstAj.get(k).equals("e")) {
                                            firstAi.add(firstAj.get(k));
                                            flag = 0;
                                        }

                                       /*if(firstAj.get(k).equals("e"))
                                       {
                                           tempCon++;
                                       }*/

                                    }
                                }
                                m.replace(ai, firstAi);
                            }
                        } else {
                            ArrayList<String> firstAi = m.get(ai);
                            if (!firstAi.contains(start + "")) {
                                firstAi.add(start + "");
                                m.replace(ai, firstAi);
                                flag = 0;
                            }

                        }
                        if (tempCon > con) {
                            con++;
                        } else
                            con = -1;
                    }

                }
            }
            if (flag == 1)
                break;
        }
        for (i = 0; i < charList.size(); i++) {
            System.out.print("First(" + charList.get(i) + ") = ");
            System.out.println(m.get(charList.get(i)));
        }

        // Follow ahead be cautious :)
        Map<String, ArrayList<String>> follow = new HashMap<>();
        ArrayList<String> temp = new ArrayList<>();
        temp.add("$");
        follow.put(charList.get(0), temp);
        int itr = 1;
        itr = 0;
        for (i = 0; i < charList.size(); i++) {
            String ai = charList.get(i);
            List<String> productionAi = map.get(ai);
            for (j = 0; j < productionAi.size(); j++) {
                String current = productionAi.get(j);
                for (k = 0; k < current.length() - 1; k++) {
                    String tempChar = current.charAt(k) + "";
                    if (charList.contains(tempChar)) {
                        String pass = current.substring(k+1,current.length());
                        ArrayList<String> res = utility(pass);
                        if (follow.containsKey(tempChar)) {
                            ArrayList<String> cat = follow.get(tempChar);
                            for (int ii = 0; ii < res.size(); ii++) {
                                if (!res.get(ii).equals("e") && !cat.contains(res.get(ii)))
                                    cat.add(res.get(ii));
                            }
                            follow.replace(tempChar, cat);
                        } else {
                            ArrayList<String> cat = new ArrayList<>();
                            for (int ii = 0; ii < res.size(); ii++) {
                                if (!res.get(ii).equals("e") && !cat.contains(res.get(ii)))
                                    cat.add(res.get(ii));
                            }
                            follow.put(tempChar, cat);
                        }
                    }
                }
            }
        }
        int flag;
        //System.out.println(follow.values());
        while (true)
        {
            flag=1;
            for(i=0;i<charList.size();i++)
            {
                String ai = charList.get(i);
                List<String> productionAi = map.get(ai);
                for(j=0;j<productionAi.size();j++)
                {
                    String current = productionAi.get(j);
                    for(k=0;k<current.length()-1;k++)
                    {
                        String pass= current.substring(k+1,current.length());
                        ArrayList<String> res= utility(pass);

                        if(res.contains("e"))
                        {
                            ArrayList<String> followB = follow.get(current.charAt(k)+"") ;
                            ArrayList<String> followA = follow.get(ai);
                            if(followA==null)
                                followA = new ArrayList<>();
                            if(followB == null)
                                followB = new ArrayList<>();
                            for(int ii =0;ii<followA.size();ii++)
                            {
                                if(!followB.contains(followA.get(ii)) && !followB.contains(followA.get(ii)))
                                {
                                    followB.add(followA.get(ii));
                                    flag=0;
                                }
                            }
                            follow.replace(current.charAt(k)+"",followB);
                        }
                    }
                    if(charList.contains(current.charAt(current.length()-1)+""))
                    {
                        String aj = current.charAt(current.length()-1)+"";
                        ArrayList<String> followB =follow.get(current.charAt(current.length()-1)+"");
                        ArrayList<String> followA = follow.get(ai);
                        if(followA== null)
                            followA = new ArrayList<>();
                        if(followB == null)
                            followB  = new ArrayList<>();
                        for(int ii =0;ii<followA.size();ii++)
                        {
                            if(!followB.contains(followA.get(ii)) && !followB.contains(followA.get(ii))) {
                                followB.add(followA.get(ii));
                                flag = 0;
                            }

                        }
                        if (follow.containsKey(aj))
                            follow.replace(ai,followB);
                        else
                            follow.put(ai,followB);

                    }
                }
            }
            if(flag==1)
                break;
        }
        for(i=0;i<charList.size();i++)
        {
            System.out.print("Follow("+charList.get(i)+") = ");
            System.out.println(follow.get(charList.get(i)));
        }
    }
    ArrayList<String> utility(String pr)
    {
        Map<String,List<String>> map = getMap();
        ArrayList<String> charList = (ArrayList<String>) getCharList().clone();
        String tempChar = (char)223+"";
        charList.add(tempChar);
        ArrayList<String> trm= new ArrayList<>();
        trm.add(pr);
        map.put(tempChar,trm);
        Map<String, ArrayList<String>> m = new HashMap<>();
        int i, j, k, sum, n;
        for (i = 0; i < charList.size(); i++) {
            String ai = charList.get(i);
            List<String> productionAi = map.get(ai);
            ArrayList<String> tempFirst = new ArrayList<>();
            for (j = 0; j < productionAi.size(); j++) {
                // if(productionAi.get(j).charAt(0) >='a' && productionAi.get(j).charAt(0) <='z')
                if (!charList.contains(productionAi.get(j).charAt(0) + "")) {
                    tempFirst.add(productionAi.get(j).charAt(0) + "");
                }
            }
            m.put(ai, tempFirst);
        }
        ArrayList<Integer> pending = new ArrayList<>();
        while (true) {
            int flag = 1;
            for (i = 0; i < charList.size(); i++) {
                String ai = charList.get(i);
                List<String> productionAi = map.get(ai);
                for (j = 0; j < productionAi.size(); j++) {
                    int con = 0;
                    while (con >= 0 && con < productionAi.get(j).length()) {
                        char start = productionAi.get(j).charAt(con);
                        int tempCon = con;
                        if (!(start >= 'a' && start <= 'z'))
                        //if(charList.contains(start+""))
                        {
                            String tempString = start + "";
                            if (m.containsKey(tempString)) {
                                ArrayList<String> firstAj = m.get(tempString);
                                ArrayList<String> firstAi = m.get(ai);
                                for (k = 0; k < firstAj.size(); k++) {
                                    if (firstAj.contains("e")) {
                                        tempCon++;
                                        if (con == productionAi.get(j).length() - 1) {
                                            if (!firstAi.contains("e")) {
                                                firstAi.add("e");
                                                flag = 0;
                                            }
                                        }
                                    }
                                    if (!firstAi.contains(firstAj.get(k))) {
                                        if (!firstAj.get(k).equals("e")) {
                                            firstAi.add(firstAj.get(k));
                                            flag = 0;
                                        }

                                       /*if(firstAj.get(k).equals("e"))
                                       {
                                           tempCon++;
                                       }*/

                                    }
                                }
                                m.replace(ai, firstAi);
                            }
                        } else {
                            ArrayList<String> firstAi = m.get(ai);
                            if (!firstAi.contains(start + "")) {
                                firstAi.add(start + "");
                                m.replace(ai, firstAi);
                                flag = 0;
                            }

                        }
                        if (tempCon > con) {
                            con++;
                        } else
                            con = -1;
                    }

                }
            }
            if (flag == 1)
                break;
        }

        return m.get(tempChar);
    }
}

