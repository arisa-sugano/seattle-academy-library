package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.ContactInfo;
import jp.co.seattle.library.service.BooksService;

@Controller //APIの入り口
public class ContactController {
	final static Logger logger = (Logger) LoggerFactory.getLogger(ContactController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * @param model　モデル情報
	 * @return
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.GET) //value＝actionで指定したパラメータ
	//RequestParamでname属性を取得
	public String contact(Model model) {
		return "contact";

	}

	/**
	 * @param locale　　ロケール情報
	 * @param inquirer お問い合わせ者
	 * @param email　　 メールアドレス
	 * @param content  お問い合わせ内容
	 * @param model　　 モデル情報
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/contactSystem", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String ContactSystem(Locale locale,
			@RequestParam("inquirer") String inquirer,
			@RequestParam("email") String email,
			@RequestParam("content") String content,
			Model model) {
		logger.info("Welcome contentSystem.java! The client locale is {}.", locale);

		// パラメータで受け取った書籍情報をDtoに格納する。
		ContactInfo ContactInfo = new ContactInfo();
		ContactInfo.setInquirer(inquirer);
		ContactInfo.setEmail(email);
		ContactInfo.setContent(content);

		booksService.ContactSystem(ContactInfo);
		model.addAttribute("okMessage", "🐶🐶🐶🐶🐶Thank you for your message! Anything elese?🐶🐶🐶🐶🐶");
		return"contact";
		
}
}
