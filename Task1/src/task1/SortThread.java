package task1;

import java.util.*;

public class SortThread extends Thread{
        private ArrayList aL;
        SortThread (ArrayList aL) {
            this.aL = aL;
        }

        @Override
        public void run() {
            Collections.sort(aL, new Comparator <String> () {
                @Override
                public int compare(String first, String second) {
                    return first.compareTo(second);
                }
            });
        }
}
