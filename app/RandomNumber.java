package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNumber {
    List<Integer> numbers;
    Random r = new Random();

    public RandomNumber() {
        numbers =  new ArrayList<Integer>();
        fill();
    }

    private void fill() {
        for (int i = 0; i < 100; i++) {
            numbers.add(i);
        }
    }

    int generate() {
        return numbers.remove(r.nextInt(numbers.size()));
    }

    int generate(int size) {
        return r.nextInt(size);
    }
}