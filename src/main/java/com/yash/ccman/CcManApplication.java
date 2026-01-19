package com.yash.ccman;
// TO Start mysql: sudo systemctl start mysql
// To login into mysql: sudo mysql -u root -p
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CcManApplication {

	public static void main(String[] args) {
        SpringApplication.run(CcManApplication.class, args);
	}

}
