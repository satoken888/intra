package jp.co.kawakyo.intra.Form;

import lombok.Data;

@Data
public class UserRegistForm {
	private Long id;
	private String username;
	private String password;
	private boolean enabled;
	private boolean admin;
}
