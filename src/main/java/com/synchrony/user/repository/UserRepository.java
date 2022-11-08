package com.synchrony.user.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synchrony.user.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsernameAndPassword(String username, String password);

	public User findByUsername(String username);

	@Query(value = "select * from users where email = 'atimpramanik3@gmail.com'", nativeQuery = true)
	User findByEmail(@Param("n") String email);


}
