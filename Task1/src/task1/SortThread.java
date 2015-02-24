package task1;

import java.util.*;

public class SortThread extends Thread{
        private ArrayList al;
        SortThread (ArrayList al) {
            this.al = al;
        }

        @Override
        public void run() {
            Collections.sort(al, new Comparator <String> () {
                @Override
                public int compare(String s1, String s2) {
                    return s1.compareTo(s2);
                }
            });
        }
}
