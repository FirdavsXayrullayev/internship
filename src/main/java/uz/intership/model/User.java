package uz.intership.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "userSeqId")
    @SequenceGenerator(name = "userSeqId", sequenceName = "user_id_seq", allocationSize = 1)
    private Integer id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role = "USER";

}
