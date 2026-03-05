package fhnw.IT_Project.Majesty_BIT;

import org.springframework.boot.SpringApplication;

public class TestMajestyBitApplication {

	public static void main(String[] args) {
		SpringApplication.from(MajestyBitApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
