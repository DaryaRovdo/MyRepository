package task1;
import java.util.*;

public class Solving {
    public ArrayList<Juice> getJuices(ArrayList <ArrayList <String>> data) {
        ArrayList <Juice> juices = new ArrayList();
        Iterator <ArrayList <String>> it = data.iterator();
        while (it.hasNext())
        {
            Juice j = new Juice();
            ArrayList <String> al = it.next();
            Iterator <String> iter = al.iterator();
            while (iter.hasNext())
            {
                j.get().add(iter.next());
            }
            juices.add(j);
        }
        return juices;
    }

    public ArrayList <String> getComponents (ArrayList <ArrayList <String>> data) {
        ArrayList <String> components = new ArrayList();
        Iterator <ArrayList <String>> it = data.iterator();
        while (it.hasNext())
        {
            ArrayList <String> j = it.next();
            Iterator <String> iter = j.iterator();
            while (iter.hasNext())
            {
                components.add(iter.next());
            }
        }
        return components;
    }

    public ArrayList <String> getUniqueComponents (ArrayList <String> components) throws InterruptedException {
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

    public TreeMap <String, Integer> getComponentsAmount(ArrayList <String> components) {
        TreeMap <String, Integer> componentsAmount = new TreeMap();
        Integer n;
        for (String str : components)
        {
            n = componentsAmount.get(str);
            if (n == null)
            {
                componentsAmount.put(str, 1);
            }
            else
            {
                componentsAmount.put(str, n + 1);
            }
        }
        return componentsAmount;
    }

    private int minAmount (Juice j, TreeMap <String, Integer> count) {
        int min = Integer.MAX_VALUE;
        for (String component : j.get())
        {
            if (count.get(component) < min)
            {
                min = count.get(component);
            }
        }
        return min;
    }

    private void sortCount (ArrayList <Juice> juices, final TreeMap <String, Integer> count) {
        Collections.sort(juices, new Comparator <Juice> () {
                @Override
                public int compare(Juice a, Juice b) {
                    if (a.get().size() > b.get().size())
                    {
                        return 1;
                    }
                    else if (a.get().size() < b.get().size())
                    {
                        return -1;
                    }
                    else if (minAmount(a, count) < minAmount(b, count))
                    {
                        return -1;
                    }
                    else
                    {
                        return 1;
                    }
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
            if (juices.get(i).get().size() == juices.get(i + 1).get().size())
            {
                if (juices.get(i).get().equals(juices.get(i + 1).get()))
                {
                    juices.remove(i);
                    i--;
                    n--;
                }
            }
        }
        return juices;
    }

    public int washAmount(ArrayList <Juice> juices, TreeMap <String, Integer> componentsAmount) {
        int amount = 0;
        sortCount(juices, componentsAmount);
        juices = getUnique(juices);
        TreeSet <String> current = new TreeSet();
        int n = juices.size();
        while (!juices.isEmpty())
        {
            current = juices.get(0).get();
            juices.remove(0);
            n--;
            for (int i = 0; i < n; i++)
            {
                if (isSubset(current, juices.get(i).get()))
                {
                    current = juices.get(i).get();
                    juices.remove(i);
                    n--;
                    i--;
                }
            }
            amount++;
        }
        return amount;
    }
}
