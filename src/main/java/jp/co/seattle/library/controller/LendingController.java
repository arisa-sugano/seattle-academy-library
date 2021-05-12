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
import jp.co.seattle.library.service.LendingService;

@Controller //APIの入り口
public class LendingController {
    final static Logger logger = LoggerFactory.getLogger(LendingController.class);

    @Autowired
    private BooksService bookdService;

    @Autowired
    private LendingService lendingService;


    /**
     * 書籍を借りる
     * @param locale　ロケール情報
     * @param bookId　書籍ID
     * @param model　 モデル情報
     * @return        詳細画面
     */
    @Transactional
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST)
    public String rentBook(Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome detailsControler.java! The client locale is {}.", locale);

        //貸出中に「借りる」ボタンを押した時
        if (lendingService.lendCheck(bookId) == 1) {
            model.addAttribute("lendingError", "現在は借りることができません");

            model.addAttribute("bookDetailsInfo", bookdService.getBookInfo(bookId));

            return "details";
        }
        //貸出可の書籍を借りる
        lendingService.rentSystem(bookId);
        model.addAttribute("lending", "貸出中");

        model.addAttribute("bookDetailsInfo", bookdService.getBookInfo(bookId));

        return "details";

    }

    /**
     * 書籍を返す
     * @param locale　ロケール情報
     * @param bookId　書籍ID
     * @param model　 モデル情報
     * @return　      詳細画面
     */
    @Transactional
    @RequestMapping(value = "/returnBook", method = RequestMethod.POST)
    public String returnBook(Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome detailsControler.java! The client locale is {}.", locale);

        lendingService.returnSystem(bookId);
        model.addAttribute("lending", "貸出可");
        model.addAttribute("bookDetailsInfo", bookdService.getBookInfo(bookId));
        return "details";
    }

}
