package com.ossovita.realestateapp.core.dataAccess;

import com.ossovita.realestateapp.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users ORDER BY random() LIMIT 1", nativeQuery = true)
    User getRandomUser();

}
