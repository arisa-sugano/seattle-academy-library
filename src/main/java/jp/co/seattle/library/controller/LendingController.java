package jp.co.seattle.library.controller;

import java.util.Locale;

/**
 * 貸出コントローラー
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;

@Controller //APIの入り口
public class LendingController {
    final static Logger logger = LoggerFactory.getLogger(LendingController.class);

    @Autowired
    private BooksService bookdService;

    /**
     * 対象書籍を貸出中にする
     *
     * @param locale ロケール情報
     * @param bookId 書籍ID
     * @param model モデル情報
     * @return "details";
     */

    @Transactional
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST)
    public String rentBook(Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome detailsControler.java! The client locale is {}.", locale);

        return "details";

    }

    @Transactional
    @RequestMapping(value = "/returnBook", method = RequestMethod.POST)
    public String returnBook(Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome detailsControler.java! The client locale is {}.", locale);

        return "details";
    }
}
