package com.example.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Entity.Item;
import com.example.Repository.ItemRepository;
import com.example.form.ItemForm;

@Service
public class ItemService {
	//@Autowired
	//ItemRepository itemRepository;
	
	//Spring推奨のコンストラクタでの注入
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
	
   //データ一覧取得
    public List<Item> findAll() {
        return this.itemRepository.findAll();
    }
    
    // 登録処理
    public Item save(ItemForm itemForm) {
        // Entityクラスのインスタンスを生成
        Item item = new Item();
        // フォームフィールドに、値をセット
        item.setName(itemForm.getName());
        item.setPrice(itemForm.getPrice());
        // Spring　Data　JPAのsaveメソッドを利用してデータを保存
        return this.itemRepository.save(item);
    }
    
    //更新処理
    //カラム：IDをもとにデータ検索を行いそのデータの更新を行う
    public Item findById(Integer id) {
    	//URLパスに含まれる「employeeId」 に対するデータを取得
        Optional<Item> optionalItem = this.itemRepository.findById(id);
        //idが入力されているか確認
        if(optionalItem.isPresent()) {
            // OptionalからEntityクラスの取得を試みる
        	Item item  = optionalItem.get();
            return item;
        }
        // 値がなければnullを返す
        //TODO 例外処理追加
        return this.itemRepository.findById(id).orElse(null);
    }
    //更新処理
    public Item update(Integer id, ItemForm itemForm) {
    	//画面で入力されたIDに紐づく商品をfindByIDをしようして、1レコード取得
    	Item item = this.findById(id);
    	//itemFormの値をエンティティに格納することで、Jpaのsaveメソッドにて、データの保存を行う
    	item.setName(itemForm.getName());
        item.setPrice(itemForm.getPrice());
        return this.itemRepository.save(item);
    }
    
    //削除処理
    public void delete(Integer id) {
    	this.itemRepository.deleteById(id);
    	
    }
    
    
}
