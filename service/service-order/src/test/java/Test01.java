import org.junit.Test;

import java.util.UUID;


public class Test01 {

    @Test
    public void testUUID(){

        for (int i = 0; i <10 ; i++) {
            String substring = UUID.randomUUID().toString().replaceAll("-","").substring(0, 16);
            System.out.println(substring);
        }
    }

}
