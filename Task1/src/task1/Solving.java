package task1;
import java.util.*;

public class Solving {
    public ArrayList<Juice> getJuices(ArrayList <ArrayList <String>> data) {
        ArrayList <Juice> juices = new ArrayList();
        Iterator <ArrayList <String>> it = data.iterator();
        while (it.hasNext()) {
            Juice j = new Juice();
            ArrayList <String> aL = it.next();
            Iterator <String> iter = aL.iterator();
            while (iter.hasNext()) {
                j.get().add(iter.next());
            }
            juices.add(j);
        }
        return juices;
    }

    public ArrayList <String> getComponents (ArrayList <ArrayList <String>> data) {
        ArrayList <String> components = new ArrayList();
        Iterator <ArrayList <String>> it = data.iterator();
        while (it.hasNext()) {
            ArrayList <String> aL = it.next();
            Iterator <String> iter = aL.iterator();
            while (iter.hasNext()) {
                components.add(iter.next());
            }
        }
        return components;
    }

    public ArrayList <String> getUniqueComponents (ArrayList <String> components) throws InterruptedException {
        SortThread st = new SortThread(components);
        st.start();
        st.join();
        int size = components.size();
        for (int i = 0; i < size - 1; i++) {
            if (components.get(i).compareTo(components.get(i + 1)) == 0) {
                components.remove(i);
                i--;
                size--;
            }
        }
        return components;
    }

    public TreeMap <String, Integer> getComponentsAmount(ArrayList <String> components) {
        TreeMap <String, Integer> componentsAmount = new TreeMap();
        Integer amount;
        for (String str : components) {
            amount = componentsAmount.get(str);
            if (amount == null) {
                componentsAmount.put(str, 1);
            }
            else {
                componentsAmount.put(str, amount + 1);
            }
        }
        return componentsAmount;
    }

    private int minAmount (Juice j, TreeMap <String, Integer> count) {
        int min = Integer.MAX_VALUE;
        for (String component : j.get()) {
            if (count.get(component) < min) {
                min = count.get(component);
            }
        }
        return min;
    }

    private void sortCount (ArrayList <Juice> juices, final TreeMap <String, Integer> count) {
        Collections.sort(juices, new Comparator <Juice> () {
                @Override
                public int compare(Juice first, Juice second) {
                    if (first.get().size() > second.get().size()) {
                        return 1;
                    }
                    else if (first.get().size() < second.get().size()) {
                        return -1;
                    }
                    else if (minAmount(first, count) < minAmount(second, count)) {
                        return -1;
                    }
                    else {
                        return 1;
                    }
                }
            });
    }

    private boolean isSubset (TreeSet <String> testSet, TreeSet <String> set) {
        return set.containsAll(testSet);
    }

    private ArrayList <Juice> getUnique (ArrayList <Juice> juices) {
        int size = juices.size();
        for (int i = 0; i < size - 1; i++) {
            if (juices.get(i).get().size() == juices.get(i + 1).get().size()) {
                if (juices.get(i).get().equals(juices.get(i + 1).get())) {
                    juices.remove(i);
                    i--;
                    size--;
                }
            }
        }
        return juices;
    }

    public int washAmount(ArrayList <Juice> juices, TreeMap <String, Integer> componentsAmount) {
        int amount = 0;
        sortCount(juices, componentsAmount);
        juices = getUnique(juices);
        TreeSet <String> currentComponents = new TreeSet();
        int size = juices.size();
        while (!juices.isEmpty()) {
            currentComponents = juices.get(0).get();
            juices.remove(0);
            size--;
            for (int i = 0; i < size; i++) {
                if (isSubset(currentComponents, juices.get(i).get())) {
                    currentComponents = juices.get(i).get();
                    juices.remove(i);
                    size--;
                    i--;
                }
            }
            amount++;
        }
        return amount;
    }
}
