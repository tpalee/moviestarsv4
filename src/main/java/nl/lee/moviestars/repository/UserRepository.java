package nl.lee.moviestars.repository;


import nl.lee.moviestars.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
