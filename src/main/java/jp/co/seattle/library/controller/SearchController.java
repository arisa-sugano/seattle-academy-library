package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;




@Controller
public class SearchController {
    final static Logger logger = LoggerFactory.getLogger(SearchController.class);


    @Autowired
    private BooksService booksService;




    /**
     * 書籍検索機能
     * @param locale　ローカル情報
     * @param search　検索ワード
     * @param model　 モデル情報
     * @return        遷移先画面
     */
    @RequestMapping(value = "/searchBooks", method = RequestMethod.POST)
    public String searchingBooks(Locale locale,
            @RequestParam("search") String search,
            Model model) {

        model.addAttribute("search", search);
        model.addAttribute("bookList", booksService.getSearchList(search));
        return "home";

    }

}




