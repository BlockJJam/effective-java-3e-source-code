package effectivejava.chapter11.item81.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentTimer {
    private ConcurrentTimer() {}

    public static long time(Executor executor,
                            int concurrency,
                            Runnable action) throws InterruptedException{
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);

        for(int i=0; i<concurrency; i++){
            executor.execute(() -> {
                ready.countDown();
                try {
                    start.await();
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }

        ready.await();
        long startNanos = System.nanoTime();
        start.countDown();
        done.await();

        return System.nanoTime() - startNanos;
    }

    public static long loop(int count){
        long startNanos = System.nanoTime();
        for(int j=0; j<count; j++){
            String threadName = Thread.currentThread().getName();
            System.out.println("Job[" + threadName + "] start");
            long sum = 0;
            for (long i = 0; i < 10_000_000_000L; i++) {
            }
            System.out.println("Job[" + threadName + "] end, sum = " + sum);
        }

        return System.nanoTime() - startNanos;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        long concurrent = time(executor, 4, () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Job[" + threadName + "] start");
            long sum = 0;
            for (long i = 0; i < 10_000_000_000L; i++) {
            }
            System.out.println("Job[" + threadName + "] end, sum = " + sum);

        });

        long sequencial = loop(4);
        System.out.println("동시성 시간: "+ concurrent);
        System.out.println("연속 시간 = " + sequencial);
        System.out.println("연속 시간 대비 동시성 시간 차이 = " + sequencial/concurrent+"배");
    }

}
