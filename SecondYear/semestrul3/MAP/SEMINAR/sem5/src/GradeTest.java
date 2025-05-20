import domain.Nota;
import domain.Student;
import domain.Tema;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GradeTest {
    public static void main(String[] args) {

        Student s1 = new Student(225, "Andrei");
        Student s2 = new Student(225, "Matei");
        Student s3 = new Student(226, "Ema");
        Student s4 = new Student(226, "Larisa");
        Student s5 = new Student(225, "Gabi");

        s1.setId(1L);
        s2.setId(2L);
        s3.setId(3L);
        s4.setId(4L);
        s5.setId(5L);


        List<Student> studentList = Arrays.asList(s1, s2, s3, s4, s5);

        Tema t1 = new Tema("Tema MAP", "1");
        Tema t2 = new Tema("Tema ASC", "2");
        Tema t3 = new Tema("Tema Algebra", "3");
        Tema t4 = new Tema("Tema OOP", "4");
        Tema t5 = new Tema("Tema SDA", "5");

        List<Tema> temaList = Arrays.asList(t1, t2, t3, t4, t5);

        Nota n1 = new Nota(s1, t5, 9.0, "Alex");
        Nota n2 = new Nota(s2, t2, 8.0, "Antonin");
        Nota n3 = new Nota(s3, t3, 5.0, "Nicu");
        Nota n4 = new Nota(s4, t4, 7.0, "Antonin");
        Nota n5 = new Nota(s5, t1, 10.0, "Antonin");

        List<Nota> notaList = Arrays.asList(n1, n2, n3, n4, n5);

        report1(notaList, "Alex");
        report2(notaList, "Alex");
        report3(notaList, 226);
    }

    /**
     * Lista studentilor al caror nume contin un anumit string (parametru),
     * sortati descrescator in functie de media lor
     */
    private static void report1(List<Nota> note, String s) {
        note.stream()
                .filter(nota -> nota.getStudent().getName().toLowerCase().contains(s.toLowerCase()))
                .collect(
                        Collectors.groupingBy(
                                Nota::getStudent,
                                Collectors.averagingDouble(Nota::getValue)
                        )
                )
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));
    }

    /**
     * Lista studentilor al caror nume contin un anumit string (parametru),
     * sortati descrescator in functie de media notelor date
     */
    private static void report2(List<Nota> note, String s) {
        note.stream().filter(nota -> nota.getProfesor().toLowerCase().contains(s.toLowerCase()))
                .collect(Collectors.groupingBy(
                        Nota::getProfesor,
                        Collectors.averagingDouble(Nota::getValue)
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }

    /**
     * Temele de la o anumita grupa, sortate descrescator in functie de
     * numarul de elevi care au primit nota la acea materie
     */
    public static void report3(List<Nota> note, int group) {
        note.stream()
                .filter(nota -> nota.getStudent().getGroup() == group)
                .collect(Collectors.groupingBy (
                        Nota::getTema,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(e -> System.out.println(e.getKey().getDesc() + " : " + e.getValue()));
    }
}
