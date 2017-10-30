/**
 * Created by zhangWeiJie on 2017/10/30.
 */
import java.util.Random;
import java.util.concurrent.*;

public class CallableAndFuture {

    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(5);
        Callable<Integer> c1 = new Target(false);
        Callable<Integer> c2 = new Target(true);

        Future<Integer> f1 = es.submit(c1);
        Future<Integer> f2 = es.submit(c2);

        int res = 0;
        try {
            res = f1.get();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean isCancelled = f1.isCancelled();
        boolean isDone = f1.isDone();

        System.out.println(res);
        System.out.println(isCancelled);
        System.out.println(isDone);

        System.out.println("---------------------------");

        try {
            boolean cancel = f2.cancel(false);
            int res2 = f2.get();
            isCancelled = f1.isCancelled();
            isDone = f1.isDone();

            System.out.println(res2);
            System.out.println(cancel);
            System.out.println(isCancelled);
            System.out.println(isDone);
        } catch (CancellationException e) {
            // TODO Auto-generated catch block
            System.out.println("任务被取消.");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            System.out.println("任务被中断.");
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            System.out.println("任务执行异常.");
        }

    }

}

class Target implements Callable<Integer> {

    private boolean sleep = false;

    public Target(boolean sleep) {
        // TODO Auto-generated constructor stub
        this.sleep = sleep;
    }

    @Override
    public Integer call() throws Exception {
        // TODO Auto-generated method stub
        if(sleep) {
            Thread.sleep(8000);
        }
        int i = new Random().nextInt(1000);
        return i;
    }

}
