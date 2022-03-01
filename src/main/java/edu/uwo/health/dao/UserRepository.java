package edu.uwo.health.dao;

import edu.uwo.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.username = :username and u.password = :password")
    User findByUsernameAndPassword(@Param("username") String username,
                                   @Param("password") String password);

    @Query("select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("update User u set u.healthStatus = :healthstatus, u.updateDate = :updateDate where u.username = :username")
    int updateUserHealthStatus(
            @Param("username") String username,
            @Param("healthstatus") int healthstatus,
            @Param("updateDate") Date updateDate
    );
}
