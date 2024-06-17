import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Формируем шаблоны поиска
        String[] arrayNames3 = {"aaa", "bbb", "ccc", "aba", "aca", "cbc", "aab", "aac"
                , "abb", "acc", "bcc", "bcc"};
        List<String> listNames3 = new ArrayList<>(Arrays.asList(arrayNames3));

        String[] arrayNames4 = {"aaaa", "bbbb", "cccc", "abba", "acca", "cbbc", "aaab"
                , "aaac", "aabb", "aacc", "aaab", "aaac", "bccc", "bbcc", "bbbc"};
        List<String> listNames4 = new ArrayList<>(Arrays.asList(arrayNames4));

        String[] arrayNames5 = {"aaaaa", "bbbbb", "ccccc", "aabaa", "aacaa", "ccbcc", "aaaab"
                , "aaaabb", "aabbb", "abbbb", "aaaac", "aaacc", "aaccc", "acccc"
                , "bbbbc", "bbbcc", "bbccc", "bcccc"};
        List<String> listNames5 = new ArrayList<>(Arrays.asList(arrayNames5));

        // Создаем классы классы для подсчета имен
        CounterNickName counter3 = new CounterNickName();
        CounterNickName counter4 = new CounterNickName();
        CounterNickName counter5 = new CounterNickName();

        //Генерация произволных строк
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        // Создаем три потока для обработки
        Thread thread3 = new Thread(null, () -> {
            counter3.count(texts, listNames3, 3);
        });

        Thread thread4 = new Thread(null, () -> {
            counter4.count(texts, listNames4, 4);
        });

        Thread thread5 = new Thread(null, () -> {
            counter5.count(texts, listNames5, 5);
        });

        // Стартуем потоки
        thread3.start();
        thread4.start();
        thread5.start();

        //Ожидаем завершения выполнения потоков
        thread3.join();
        thread4.join();
        thread5.join();

        // Выводим результаты
        System.out.println("Результаты подсчета красивых имен");
        System.out.println("Counter3: " + counter3.getCounter());
        System.out.println("Counter4: " + counter4.getCounter());
        System.out.println("Counter5: " + counter5.getCounter());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
