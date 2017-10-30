/**
 * Created by zhangWeiJie on 2017/10/30.
 */
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

/**
 * CompletionService
 有时候我们需要利用executor去执行一批任务，每个任务都有一个返回值，利用Future就可以解决这个问题，为此我们需要保存每个任务提交后的Future，然后依次调用get方法轮询，获得已经执行完毕的任务的结果，这样的过程显得无趣。我们希望一次提交一批任务后，executor执行结束也是返回给我们一个已经执行完毕的Future集合。
 CompletionService整合了Executor和BlockingQueue的功能。你可以将一批Callable任务交给它去执行，然后使用类似于队列中的take和poll方法，在结果完成时获得这个结果，就像一个打包的Future。将生产新的异步任务与使用已完成任务的结果分离开来的服务。生产者submit方法 执行的任务。使用者 take 已完成的任务，并按照完成这些任务的顺序处理它们的结果。ExecutorCompletionService类是一个实现了CompletionService接口的实现类，它将计算任务交给一个传入的Executor去执行。
 下面是一个ExecutorCompletionService的示例
 */
public class TestCompletionService {

    private class Target implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            // TODO Auto-generated method stub
            int i = new Random().nextInt(1000);
            return i;
        }

    }

    public static void main(String[] args) throws Exception {
        Executor executor = Executors.newFixedThreadPool(5);
        ExecutorCompletionService<Integer> ecs = new ExecutorCompletionService<>(executor);

        Callable<Integer> c1 = new TestCompletionService().new Target();
        Callable<Integer> c2 = new TestCompletionService().new Target();
        Callable<Integer> c3 = new TestCompletionService().new Target();

        ecs.submit(c1);
        ecs.submit(c2);
        ecs.submit(c3);
/**
 * 这样将Future分离开来，已经完成的任务的Future就会被加入到BlockingQueue中供用户直接获取。
 关于poll方法和get方法的区别，poll方法是非阻塞的，有则返回，无则返回NULL，take方法是阻塞的，没有的话则会等待。
 */
        System.out.println(ecs.take().get());
        System.out.println(ecs.poll().get());
        System.out.println(ecs.take().get());

    }

}
