package liangyongqi.iam;

import liangyongqi.iam.Util.LifeCycle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IamApplication {

    public static void main(String[] args) {
        SpringApplication.run(IamApplication.class, args);
        LifeCycle.start();
    }

}

