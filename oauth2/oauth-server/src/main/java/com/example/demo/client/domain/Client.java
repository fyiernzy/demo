package com.example.demo.client.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private Role role;

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        Class<?> oEffectiveClass = object instanceof HibernateProxy
                                   ? ((HibernateProxy) object).getHibernateLazyInitializer()
                                       .getPersistentClass()
                                   : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                                      ? ((HibernateProxy) this).getHibernateLazyInitializer()
                                          .getPersistentClass()
                                      : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        Client client = (Client) object;
        return getId() != null && Objects.equals(getId(), client.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
               ? ((HibernateProxy) this).getHibernateLazyInitializer()
                   .getPersistentClass().hashCode()
               : getClass().hashCode();
    }
}
