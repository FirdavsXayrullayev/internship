package uz.intership.repository;

import org.springframework.data.repository.CrudRepository;
import uz.intership.model.UserSession;

public interface UserSessionRepo extends CrudRepository<UserSession, String> {
}
