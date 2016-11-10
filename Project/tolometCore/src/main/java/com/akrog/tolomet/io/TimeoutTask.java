package com.akrog.tolomet.io;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by gorka on 8/11/16.
 */

public abstract class TimeoutTask<T> implements Callable<T> {
    private Future<T> future;
    private final long timeout;
    private final int retries;

    public TimeoutTask( long timeout ) {
        this(timeout,1);
    }

    public TimeoutTask( long timeout, int retries ) {
        this.timeout = timeout;
        this.retries = retries;
    }

    public T execute() throws Exception {
        int retries = this.retries;
        Exception error = null;
        T result = null;
        do {
            try {
                result = retry();
            } catch (Exception e) {
                error = e;
            }
        } while( error != null && --retries > 0 );
        if( error != null )
            throw error;
        return result;
    }

    private T retry() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
        future = executor.submit(this);
        return future.get(timeout, TimeUnit.SECONDS);
    }

    void cancel() {
        if( future == null )
            return;
        future.cancel(true);
        future = null;
    }
}
