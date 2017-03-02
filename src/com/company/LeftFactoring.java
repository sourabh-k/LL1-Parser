package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sourabh on 10-Feb-17.
 */
public class LeftFactoring {
    private Map<String,List<String>> map = new HashMap<String,List<String>>();
    private ArrayList<String> charList = new ArrayList<String>();
    public LeftFactoring(Map<String,List<String>> m,ArrayList<String> a)
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

    public  void  leftFactor()
    {
        int i,j,k,sum,ii;
        int res=0;
        int key=192;
        int end=1;
        while(true) {
            end = 1;
            for (i = 0; i < charList.size(); i++) {
                String ai = charList.get(i);
                ArrayList<String> tempProductionAi = new ArrayList<>();
                ArrayList<String> tempProductionAj = new ArrayList<>();
                //tempProductionAi = (ArrayList<String>) map.get(ai);
                List<String> productionAi = map.get(ai);
                StringBuilder sb = new StringBuilder();
                int flag = 1;
                j = 0;
                if (productionAi.size() == 1)
                    continue;
                int ex=1;
                while (ex==1) {
                    flag = 0;
                    Map<Character, Integer> m = new HashMap<>();
                    int max = 0;
                    char max_k = 'x';
                    for (ii = 0; ii < productionAi.size(); ii++) {
                        String current = productionAi.get(ii);
                        if (current.length() <= j)
                            flag++;
                        else {
                            char c = current.charAt(j);
                            if (m.containsKey(c)) {
                                int temp = m.get(c);
                                temp++;
                                m.remove(c);
                                m.put(c, temp);
                                if (temp > max) {
                                    max = temp;
                                    max_k = c;
                                }
                            } else {
                                m.put(c, 1);
                                if (max < 1) {
                                    max = 1;
                                    max_k = c;
                                    //System.out.println(max_k);
                                }
                            }
                        }
                    }
                    if (flag == productionAi.size()) {
                        break;
                    } else {
                        if (max > 1)
                            sb.append(max_k);
                        else
                            ex=0;
                        // System.out.println(max_k);
                        j++;
                    }
                }
               // System.out.println(sb.toString());
                if(sb.length()!=0)
                    end=0;
                int add = 1;
                int ises = 0;
                for (ii = 0; ii < productionAi.size() && sb.toString().length() != 0; ii++) {
                    String current = productionAi.get(ii);
                    String common = sb.toString();
                    int tempL = common.length();
                    if (current.length() >= tempL) {
                        if (current.substring(0, tempL).equals(common)) {
                            if (add == 1) {
                                String tempChar = (char) key + "";
                                tempProductionAi.add(common + tempChar);
                                add = 0;
                            }
                            if (current.length() > tempL)
                                tempProductionAj.add(current.substring(tempL, current.length()));
                            else {
                                ises = 1;
                            }
                        } else {
                            tempProductionAi.add(current);
                        }
                    } else {
                        tempProductionAi.add(current);
                    }
                }
                if (add == 0) {
                    String tempChar = (char) key + "";
                    map.remove(ai);
                    map.put(ai, tempProductionAi);
                    map.put(tempChar, tempProductionAj);
                    charList.add(tempChar);
                    if (ises == 1)
                        tempProductionAj.add("e");
                    end=0;

                }
                key++;
            }
            if(end==1)
                break;
        }
        System.out.println("Result left factoring");
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
    }
}
