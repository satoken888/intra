package jp.co.kawakyo.intra.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kawakyo.intra.Entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	public Account findByUsernameIsAndEnabledTrue(String username);
	
	public List<Account> findAllByEnabledTrue();
	
	@Transactional
	@Modifying
	@Query("update Account a set a.enabled = false where a.id = :id")
	public int deleteUserById(Long id);
}
