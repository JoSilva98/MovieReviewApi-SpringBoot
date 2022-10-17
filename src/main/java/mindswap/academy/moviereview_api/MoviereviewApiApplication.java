package mindswap.academy.moviereview_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableCaching
public class MoviereviewApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviereviewApiApplication.class, args);
    }
}
