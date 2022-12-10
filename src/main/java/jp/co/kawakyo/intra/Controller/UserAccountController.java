package jp.co.kawakyo.intra.Controller;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.kawakyo.intra.Entity.Account;
import jp.co.kawakyo.intra.Service.UserAccountService;
import jp.co.kawakyo.intra.Form.UserEditForm;
import jp.co.kawakyo.intra.Form.UserRegistForm;
import jp.co.kawakyo.intra.Form.UserSearchForm;

@Controller
public class UserAccountController {
	
	@Autowired
	UserAccountService userAccountService;
	
	@GetMapping("/user")
	public String user(UserSearchForm form, Model model) {
		model.addAttribute("userSearchForm", form);
		return "user";
	}
	
	@PostMapping("/userSearch")
	public String userSearch(UserSearchForm form, Model model) {
		ArrayList<Account> userList = new ArrayList<Account>();
		if(StringUtils.isEmpty(form.getUsername())) {
			//入力が空の場合は全検索
			userList = new ArrayList<Account>(userAccountService.findAllEnabledTrue());
		} else {
			//入力されている場合は入力されてる名前で検索
			Account userAccount = userAccountService.findByUserName(form.getUsername());
			if(userAccount != null) { 
				userList.add(userAccount);
			} 
		}
		
		model.addAttribute("userList",userList);
		return "user";
	}

	@GetMapping("/userRegist")
	public String userRegistView(UserRegistForm form,Model model) {
		return "userRegist";
	}

	@PostMapping("/userRegist")
	public String userRegist(UserRegistForm form, Model model) {
		userAccountService.save(form);
		model.addAttribute("alertMessage", "登録完了しました。");
		return "user";

	}
	
	@GetMapping("/userEdit")
	public String userEdit(UserEditForm form, @RequestParam("id") Long id, Model model) {
		Account ac = userAccountService.findById(id);
		form = castUserEditFormFromAccount(ac);
		model.addAttribute("userEditForm",form);
		return "userEdit";
	}
	
	@PostMapping("/userEdit")
	public String userEdit(UserEditForm form, Model model) {
		userAccountService.save(form);
		model.addAttribute("alertMessage", "登録完了しました");
		return "userEdit";
	}
	
	@PostMapping("/userDelete")
	public String userDelete(@RequestParam("id") Long id,UserSearchForm form, Model model) {
		userAccountService.delete(id);
		model.addAttribute("alertMessage", "削除完了しました");
		return "user";
	}
	
	private UserEditForm castUserEditFormFromAccount(Account ac) {
		UserEditForm retForm = new UserEditForm();
		
		retForm.setId(ac.getId());
		retForm.setUsername(ac.getUsername());
		retForm.setPassword(ac.getPassword());
		retForm.setEnabled(ac.isEnabled());
		retForm.setAdmin(ac.isAdmin());
		
		return retForm;
	}
}
