package prosky.ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class IntService {

    private static final Logger logger = LoggerFactory.getLogger(IntService.class);

    public Long findTheStream() {//Calculation findTheStream 44912600 nanoseconds
        logger.info("Start the Stream");
        long startTime = System.nanoTime();

        long sum = Stream
                .iterate(1L, a -> a + 1)
                .limit(1_000_000)
                .reduce(0L, Long::sum);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        logger.info("Calculation findTheStream {} nanoseconds", duration);
        return sum;
    }

    public Long findTheFastestStream() {//Calculation findTheFastestStream 8961100 nanoseconds
        logger.info("Start the parallel Stream");
        long startTime = System.nanoTime();

        long sum = IntStream
                .rangeClosed(1, 1_000_000)
                .parallel()
                .asLongStream()
                .sum();

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        logger.info("Calculation findTheFastestStream {} nanoseconds", duration);
        return sum;
    }

    public Long getSum() {//Calculation calculateSum 500 nanoseconds
        logger.info("Was invoked method for calculating the sum of numbers from 1 to 1,000,000");
        int n = 1_000_000;
        return calculateSum(n);
    }

    private Long calculateSum(int n) {
        logger.info("We're timing it");

        long startTime = System.nanoTime();
        long result = (long) n * (n + 1) / 2; // Используем long для вычислений
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        logger.info("Calculation calculateSum {} nanoseconds", duration);

        return result;
    }
}
