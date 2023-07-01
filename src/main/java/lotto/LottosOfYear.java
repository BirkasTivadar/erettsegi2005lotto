package lotto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class LottosOfYear {

    private final Map<Integer, List<Integer>> numbersOfWeeks;

    public LottosOfYear() {
        numbersOfWeeks = new HashMap<>();
    }

    public void addWeek(Integer week, List<Integer> numbers) {
        List<Integer> ordered = new ArrayList<>(numbers);
        Collections.sort(ordered);
        numbersOfWeeks.put(week, ordered);
    }

    public void loadFromFile(Path path) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            loadNumbersOfWeeks(bufferedReader);
        } catch (IOException ioException) {
            throw new IllegalStateException("Can not read file", ioException);
        }
    }

    private void loadNumbersOfWeeks(BufferedReader bufferedReader) throws IOException {
        String line;
        Integer week = 1;
        while ((line = bufferedReader.readLine()) != null) {
            List<Integer> numbers = new ArrayList<>();
            String[] numbersArray = line.split(" ");
            IntStream.range(0, 5)
                    .forEach(i -> numbers.add(Integer.parseInt(numbersArray[i])));
            numbersOfWeeks.put(week, numbers);
            week++;
        }
    }

    public List<Integer> getNumbersOfWeek(Integer week) {
        return new ArrayList<>(numbersOfWeeks.get(week));
    }

    public boolean checkMissedNumber() {
        Set<Integer> allNumbers = new HashSet<>();
        IntStream.range(1, numbersOfWeeks.size())
                .forEach(i -> allNumbers.addAll(numbersOfWeeks.get(i)));
        return allNumbers.size() != 90;
    }

    public long countOddNumbers() {
        return IntStream.range(1, numbersOfWeeks.size())
                .mapToLong(i -> numbersOfWeeks.get(i).stream()
                        .filter(n -> n % 2 != 0)
                        .count())
                .sum();
    }

    public void writeToFile(Path path) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            for (int i = 1; i <= numbersOfWeeks.size(); i++) {
                List<Integer> numbersOfWeek = numbersOfWeeks.get(i);
                for (Integer number : numbersOfWeek) {
                    bufferedWriter.write(number + " ");
                }
                bufferedWriter.newLine();
            }
        } catch (IOException ioException) {
            throw new IllegalStateException("Can not write file", ioException);
        }
    }

    public int[] getCountDrawNumbers() {
        int[] result = new int[90];
        List<Integer> allDrawNumbers = new ArrayList<>();
        numbersOfWeeks.values()
                .forEach(allDrawNumbers::addAll);
        allDrawNumbers
                .forEach(number -> result[number - 1]++);
        return result;
    }

    public Set<Integer> getNotDrawnPrimes() {
        Set<Integer> result = new HashSet<>();
        List<Integer> primes = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89);
        Set<Integer> allNumbers = new HashSet<>();
        numbersOfWeeks.values()
                .forEach(allNumbers::addAll);
        primes.stream()
                .filter(prime -> !allNumbers.contains(prime))
                .forEach(result::add);
        return result;
    }
}
