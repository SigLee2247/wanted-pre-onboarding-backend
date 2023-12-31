package preonboarding.backend.domain.user.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import preonboarding.backend.domain.Auditable;

@Entity
@Getter
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ID", updatable = false)
    private Long id;
    @Column(length = 32)
    private String username;
    @Column(unique = true, length = 32)
    private String email;
    @Column(length = 32)
    private String password;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Recruit> recruitList = new ArrayList<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.recruitList = new ArrayList<>();
    }

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.recruitList = new ArrayList<>();
    }

    public User(Long userId) {
        this.id = userId;
    }

    public User() {
    }
}
