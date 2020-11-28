import com.hlcx.command.Command01;
import com.hlcx.command.Command02;


import java.util.concurrent.ExecutionException;

public class Test {
    @org.junit.Test
    public void test_01() throws ExecutionException,InterruptedException {
        String result = new Command01("world01").execute();
        String result02 = new Command02("world02").execute();
        System.out.println(result+"  "+result02);

    }
}
