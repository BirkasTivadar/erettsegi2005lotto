package lotto;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LottoMain {

    public static void main(String[] args) {
        LottosOfYear lottosOfYear = new LottosOfYear();

        Path lottoReadFile = Path.of("src", "main", "resources", "lottosz.dat");

        lottosOfYear.loadFromFile(lottoReadFile);


        // 1.
        lottosOfYear.addWeek(52, new ArrayList<>(List.of(89, 24, 34, 11, 64)));

        // 4.
        System.out.println(lottosOfYear.getNumbersOfWeek(52));

        // 5.
        System.out.println(lottosOfYear.checkMissedNumber());

        // 6.
        System.out.println(lottosOfYear.countOddNumbers());

        // 7.
        Path lottoWriteFile = Path.of("src", "main", "resources", "lotto52ki.dat");
        lottosOfYear.writeToFile(lottoWriteFile);

        // 8.
        System.out.println(Arrays.toString(lottosOfYear.getCountDrawNumbers()));

        // 9.
        System.out.println(lottosOfYear.getNotDrawnPrimes());
    }
}
