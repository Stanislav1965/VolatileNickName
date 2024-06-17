import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterNickName {
    AtomicInteger counter = new AtomicInteger(0);

    public void count(String[] texts, List<String> names, int length) {
        for (String text : texts) {
            if (text.length() == length) {
                for (String name : names) {
                    if (text.equals(name)) {
                        counter.getAndIncrement();
                    }
                }
            }
        }
    }

    public long getCounter() {
        return counter.get();
    }

}
