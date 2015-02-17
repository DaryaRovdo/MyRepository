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
        Solving s = new Solving();
        FileWorking fw = new FileWorking();
        data = fw.input("juice.in");
        juices = s.getJuices(data);
        ArrayList <String> components = new ArrayList();
        components = s.getComponents(data);
        fw.printComponents(components, "juice1.out");
        TreeMap <String, Integer> componentsAmount = new TreeMap();
        componentsAmount = s.getComponentsAmount(components);
        components = s.getUniqueComponents(components);
        fw.printComponents(components, "juice2.out");
        int n = s.washAmount(juices, componentsAmount);
        fw.printAmount(n, "juice3.out");
    }

}
