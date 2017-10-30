/**
 * Created by zhangWeiJie on 2017/10/23.
 */
public interface Formula {
    double calculate(int a);
    default double sqrt(int a){
        return Math.sqrt(a);
    }
}
