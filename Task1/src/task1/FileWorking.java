package task1;
import java.util.*;
import java.io.*;

public class FileWorking {
    public  ArrayList <ArrayList <String>> input(String name) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(name));
        String tempString = in.readLine();
        StringTokenizer sT;
        ArrayList <ArrayList <String>> data = new ArrayList();
        while (tempString != null) {
            sT = new StringTokenizer(tempString);
            int count = sT.countTokens();
            ArrayList <String> aL = new ArrayList();
            for (int i = 0; i < count; i++) {
                String s = sT.nextToken();
                aL.add(s);
            }
            data.add(aL);
            tempString = in.readLine();
        }
        in.close();
        return data;
    }

    public void printComponents(ArrayList <String> components, String name) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(name));
        Iterator <String> iter = components.iterator();
        while (iter.hasNext()) {
            out.println(iter.next());
        }
        out.flush();
        out.close();
    }

    public void printAmount(int amount, String name) throws IOException{
        PrintWriter out = new PrintWriter (new FileWriter (name));
        out.println(amount);
        out.flush();
        out.close();
    }
}
