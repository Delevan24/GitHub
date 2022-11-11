package com.amy.common.design.fortest.futuredemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.*;

/**
 * @author xuqingxin
 */
@Slf4j
public class CompletableFutureDemo {

    /**
     * <p>
     * ***********************************************
     * CompletableFuture使用详解
     *
     * CompletableFuture是对Future的扩展和增强。CompletableFuture实现了Future接口，并在此基础上进行了丰富的扩展，
     * 完美弥补了Future的局限性，同时CompletableFuture实现了对任务编排的能力。借助这项能力，可以轻松地组织不同任务的运行顺序、
     * 规则以及方式。从某种程度上说，这项能力是它的核心能力。而在以往，虽然通过CountDownLatch等工具类也可以实现任务的编排，
     * 但需要复杂的逻辑处理，不仅耗费精力且难以维护。
     *
     * CompletionStage接口定义了任务编排的方法，执行某一阶段，可以向下执行后续阶段。
     * 异步执行的，默认线程池是ForkJoinPool.commonPool()，但为了业务之间互不影响，且便于定位问题，强烈推荐使用自定义线程池。
     *
     * 1.2.1 常用方法
     *
     * 依赖关系
     *
     * thenApply()：把前面任务的执行结果，交给后面的Function
     * thenCompose()：用来连接两个有依赖关系的任务，结果由第二个任务返回
     * and集合关系
     *
     * thenCombine()：合并任务，有返回值
     * thenAccepetBoth()：两个任务执行完成后，将结果交给thenAccepetBoth处理，无返回值
     * runAfterBoth()：两个任务都执行完成后，执行下一步操作(Runnable类型任务)
     * or聚合关系
     *
     * applyToEither()：两个任务哪个执行的快，就使用哪一个结果，有返回值
     * acceptEither()：两个任务哪个执行的快，就消费哪一个结果，无返回值
     * runAfterEither()：任意一个任务执行完成，进行下一步操作(Runnable类型任务)
     * 并行执行
     *
     * allOf()：当所有给定的 CompletableFuture 完成时，返回一个新的 CompletableFuture
     * anyOf()：当任何一个给定的CompletablFuture完成时，返回一个新的CompletableFuture
     * 结果处理
     *
     * whenComplete：当任务完成时，将使用结果(或 null)和此阶段的异常(或 null如果没有)执行给定操作
     * exceptionally：返回一个新的CompletableFuture，当前面的CompletableFuture完成时，它也完成，当它异常完成时，给定函数的异常触发这个CompletableFuture的完成
     *
     * ***********************************************
     * </p>
     */


