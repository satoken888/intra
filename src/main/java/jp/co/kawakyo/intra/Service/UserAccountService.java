package jp.co.kawakyo.intra.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kawakyo.intra.Entity.Account;
import jp.co.kawakyo.intra.Entity.UserAccount;
import jp.co.kawakyo.intra.Repository.AccountRepository;
import jp.co.kawakyo.intra.Form.UserEditForm;
import jp.co.kawakyo.intra.Form.UserRegistForm;

@Service
@Transactional
public class UserAccountService implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(UserAccountService.class);

	@Autowired
	private AccountRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null || "".equals(username)) {
			throw new UsernameNotFoundException("Username is empty");
		}

		Account ac = repository.findByUsernameIsAndEnabledTrue(username);
		if (ac == null) {
			throw new UsernameNotFoundException("User not found: " + username);
		}

		if (!ac.isEnabled()) {
			throw new UsernameNotFoundException("User not found: " + username);
		}

		UserAccount user = new UserAccount(ac, getAuthorities(ac));

		return user;
	}

	public List<Account> findAll() {
		return repository.findAll();
	}

	public List<Account> findAllEnabledTrue() {
		return repository.findAllByEnabledTrue();
	}

	public Account findById(Long id) {
		return repository.findById(id).get();
	}

	public Account findByUserName(String userName) {
		return repository.findByUsernameIsAndEnabledTrue(userName);
	}

	public void save(UserRegistForm form) {
		Date today = new Date();
		Account ac = new Account(0L, form.getUsername(), passwordEncoder.encode(form.getPassword()), form.isEnabled(),
				form.isAdmin(), null, today);
		repository.saveAndFlush(ac);
	}

	public void save(UserEditForm form) {
		Account ac = repository.findById(form.getId()).get();
		ac.setUsername(form.getUsername());
		//パスワードが入力されている場合はパスワードも更新
		if(StringUtils.isNotEmpty(form.getPassword())){
			ac.setPassword(passwordEncoder.encode(form.getPassword()));
		}
		// ac.setPassword(passwordEncoder.encode(form.getPassword()));
		ac.setEnabled(form.isEnabled());
		ac.setAdmin(form.isAdmin());
		ac.setUpdateDate(new Date());
		repository.saveAndFlush(ac);
	}

	/**
	 * ユーザー情報の論理削除
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		int result = repository.deleteUserById(id);
		// logger.info("delete complete? : " + result);
		// result : 1
	}

	private Collection<GrantedAuthority> getAuthorities(Account account) {

		if (account.isAdmin()) {
			return AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
		} else {
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}

	}

	public void registerAdmin(String username, String password) {
		Account user = new Account(username, passwordEncoder.encode(password), true);
		repository.save(user);
	}

	public void registerUser(String username, String password) {
		Account user = new Account(username, passwordEncoder.encode(password), false);
		repository.save(user);
	}

}
