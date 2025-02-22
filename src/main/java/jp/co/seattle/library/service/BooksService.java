package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.dto.ContactInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 *  * 書籍サービス
 * @author user
 *
 */
@Service
public class BooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 取得したい情報を取得するようにSQLを修正
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT id ,title ,author ,publisher ,publish_date, thumbnail_url FROM books ORDER BY title ASC",
				new BookInfoRowMapper());

		return getedBookList;
	}

	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {

		// JSPに渡すデータを設定する
		String sql = "SELECT * FROM books where id ="
				+ bookId;

		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

		return bookDetailsInfo;
	}

	/**
	 * 書籍を登録する
	 *
	 * @param bookInfo 書籍情報
	 */
	public void registBook(BookDetailsInfo bookInfo) {

		String sql = "INSERT INTO books (title, author,publisher,publish_Date,thumbnail_name,thumbnail_url,reg_date,upd_date,description,ISBN) VALUES ('"
				+ bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
				+ bookInfo.getPublishDate() + "','"
				+ bookInfo.getThumbnailName() + "','"
				+ bookInfo.getThumbnailUrl() + "',"
				+ "sysdate(),"
				+ "sysdate()," + "'"
				+ bookInfo.getDescription() + "','"
				+ bookInfo.getISBN() + "');";

		jdbcTemplate.update(sql);
	}

	public int getNewestId() {
		String sql = "select max(ID) from books;";
		int newestId = jdbcTemplate.queryForObject(sql, Integer.class);
		return newestId;
	}

	public void deletingSystem(int bookId) {
		String sql = "DELETE FROM books where id =" + bookId + ";";
		jdbcTemplate.update(sql);

	}

	/**
	 * 書籍情報を編集する
	 * @param bookInfo　書籍情報
	 */

	public void updateBook(BookDetailsInfo bookInfo) {

		String sql = "update books set title='" + bookInfo.getTitle()
				+ "', author='" + bookInfo.getAuthor()
				+ "',publisher='" + bookInfo.getPublisher()
				+ "',publish_Date='" + bookInfo.getPublishDate()
				+ "',thumbnail_name='" + bookInfo.getThumbnailName()
				+ "',thumbnail_Url='" + bookInfo.getThumbnailUrl()
				+ "',upd_date=sysdate()"
				+ ",description='" + bookInfo.getDescription()
				+ "',ISBN='" + bookInfo.getISBN()
				+ "'where ID =" + bookInfo.getBookId() + ";";

		jdbcTemplate.update(sql);
	}

	//もしサムネの変更がなかったとき
	/**
	 * サムネイルの変更がなかったときに元のデータを更新する
	 * @param bookInfo　書籍情報
	 */
	public void nullThumbnail(BookDetailsInfo bookInfo) {

		String sql = "update books set title='" + bookInfo.getTitle()
				+ "', author='" + bookInfo.getAuthor()
				+ "',publisher='" + bookInfo.getPublisher()
				+ "',publish_Date='" + bookInfo.getPublishDate()
				+ "',upd_date=sysdate()"
				+ ",description='" + bookInfo.getDescription()
				+ "',ISBN='" + bookInfo.getISBN()
				+ "'where ID =" + bookInfo.getBookId() + ";";

		jdbcTemplate.update(sql);
	}

	//書籍検索機能
	public List<BookInfo> getSearchList(String search) {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT id,title,author,publisher,publish_date,thumbnail_url from books where title or author like '%"
						+ search + "%' ORDER BY title asc",
				new BookInfoRowMapper());
		return getedBookList;
	}

	/**
	 * お問い合わせ情報をcontentTableに格納する
	 * @param ContactInfo
	 */
	public void ContactSystem(ContactInfo ContactInfo) {

		String sql = "INSERT INTO contact (inquirer, email, content) VALUES ('"
				+ ContactInfo.getInquirer() + "','"+ ContactInfo.getEmail() +"','"+ ContactInfo.getContent() + "');";

		jdbcTemplate.update(sql);
	}
}
