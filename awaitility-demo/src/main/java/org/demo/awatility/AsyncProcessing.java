package org.demo.awatility;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class AsyncProcessing {
    AtomicReference<TaskStatus> status = new AtomicReference<>();

    public void submitTask() {
        status.set(TaskStatus.STARTED);
        Executor threadExecutor = Executors.newFixedThreadPool(4);
        Runnable myTask = new MyTask(s -> {
            status.set(TaskStatus.FINISHED);
        });
        threadExecutor.execute(myTask);
    }

    public ForkJoinTask<Integer> parallelStreams() throws ExecutionException, InterruptedException {
        var forkJoinPool = new ForkJoinPool(10);
        return forkJoinPool.submit(() ->
                IntStream.range(1, 1_000).parallel()
                        .filter(AsyncProcessing::isPrime)
                        .sum());
    }

    public Integer getResult(ForkJoinTask<Integer> task) throws ExecutionException, InterruptedException {
        if (!task.isCompletedNormally()) {
            throw new IllegalStateException();
        }
        return task.get();
    }

    public boolean isStatusFinished() {
       return status.get().equals(TaskStatus.FINISHED);
    }

    public static boolean isPrime(int n){
        int factors = 0;
        for(int i = 1; i <= n; i++){
            if(n % i == 0)
                factors++;
        }
        if (factors == 2)
            return true;
        else
            return false;
    }

}
