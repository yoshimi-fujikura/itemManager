package com.example.form;

/*
 * 商品情報の入力データを受け取るフォームクラス。
 * 画面から送信されるリクエストパラメータをバインド
 */
public class ItemForm {
    private String name;

    private Integer price;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getPrice() {
        return this.price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }

}
