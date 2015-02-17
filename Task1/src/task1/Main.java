package task1;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            new Main().solve();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void solve() throws IOException, InterruptedException {
        ArrayList <Juice> juices = new ArrayList();
        ArrayList <ArrayList <String>> data = new ArrayList();
        data = input("juice.in");
        juices = getJuices(data);
        ArrayList <String> components = new ArrayList();
        components = getComponents(data);
        printComponents(components, "juice1.out");
        TreeMap <String, Integer> componentsAmount = new TreeMap();
        componentsAmount = getComponentsAmount(components);
        components = getUniqueComponents(components);
        printComponents(components, "juice2.out");
        int n = washAmount(juices, componentsAmount);
        printAmount(n, "juice3.out");
    }
    private ArrayList <ArrayList <String>> input(String name) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(name));
        String temp = in.readLine();
        StringTokenizer sT;
        ArrayList <ArrayList <String>> data = new ArrayList();
        while (temp != null)
        {
            sT = new StringTokenizer(temp);
            int n = sT.countTokens();
            ArrayList <String> j = new ArrayList();
            for (int i = 0; i < n; i++)
            {
                String s = sT.nextToken();
                j.add(s);
            }
            data.add(j);
            temp = in.readLine();
        }
        in.close();
        return data;
    }
    private ArrayList<Juice> getJuices(ArrayList <ArrayList <String>> data) {
        ArrayList <Juice> juices = new ArrayList();
        Iterator <ArrayList <String>> it = data.iterator();
        while (it.hasNext())
        {
            Juice j = new Juice();
            ArrayList <String> al = it.next();
            Iterator <String> iter = al.iterator();
            while (iter.hasNext())
                j.components.add(iter.next());
            juices.add(j);
        }
        return juices;
    }
    private ArrayList <String> getComponents (ArrayList <ArrayList <String>> data) {
        ArrayList <String> components = new ArrayList();
        Iterator <ArrayList <String>> it = data.iterator();
        while (it.hasNext())
        {
            ArrayList <String> j = it.next();
            Iterator <String> iter = j.iterator();
            while (iter.hasNext())
                components.add(iter.next());
        }
        return components;
    }
    private void printComponents(ArrayList <String> components, String name) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(name));
        Iterator <String> iter = components.iterator();
        while (iter.hasNext())
            out.println(iter.next());
        out.flush();
        out.close();
    }
    private ArrayList <String> getUniqueComponents (ArrayList <String> components) throws InterruptedException {
        SortThread st = new SortThread(components);
        st.start();
        st.join();
        int n = components.size();
        for (int i = 0; i < n - 1; i++)
        {
            if (components.get(i).compareTo(components.get(i + 1)) == 0)
            {
                components.remove(i);
                i--;
                n--;
            }
        }
        return components;
    }
    private TreeMap <String, Integer> getComponentsAmount(ArrayList <String> components) {
        TreeMap <String, Integer> componentsAmount = new TreeMap();
        Integer n;
        for (String str : components)
        {
            n = componentsAmount.get(str);
            if (n == null)
                componentsAmount.put(str, 1);
            else
                componentsAmount.put(str, n + 1);
        }
        return componentsAmount;
    }
    private int minAmount (Juice j, TreeMap <String, Integer> count) {
        int min = 2147483647;
        for (String component : j.components)
        {
            if (count.get(component) < min)
                min = count.get(component);
        }
        return min;
    }
    private void sortCount (ArrayList <Juice> juices, final TreeMap <String, Integer> count) {
        Collections.sort(juices, new Comparator <Juice> () {
                @Override
                public int compare(Juice j1, Juice j2) {
                    if (j1.components.size() > j2.components.size())
                        return 1;
                    else if (j1.components.size() < j2.components.size())
                        return -1;
                    else if (minAmount(j1, count) < minAmount(j2, count))
                        return -1;
                    else
                        return 1;
                }
            });
    }
    private boolean isSubset (TreeSet <String> test, TreeSet <String> set) {
        return set.containsAll(test);
    }
    private ArrayList <Juice> getUnique (ArrayList <Juice> juices) {
        int n = juices.size();
        for (int i = 0; i < n - 1; i++)
        {
            if (juices.get(i).components.size() == juices.get(i + 1).components.size())
                if (juices.get(i).components.equals(juices.get(i + 1).components))
                {
                    juices.remove(i);
                    i--;
                    n--;
                }
        }
        return juices;
    }
    private int washAmount(ArrayList <Juice> juices, TreeMap <String, Integer> componentsAmount) {
        int amount = 0;
        sortCount(juices, componentsAmount);
        juices = getUnique(juices);
        TreeSet <String> current = new TreeSet();
        int n = juices.size();
        while (!juices.isEmpty())
        {
            current = juices.get(0).components;
            juices.remove(0);
            n--;
            for (int i = 0; i < n; i++)
            {
                if (isSubset(current, juices.get(i).components))
                {
                    current = juices.get(i).components;
                    juices.remove(i);
                    n--;
                    i--;
                }
            }
            amount++;
        }
        return amount;
    }
    private void printAmount(int n, String name) throws IOException{
        PrintWriter out = new PrintWriter (new FileWriter (name));
        out.println(n);
        out.flush();
        out.close();
    }
}
