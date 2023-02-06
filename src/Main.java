import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // Собираем совершеннолетних
        Stream<Person> adults = persons.stream()
                .filter(x -> x.getAge() > 18);

        // Делаем список призывников
        Stream<String> recruits = persons.stream()
                .filter(recruit -> recruit.getAge() >= 18)
                .filter(recruit -> recruit.getAge() <= 37)
                .filter(recruit -> recruit.getSex().toString() == "MAN")
                .map(Person::getFamily);

        // Собираем работоспособное население
        Stream<Person> workers = persons.stream()
                .filter(worker->worker.getAge()>=18)
                .filter(worker->
                    (worker.getSex().toString() == "MAN" && worker.getAge() < 60) ||
                    (worker.getSex().toString() == "WOMAN" && worker.getAge() < 6))
                .filter(worker->worker.getEducation().toString()=="HIGHER")
                .sorted(Comparator.comparing(worker->worker.getFamily().toString()));

        System.out.println("Список работоспособного населения: ");
        List<Person> listWorkers = workers.collect(Collectors.toList());
        System.out.println(listWorkers);

        System.out.println("Список призывников: ");
        List<String> listRecruits = recruits.collect(Collectors.toList());
        System.out.println(listRecruits);

        System.out.println("Количество совершеннолетних: " + adults.count());

    }
}