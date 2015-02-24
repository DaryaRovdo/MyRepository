package task1;

import java.util.*;

public class Juice {
        private TreeSet <String> components;
        Juice() {
            components = new TreeSet ();
        }
        public TreeSet <String> get() {
            return components;
        }
        public void addComponent(String s) {
            components.add(s);
        }
}
