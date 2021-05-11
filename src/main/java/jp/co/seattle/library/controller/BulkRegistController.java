package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * Handles requests for the application home page.
 */

@Controller //APIの入り口
public class BulkRegistController {

    final static Logger logger = LoggerFactory.getLogger(BulkRegistController.class);

    @Autowired
    private BooksService booksService;

    @Autowired
    private ThumbnailService thumbnailService;

    /**
     * jspからメソッドを取得
     * @param model モデル
     * @return      一括登録画面に遷移
     */
    @RequestMapping(value = "/bulkRegist", method = RequestMethod.GET) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String login(Model model) {
        return "bulkRegist";
    }

    /**
     * 一括登録機能
     * @param locale
     * @param uploadFile csvファイルを読み込み
     * @param model      モデル
     * @return           繊維先画面
     */
    @Transactional
    @RequestMapping(value = "/bulkRegistSystem", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String bulkRegistSystem(Locale locale,
            @RequestParam("csvFile") MultipartFile uploadFile, Model model) {

        logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

        try (InputStream stream = uploadFile.getInputStream(); //ファイル読み込み
                Reader reader = new InputStreamReader(stream); //バイト型から文字で読めるように変換
                BufferedReader buf = new BufferedReader(reader);)//１行ずつ読み込み
        {

            //バリデーションチェックが終わったデータを格納するリスト
            ArrayList<BookDetailsInfo> bookList = new ArrayList<BookDetailsInfo>();

            //エラーがある行を格納するリスト
            ArrayList<String> errorMsg = new ArrayList<String>();
            int errorcheck = 1;

            String line;
            //nullになるまで１行ごと（leadline）確認
            while ((line = buf.readLine()) != null) {
                //配列を作成
                String[] data = new String[6];

                // lineをカンマで分割し、配列リストdataに追加
                data = line.split(",", -1);

                BookDetailsInfo bookInfo = new BookDetailsInfo();

                bookInfo.setDescription(data[5]);

                boolean check = false;

                //必須項目チェック
                if (StringUtils.isEmpty(data[0]) ||
                        StringUtils.isEmpty(data[1]) ||
                        StringUtils.isEmpty(data[2]) ||
                        StringUtils.isEmpty(data[3])) {

                    check = true;

                }
                //必須項目格納
                bookInfo.setTitle(data[0]);
                bookInfo.setAuthor(data[1]);
                bookInfo.setPublisher(data[2]);

                //発行日バリデーションチェック
                try {
                    DateFormat df = new SimpleDateFormat("yyyyMMdd");
                    df.setLenient(false);
                    df.parse(data[3]);

                    bookInfo.setPublishDate(data[3]);

                } catch (ParseException p) {
                    model.addAttribute("error1", "年月日を入力してください");
                    check = true;

                }
                //ISBNバリデーションチェック
                boolean isIsbnForCheck = data[4].matches("(^\\d{10,13}$)?");

                if (!isIsbnForCheck) {
                    model.addAttribute("error", "10字または13字以内の数字を入力してください");
                    check = true;

                }
                bookInfo.setISBN(data[4]);

                if (check) {

                    errorMsg.add(errorcheck + "行目の書籍情報登録でバリデーションエラー");


                }

                bookList.add(bookInfo);

                errorcheck++;
            }

            if (!CollectionUtils.isEmpty(errorMsg)) {
                model.addAttribute("errorMessage", errorMsg);
                return "bulkRegist";
            }
            for (BookDetailsInfo list : bookList) {
                booksService.registBook(list);
            }

            model.addAttribute("resultMessage", "登録完了");

            return "bulkRegist";

        } catch (Exception e) {
            return "bulkRegist";

    }
}
}