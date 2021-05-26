package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * お問い合わせ内容格納DTO
 * @author user
 */

@Configuration
@Data
public class ContactInfo {

	private int id;

	private String inquirer;
	
	private String email;

	private String content;

	public ContactInfo() {

	}

	// コンストラクタ
	public ContactInfo(int id, String inquirer, String content) {
		this.id = id;
		this.inquirer = inquirer;
		this.content = content;

	}
}
