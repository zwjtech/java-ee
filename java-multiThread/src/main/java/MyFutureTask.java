/**
 * Created by zhangWeiJie on 2017/10/30.
 */
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * FutureTask
 FutureTask类相当于同时实现了Runnable和Future接口，提供了Future接口的具体实现，可以使用FutureTask去包装Callable或Runnable任务。
 因为FutureTask实现了Runnable接口，所以可以将其交给Executor去执行，或者直接调用run( )去执行。
 */
public class MyFutureTask {

    public static void main(String[] args) throws Exception {

        Executor executor = Executors.newFixedThreadPool(5);

        Callable<Integer> callable = new MyTarget();
        FutureTask<Integer> ft = new FutureTask<>(callable);

        executor.execute(ft);
        System.out.println(ft.get());

//      直接调用run
//      ft.run();
//      System.out.println(ft.get());
        System.out.println("-----------------------");

        Runnable runnable = new MyRunnableTarget();
        FutureTask<String> ft2 = new FutureTask<String>(runnable, "SUCCESS");
        executor.execute(ft2);
        System.out.println(ft2.get());

    }

}

class MyTarget implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        // TODO Auto-generated method stub
        int i = new Random().nextInt(1000);
        return i;
    }

}

class MyRunnableTarget implements Runnable {

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("Runnable is invoke...");
    }

}
