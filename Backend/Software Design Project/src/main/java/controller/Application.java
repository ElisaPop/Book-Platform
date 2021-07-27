package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import utils.CryptoUtils;

@CrossOrigin(origins = "http://localhost:8081")
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {

        String hashPassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash("parola1"));
        System.out.println(hashPassword);
        SpringApplication.run(Application.class, args);
    }

}
