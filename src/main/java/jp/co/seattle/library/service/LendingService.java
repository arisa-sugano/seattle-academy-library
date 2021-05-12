package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
     * 書籍サービス
     *  
     *  lendingテーブルに関する処理を実装する
     */

@Service
public class LendingService {

    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * テーブルに書籍があるか確認
     * @param bookId 書籍
     * @return       繊維先画面
     */
    public int lendCheck(int bookId) {
        String sql = "select count(*) from lending where bookId=" + bookId + ";";
        int lendCheck = jdbcTemplate.queryForObject(sql, Integer.class);
        return lendCheck;

    }

    /**
     * 書籍を貸出中デーブルに追加
     * @param bookId　書籍ID
     */
    public void rentSystem(int bookId) {
        String sql = "INSERT INTO lending (bookId) VALUES (" + bookId + ");";
        jdbcTemplate.update(sql);

    }

    /**
     * 書籍を貸出中デーブルから消去
     * @param bookId  書籍ID
     */
    public void returnSystem(int bookId) {
        String sql = "DELETE FROM lending where bookId =" + bookId + ";";
        jdbcTemplate.update(sql);

    }

}
