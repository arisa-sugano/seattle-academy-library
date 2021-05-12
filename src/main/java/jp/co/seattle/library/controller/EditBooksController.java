package jp.co.seattle.library.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.LendingService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * Handles requests for the application home page.
 */

@Controller //APIの入り口
public class EditBooksController {
    final static Logger logger = LoggerFactory.getLogger(EditBooksController.class);

    @Autowired
    private BooksService booksService;

    @Autowired
    private ThumbnailService thumbnailService;

    @Autowired
    private LendingService lendingService;

    //deatils.jsbの編集ボタンを押すとここに飛ぶ
    //POST：更新 GET:情報の取得

    /**
     * 書籍情報を編集する
     * @param locale　ロケール情報
     * @param bookId　書籍ID
     * @param model　　モデル
     * @return　　繊維先画面
     */
    @RequestMapping(value = "/editBook", method = RequestMethod.POST) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String login(Locale locale, @RequestParam("bookId") Integer bookId, Model model) {

        BookDetailsInfo newIdInfo = booksService.getBookInfo(bookId);
        model.addAttribute("bookDetailsInfo", newIdInfo);

        return "editBook";
    }

    //詳細画面⇨編集ボタン⇨詳細情報ががいている編集画面⇨更新⇨更新された情報を出力
    @Transactional
    //編集画面の更新ボタンの話
    @RequestMapping(value = "/updateBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String updateBook(Locale locale,
            //BookIDは？
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("publishe_Date") String publishDate,
            @RequestParam("thumbnail") MultipartFile file,
            @RequestParam("ISBN") String ISBN,
            @RequestParam("description") String description,
            @RequestParam("bookId") Integer bookId,

            Model model) {
        logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

        // パラメータで受け取った書籍情報をDtoに格納する。
        BookDetailsInfo bookInfo = new BookDetailsInfo();
        bookInfo.setTitle(title);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setPublishDate(publishDate);
        bookInfo.setDescription(description);
        bookInfo.setISBN(ISBN);
        bookInfo.setBookId(bookId);

        boolean isIsbnForCheck = ISBN.matches("(^\\d{10}|\\d{13}$)?");

        boolean check = false;

        if (!isIsbnForCheck) {
            model.addAttribute("error", "10字または13字以内の数字を入力してください");
            check = true;

        }

        try {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            df.setLenient(false);
            df.parse(publishDate);

        } catch (ParseException p) {
            model.addAttribute("error1", "年月日を入力してください");
            check = true;

        }

        if (check) {
            return "editBook";
        }

        // クライアントのファイルシステムにある元のファイル名を設定する
        String thumbnail = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                // サムネイル画像をアップロード
                String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
                // URLを取得
                String thumbnailUrl = thumbnailService.getURL(fileName);

                bookInfo.setThumbnailName(fileName);
                bookInfo.setThumbnailUrl(thumbnailUrl);

            } catch (Exception e) {

                // 異常終了時の処理
                logger.error("サムネイルアップロードでエラー発生", e);
                model.addAttribute("bookDetailsInfo", bookInfo);
                return "editBook";
            }
        }

        // 書籍情報を更新する
        if (!file.isEmpty()) {
            booksService.updateBook(bookInfo);
        }

        if (file.isEmpty()) {
            booksService.nullThumbnail(bookInfo);
        }

        BookDetailsInfo newIdInfo = booksService.getBookInfo(bookId);
        model.addAttribute("bookDetailsInfo", newIdInfo);

        //貸出ステータス引き継ぎ
        if (lendingService.lendCheck(bookId) == 1) {
            model.addAttribute("lending", "貸出中");
        }

        if (lendingService.lendCheck(bookId) == 0) {
            model.addAttribute("lending", "貸出可");
        }

        return "details";

    }
}
