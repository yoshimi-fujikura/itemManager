package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Entity.Item;
import com.example.Service.ItemService;
import com.example.form.ItemForm;

/*
 * 商品管理画面の処理を制御
 * 商品の一覧表示、登録、編集、削除に関するリクエストを受け付け
 */
@Controller
@RequestMapping("/item")
public class ItemController {
	//コンストラクタインジェクション
    private final ItemService itemService;
    //@Autowiredコンストラクタが1つの場合省略可能
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
	
    // 商品一覧の表示
    @GetMapping
    public String index(Model model) {
    	List<Item> items  = this.itemService.findAll();
    	model.addAttribute("items",items);
        return "item/index";
    }

    // 商品登録ページ表示用
    @GetMapping("toroku")
    //@ModelAttributeでフォームとJavaオブジェクトを紐付け（データバインディング）
    public String torokuPage(@ModelAttribute ItemForm itemForm) {
        return "item/torokuPage";
    }

    // 商品登録の実行
    @PostMapping("toroku")
    public String toroku(ItemForm itemForm) {
    	//フォームの値でデータを登録する
    	this.itemService.save(itemForm);
        // 登録後は、一覧ページにリダイレクト。
        return "redirect:/item";
    }

    // 商品編集ページ
    @GetMapping("henshu/{id}")
    public String henshuPage(@PathVariable Integer id, Model model , @ModelAttribute ItemForm itemForm) {
        // 入力した、IDをもとに検索
        Item item = this.itemService.findById(id);;
        //空の場合メッセージを表示
        if (item == null) {
            model.addAttribute("message", "該当するデータはありませんでした。");
            return "item/notFound"; 
        }
        //IDがある場合は、更新
        itemForm.setName(item.getName());
        itemForm.setPrice(item.getPrice());
        
        model.addAttribute("id", id);
        return "item/henshuPage";
    }

    // 商品編集の実行
    @PostMapping("henshu/{id}")
    public String henshu(@PathVariable Integer id, @ModelAttribute ItemForm itemForm) {
        this.itemService.update(id,itemForm);

        return "redirect:/item";
    }

    // 商品削除の実行
    @PostMapping("sakujo/{id}")
    public String sakujo(@PathVariable Integer id) {
        this.itemService.delete(id);
        return "redirect:/item";
    }
	
}
