package stream;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork
public class StreamParallelBenchmark {

    @Param({"1000", "10000", "100000"})
    private int size;

    private List<Integer> numbers;

    @Setup
    public void setup() {
        numbers = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            numbers.add(random.nextInt(1000));
        }
    }

    @Benchmark
    public int streamSingleThread() {
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    @Benchmark
    public int streamParallel() {
        return numbers.parallelStream().mapToInt(Integer::intValue).sum();
    }

//    @Benchmark
//    public int streamParallelWithHttpRequests() {
//        return numbers.parallelStream().mapToInt(value -> {
//            // simulate an HTTP request
//            try {
//                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(11));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return value;
//        }).sum();
//    }
}