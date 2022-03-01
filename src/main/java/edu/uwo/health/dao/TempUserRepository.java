package edu.uwo.health.dao;

import edu.uwo.health.entity.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TempUserRepository extends JpaRepository<TempUser, Integer> {

    @Query("select tu from TempUser tu where tu.username = :username")
    TempUser findByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("update TempUser tu set tu.verifyCode = :verifyCode where tu.username = :username")
    int updateTempUserByUsername(@Param("username") String username, @Param("verifyCode") String verifyCode);

    @Transactional
    @Modifying
    @Query("delete from TempUser tu where tu.username = :username")
    int deleteTempUserByUsername(@Param("username") String username);
}
