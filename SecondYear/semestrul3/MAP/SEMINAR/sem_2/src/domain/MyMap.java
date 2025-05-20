package domain;

import java.util.*;

public class MyMap {
    TreeMap<Integer , List<Student>> tree;

    public MyMap() {
        this.tree = new TreeMap<>(new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                return -(o1 - o2);
            }
        });
    }

    public Set<Map.Entry<Integer, List<Student>>> getEntries(){
        return tree.entrySet();
    }

    public void addStudent(Student student){
        int medie = Math.round(student.getMedie());

        if (tree.get(medie) == null) {
            ArrayList<Student> list = new ArrayList<>();
            list.add(student);
            tree.put(medie, list);
        }
        else {
            tree.get(medie).add(student);
        }


    }

    public static List<Student> getList(){
        List<Student> l=new ArrayList<Student>();
        l.add(new Student("1",9.7f));
        l.add(new Student("2",7.3f));
        l.add(new Student("3",6f));
        l.add(new Student("4",6.9f));
        l.add(new Student("5",9.5f));
        l.add(new Student("6",9.9f));
        return l;
    }
}
