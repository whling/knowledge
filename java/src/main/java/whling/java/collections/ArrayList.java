package whling.java.collections;

import com.google.common.collect.Lists;

import java.util.ListIterator;

public class ArrayList {



    public static void main(String[] args) {
        java.util.List<String> list = Lists.newArrayList("1","2","3");

//        list = Lists.newCopyOnWriteArrayList(list);

//        for (String s : list) {
//            System.out.println(s);
//            synchronized (ArrayList.class) {
//                list.remove(s);
//
//            }
//        }

        /**
         * 从后往前遍历
         */
        ListIterator<String> iterator = list.listIterator(list.size());
        while (iterator.hasPrevious()) {
//            String s = iterator.next();
//            System.out.println(iterator.hasPrevious());
////            System.out.println(s);
//            System.out.println(iterator.previousIndex());
//            System.out.println(iterator.nextIndex());
            System.out.println(iterator.previous());
            System.out.println("===");
        }

//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            String s = iterator.next();
//            System.out.println(s);
//            iterator.remove();
//        }

    }
}
