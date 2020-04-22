package whling.java.collections;

import com.google.common.collect.Lists;

public class ArrayList {



    public static void main(String[] args) {
        java.util.List<String> list = Lists.newArrayList("1","2","3");

//        list = Lists.newCopyOnWriteArrayList(list);

        for (String s : list) {
            System.out.println(s);
            synchronized (ArrayList.class) {
                list.remove(s);

            }
        }

//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            String s = iterator.next();
//            System.out.println(s);
//            iterator.remove();
//        }

    }
}
