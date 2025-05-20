import domain.MyMap;
import domain.Student;
import domain.validators.ValidationException;
import domain.validators.Validator;
import repo.InMemoryRepository;
import repo.Repository;

import java.util.HashSet;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Student s1 =  new Student("Dan", 4.5f);
        Student s2 =  new Student("Ana", 8.5f);
        Student s3 =  new Student("Dan", 4.5f);
        Student s4 =  new Student("Mihai", 9.5f);
        Student s5 =  new Student("Danu", 3.5f);

        HashSet<Student> colectie= new HashSet<>();
        colectie.add(s1);
        colectie.add(s2);
        colectie.add(s3);
        colectie.add(s4);
        colectie.add(s5);
        System.out.println("Colectie:");
        System.out.println(colectie);

        TreeSet<Student> treeSet = new TreeSet<>(colectie);
        System.out.println("TreeSet:");
        System.out.println(treeSet);


        System.out.println("domain.MyMap:");
        MyMap map = new MyMap();
        for(var it : MyMap.getList()){
            System.out.println(it);
        }

        System.out.println("Medii:");
        map.addStudent(s1);
        map.addStudent(s2);
        map.addStudent(s3);
        System.out.println(map.getEntries());


        Validator<Student> validator = new Validator<Student>() {
            @Override
            public void validate(Student entity) throws ValidationException {

            }
        };

        Repository<Long, Student> repo = new InMemoryRepository<>(validator);

        

    }

}