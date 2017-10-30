/**
 * Created by zhangWeiJie on 2017/10/30.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * 批处理与任务执行时限
 在有些应用场景中，我们需要同时处理多个任务，并获取结果，使用上面的CompletionService将完成的任务与未完成的任务分隔开似乎能够解决，但是如果其中有一个任务相当耗时，就会影响整个批处理任务的完成速度。比如，在一个页面中，我们需要从多个数据源获取数据，并在页面展示，同时我们希望整个页面的加载过程不超过2秒，那么那些超过2秒没有响应成功的数据源数据则用默认值替换，ExecutorService提供了invokeAll( )来完成这个任务。
 下面我们通过一个示例演示invokeAll方法，程序中定义了3个任务，c1、c2、c3模拟执行时间分别为1、2、3秒，程序允许的最大执行时间是2秒，超过2秒的任务就会被取消。
 */
public class TestCompletionServiceLimitTime {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(5);

        Callable<Integer> c1 = new TestCompletionServiceLimitTime().new Target(1);
        Callable<Integer> c2 = new TestCompletionServiceLimitTime().new Target(2);
        Callable<Integer> c3 = new TestCompletionServiceLimitTime().new Target(3);

        List<Callable<Integer>> list = new ArrayList<>();
        list.add(c1);
        list.add(c2);
        list.add(c3);

        try {
            List<Future<Integer>> res = es.invokeAll(list, 2, TimeUnit.SECONDS);

            Iterator<Future<Integer>> it = res.iterator();
            while(it.hasNext()) {
                Future<Integer> f = it.next();
                int i = f.get();
                System.out.println(i);
            }
        } catch (CancellationException e ) {
            System.out.println("任务取消");
        } catch (InterruptedException e) {
            System.out.println("中断异常");
        } catch (ExecutionException e) {
            System.out.println("执行异常");
        }

    }
    public class Target implements Callable<Integer> {

        private int a = 0;

        public Target(int a) {
            // TODO Auto-generated constructor stub
            this.a = a;
        }

        @Override
        public Integer call() throws Exception {
            // TODO Auto-generated method stub
            Thread.sleep(1000*a);
            return a;
        }

    }
}

