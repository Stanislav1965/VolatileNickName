import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger counter3 = new AtomicInteger(0);
        AtomicInteger counter4 = new AtomicInteger(0);
        AtomicInteger counter5 = new AtomicInteger(0);

        //Генерация произволных строк
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        // Создаем три потока для обработки
        Thread thread3 = new Thread(null, () -> {
            for (String text : texts) {
                if (!isWordOneChar(text)) {
                    if (isWordPalindrome(text)) {
                        incrementCounter(text.length(),counter3,counter4,counter5);
                    }
                }
            }
        });

        Thread thread4 = new Thread(null, () -> {
            for (String text : texts) {
                if (isWordOneChar(text)) {
                    incrementCounter(text.length(),counter3,counter4,counter5);
                }
            }
        });

        Thread thread5 = new Thread(null, () -> {
            for (String text : texts) {
                if (!isWordOneChar(text)) {
                    if (isLettersAscending(text)) {
                        incrementCounter(text.length(),counter3,counter4,counter5);
                    }
                }
            }
        });

        // Стартуем потоки
        thread3.start();
        thread4.start();
        thread5.start();

        //Ожидаем завершения выполнения потоков
        thread3.join();
        thread4.join();
        thread5.join();

        //Выводим результаты
        System.out.println("Результаты подсчета красивых имен");
        System.out.println("Красивых слов с длиной 3: " + counter3);
        System.out.println("Красивых слов с длиной 4: " + counter4);
        System.out.println("Красивых слов с длиной 5: " + counter5);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isWordPalindrome(String word) {
        char[] chars = word.toCharArray();
        int left = 0; // индекс первого символа
        int right = chars.length - 1; // индекс последнего символа
        while (left < right) { // пока не дошли до середины слова
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isWordOneChar(String word) {
        char[] chars = word.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (chars[0] != chars[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLettersAscending(String word) {
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (Character.getNumericValue(chars[i]) > Character.getNumericValue(chars[i + 1])) {
                return false;
            }
        }
        return true;
    }

    public static void incrementCounter(int length, AtomicInteger counter3, AtomicInteger counter4, AtomicInteger counter5){
        switch (length) {
            case 3:
                counter3.getAndIncrement();
                break;
            case 4:
                counter4.getAndIncrement();
                break;
            case 5:
                counter5.getAndIncrement();
                break;
        }
    }
}