    /**
     * <p>
     * ***********************************************
     * 异步操作：
     * <p>
     * CompletableFuture提供了四个静态方法来创建一个异步操作：
     * <p>
     * public static CompletableFuture<Void> runAsync(Runnable runnable)
     * public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
     * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
     * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
     * <p>
     * 这四个方法的区别：
     * <p>
     * a、runAsync() 以Runnable函数式接口类型为参数，没有返回结果，supplyAsync() 以Supplier函数式接口类型为参数，返回结果类型为U；Supplier接口的 get()是有返回值的(会阻塞)
     * b、使用没有指定Executor的方法时，内部使用ForkJoinPool.commonPool() 作为它的线程池执行异步代码。如果指定线程池，则使用指定的线程池运行。
     * c、默认情况下CompletableFuture会使用公共的ForkJoinPool线程池，这个线程池默认创建的线程数是 CPU 的核数
     * （也可以通过 JVM option:-Djava.util.concurrent.ForkJoinPool.common.parallelism 来设置ForkJoinPool线程池的线程数）。
     * 如果所有CompletableFuture共享一个线程池，那么一旦有任务执行一些很慢的 I/O 操作，就会导致线程池中所有线程都阻塞在 I/O 操作上，
     * 从而造成线程饥饿，进而影响整个系统的性能。所以，强烈建议你要根据不同的业务类型创建不同的线程池，以避免互相干扰
     * <p>
     * ***********************************************
     * </p>
     */
    @Test
    public void asyncTest() throws ExecutionException, InterruptedException {
        Runnable runnable = () -> System.out.println("无返回结果异步任务");
        CompletableFuture.runAsync(runnable);

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("有返回值的异步任务");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello World";
        });
        String result = future.get();
    }

    /**
     * <p>
     * ***********************************************
     * |结果处理:
     * 当CompletableFuture的计算结果完成，或者抛出异常的时候，我们可以执行特定的 Action。主要是下面的方法：
     * <p>
     * public CompletableFuture<T> whenComplete(BiConsumer<? super T,? super Throwable> action)
     * public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
     * public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
     * <p>
     * Action的类型是BiConsumer<? super T,? super Throwable>，它可以处理正常的计算结果，或者异常情况。
     * 方法不以Async结尾，意味着Action使用相同的线程执行，而Async可能会使用其它的线程去执行(如果使用相同的线程池，也可能会被同一个线程选中执行)。
     * 这几个方法都会返回CompletableFuture，当Action执行完毕后它的结果返回原始的CompletableFuture的计算结果或者返回异常
     * <p>
     * ***********************************************
     * </p>
     */

    @Test
    public void test3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            if (new Random().nextInt(10) % 2 == 0) {
                int i = 12 / 0;
            }
            System.out.println("执行结束！");
            return "test";
        });
        // 任务完成或异常方法完成时执行该方法
        // 如果出现了异常,任务结果为null
        future.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String t, Throwable action) {
                System.out.println(t + " 执行完成！");
            }
        });
        // 出现异常时先执行该方法
        future.exceptionally(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable t) {
                System.out.println("执行失败：" + t.getMessage());
                return "异常xxxx";
            }
        });

        future.get();
    }

    /**
     * <p>
     * ***********************************************
     * | 结果转换：
     * thenApply接收一个函数作为参数，使用该函数处理上一个CompletableFuture调用的结果，并返回一个具有处理结果的Future对象。
     * public <U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
     * public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
     * <p>
     * ***********************************************
     * </p>
     */
    @Test
    public void test4() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int result = 100;
            System.out.println("第一次运算：" + result);
            return result;
        }).thenApply(number -> {
            int result = number * 3;
            System.out.println("第二次运算：" + result);
            return result;
        });
    }

    /**
     * <p>
     * ***********************************************
     * |    thenCompose的参数为一个返回CompletableFuture实例的函数，该函数的参数是先前计算步骤的结果。
     * <p>
     * public <U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn);
     * public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) ;
     * ***********************************************
     * </p>
     */
    @Test
    public void testMethod1() {
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int number = new Random().nextInt(30);
                        System.out.println("第一次运算：" + number);
                        return number;
                    }
                })
                .thenCompose(new Function<Integer, CompletionStage<Integer>>() {
                    @Override
                    public CompletionStage<Integer> apply(Integer param) {
                        return CompletableFuture.supplyAsync(new Supplier<Integer>() {
                            @Override
                            public Integer get() {
                                int number = param * 2;
                                System.out.println("第二次运算：" + number);
                                return number;
                            }
                        });
                    }
                });
    }


    /**
     * <p>
     * ***********************************************
     * |thenAcceptBoth函数的作用是，当两个CompletionStage都正常完成计算的时候，就会执行提供的action消费两个异步的结果。
     * public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
     * public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
     * ***********************************************
     * </p>
     */
    @Test
    public void testMethod2() throws InterruptedException {
        CompletableFuture<Integer> futrue1 = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(3) + 1;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务1结果：" + number);
            return number;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(3) + 1;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务2结果：" + number);
            return number;
        });

        futrue1.thenAcceptBoth(future2, (x, y) -> System.out.println("最终结果：" + (x + y)));

        TimeUnit.SECONDS.sleep(2);
    }

    /**
     * <p>
     * ***********************************************
     * |    thenRun也是对线程任务结果的一种消费函数，与thenAccept不同的是，thenRun会在上一阶段
     * CompletableFuture计算完成的时候执行一个Runnable，而Runnable并不使用该CompletableFuture计算的结果。
     * <p>
     * public CompletionStage<Void> thenRun(Runnable action);
     * public CompletionStage<Void> thenRunAsync(Runnable action);
     * ***********************************************
     * </p>
     */
    @Test
    public void testMethod3() {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(10);
            System.out.println("第一阶段：" + number);
            return number;
        }).thenRun(() ->
                System.out.println("thenRun 执行"));

    }

    /**
     * <p>
     * ***********************************************
     * |    thenCombine
     * <p>
     * 合并两个线程任务的结果，并进一步处理。
     * <p>
     * public <U,V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
     * <p>
     * public <U,V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
     * <p>
     * public <U,V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn, Executor executor);
     * ***********************************************
     * </p>
     */
    @Test
    public void testMethod4() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(() -> {
                    int number = new Random().nextInt(10);
                    System.out.println("任务1结果：" + number);
                    return number;
                });
        CompletableFuture<Integer> future2 = CompletableFuture
                .supplyAsync(() -> {
                    int number = new Random().nextInt(10);
                    System.out.println("任务2结果：" + number);
                    return number;
                });
        CompletableFuture<Integer> result = future1
                .thenCombine(future2, Integer::sum);
        System.out.println("组合后结果：" + result.get());


    }

    /**
     * <p>
     * ***********************************************
     * |    任务交互
     * 线程交互指将两个线程任务获取结果的速度相比较，按一定的规则进行下一步处理。
     * <p>
     * applyToEither
     * <p>
     * 两个线程任务相比较，先获得执行结果的，就对该结果进行下一步的转化操作。
     * <p>
     * public <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> other,Function<? super T, U> fn);
     * public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn);
     * ***********************************************
     * </p>
     */
    @Test
    public void testMethod5() {
        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(() -> {
                    int number = new Random().nextInt(10);
                    try {
                        TimeUnit.SECONDS.sleep(number);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务1结果:" + number);
                    return number;
                });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务2结果:" + number);
            return number;
        });

        future1.applyToEither(future2, number -> {
            System.out.println("最快结果：" + number);
            return number * 2;
        });
    }

    /**
     * <p>
     * ***********************************************
     * | acceptEither
     * <p>
     * 两个线程任务相比较，先获得执行结果的，就对该结果进行下一步的消费操作。
     * <p>
     * public CompletionStage<Void> acceptEither(CompletionStage<? extends T> other,Consumer<? super T> action);
     * public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action);
     * ***********************************************
     * </p>
     */
    @Test
    public void testMethod6() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(10) + 1;
            try {
                TimeUnit.SECONDS.sleep(number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第一阶段：" + number);
            return number;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(10) + 1;
            try {
                TimeUnit.SECONDS.sleep(number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第二阶段：" + number);
            return number;
        });

        future1.acceptEither(future2, number -> System.out.println("最快结果：" + number));
    }

    /**
     * <p>
     * ***********************************************
     * |    runAfterEither
     * <p>
     * 两个线程任务相比较，有任何一个执行完成，就进行下一步操作，不关心运行结果。
     * <p>
     * public CompletionStage<Void> runAfterEither(CompletionStage<?> other,Runnable action);
     * public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action);
     * <p>
     * ***********************************************
     * </p>
     */
    @Test
    public void testMethod7() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(5);
            try {
                TimeUnit.SECONDS.sleep(number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务1结果：" + number);
            return number;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(5);
            try {
                TimeUnit.SECONDS.sleep(number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务2结果:" + number);
            return number;
        });

        future1.runAfterEither(future2, () -> System.out.println("已经有一个任务完成了")).join();
    }

    /**
     * <p>
     * ***********************************************
     * |    anyOf
     * <p>
     * anyOf()的参数是多个给定的 CompletableFuture，当其中的任何一个完成时，方法返回这个 CompletableFuture。
     * <p>
     * public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)
     * ***********************************************
     * </p>
     */
    @Test
    public void testMethod8() {
        Random random = new Random();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        });
        CompletableFuture<Object> result = CompletableFuture.anyOf(future1, future2);
    }

    /**
     * <p>
     * ***********************************************
     * |    allOf
     *
     * allOf方法用来实现多 CompletableFuture 的同时返回。
     *
     * public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)
     * ***********************************************
     * </p>
     */
    @Test
    public void testMethod() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future1完成！");
            return "future1完成！";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future2完成！");
            return "future2完成！";
        });

        CompletableFuture<Void> combindFuture = CompletableFuture.allOf(future1, future2);

        try {
            combindFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void exceptionTest() {
        CompletableFuture.runAsync(() -> {
            System.out.println("future is running!");
            throw new RuntimeException(" i'm exception!!");
        }).exceptionally(t -> {
            System.out.println("执行失败：" + t);
            log.error("getPayInfo.syncPayDetails failed! msg:{}", ExceptionUtils.getFullStackTrace(t));

            return null;
        });

        System.out.println("i'm ok!!");
        System.out.println("i'm ok!!");
        System.out.println("i'm ok!!");
        System.out.println("i'm ok!!");
        System.out.println("i'm ok!!");
        System.out.println("i'm ok!!");
        System.out.println("i'm ok!!");
        System.out.println("i'm ok!!");
        System.out.println("i'm ok!!");
        System.out.println("i'm ok!!");
        System.out.println("i'm ok!!");
    }
}
