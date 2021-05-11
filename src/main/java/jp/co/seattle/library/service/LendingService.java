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

}
