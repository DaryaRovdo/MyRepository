package task1;
import java.util.*;
import java.io.*;

public class FileWorking {
    public  ArrayList <ArrayList <String>> input(String name) throws IOException {
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

    public void printComponents(ArrayList <String> components, String name) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(name));
        Iterator <String> iter = components.iterator();
        while (iter.hasNext())
        {
            out.println(iter.next());
        }
        out.flush();
        out.close();
    }

    public void printAmount(int n, String name) throws IOException{
        PrintWriter out = new PrintWriter (new FileWriter (name));
        out.println(n);
        out.flush();
        out.close();
    }
}
