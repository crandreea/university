import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {
    public static <E> void printArie(List<E> l, Arie<E> f) {
        l.forEach(e->{
            double area = f.compute(e);
            System.out.println(area);
        });
    }

    //2. PREDICATE
    public static <E> void filterAndPrint(List<E> list, Predicate<E> p){
        list.forEach(e->{
            if(p.test(e))
                System.out.println(e);
        });
    }


    public static void main(String[] args) {
        //2. PREDICATE
        Predicate<Integer> p = x -> x % 2 == 0;
        filterAndPrint(List.of(1, 2, 3, 4, 5, 6), p);


        //3. FUNCTIONS
        Function<String, Integer> converterLambda1 = x -> Integer.valueOf(x);
        System.out.println(converterLambda1.apply("123"));

        Predicate<Character> isVowel = x -> List.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U').contains(x);

        Function<String, String> converterLambda2 = x -> {
            String result = "";

            for (int i = 0; i < x.length(); i++) {
                if (isVowel.test(x.charAt(i))) {
                    result = result + x.charAt(i) + "p" + x.charAt(i);
                } else {
                    result = result + x.charAt(i);
                }
            }
            return result;
        };
        System.out.println(converterLambda2.apply("Ana are mere"));


        //4. SUPPLIER
        Supplier<Integer> primeNumberSupplier = () -> 2;
        System.out.println(primeNumberSupplier.get());

        Supplier<String> stringSupplier = String::new;
        System.out.println(stringSupplier.get());

        Supplier<Cerc> circleSupplier = () -> new Cerc();
        System.out.println(circleSupplier.get());

        //5. COMPARATORS
        List.of("Mere", "Pere", "Nuci", "Visine", "Cirese")
                .stream()
                .sorted(String::compareTo)
                .map(String::toUpperCase)
                .forEach(System.out::println);

        List<String> strings = List.of("Mere", "Pere", "Nuci", "Visine", "Cirese");
        Stream<String> streams = strings.stream();
        streams.sorted((a, b)->b.compareTo(a)) //sorteaza cu comparator
                .map(a->a.toUpperCase()) //transforma in smth
                .forEach(System.out::println);




    }
}