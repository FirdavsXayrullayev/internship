package uz.intership.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.intership.model.CurrentUserModel;

@Repository
public interface CurrentUserRepository extends JpaRepository<CurrentUserModel, Integer> {
}
