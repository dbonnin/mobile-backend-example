package com.dbonnin.mobile_backend;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dbonnin.mobile_backend.persistence.Car;
import com.dbonnin.mobile_backend.persistence.CarRepository;
import com.dbonnin.mobile_backend.persistence.User;
import com.dbonnin.mobile_backend.persistence.UserRepository;

@SpringBootApplication
public class MobileBackendApplication {

	@Autowired
    private CarRepository carRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 


	public static void main(String[] args) {
		SpringApplication.run(MobileBackendApplication.class, args);
	}

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            // Initialize Cars
            List<Car> cars = Arrays.asList(
                new Car(null, "Toyota", "Camry", 2022, "Japan"),
                new Car(null, "Honda", "Civic", 2021, "Japan"),
                new Car(null, "Ford", "Mustang", 2023, "USA"),
                new Car(null, "BMW", "X5", 2022, "Germany"),
                new Car(null, "Mercedes", "C-Class", 2023, "Germany"),
                new Car(null, "Volkswagen", "Golf", 2021, "Germany"),
                new Car(null, "Hyundai", "Tucson", 2022, "South Korea")
            );
            carRepository.saveAll(cars);

            // Initialize Users with BCrypt encrypted passwords
            List<User> users = Arrays.asList(
                new User(null, "john_doe", passwordEncoder.encode("pass123"), "john@example.com", "John", "Doe"),
                new User(null, "jane_smith", passwordEncoder.encode("pass456"), "jane@example.com", "Jane", "Smith"),
                new User(null, "bob_wilson", passwordEncoder.encode("pass789"), "bob@example.com", "Bob", "Wilson"),
                new User(null, "alice_brown", passwordEncoder.encode("passabc"), "alice@example.com", "Alice", "Brown")
            );
            userRepository.saveAll(users);

            System.out.println("Database initialized!");
            System.out.println("Inserted " + cars.size() + " cars");
            System.out.println("Inserted " + users.size() + " users");
        };
    }

}
