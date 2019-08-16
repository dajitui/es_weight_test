package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * Created on 2019/8/12.
 *
 * @author yangsen
 */
@Data
@Document(indexName = "item", type = "docs", shards = 1, replicas = 0)
public class Item {
    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title; //标题
    @Field(type = FieldType.Keyword)
    private String category;// 分类
    @Field(type = FieldType.Keyword)
    private Double weights; // 权重

    public Item(long l, String title, String s, double v) {
        this.id = l;
        this.title = title;
        this.category = s;
        this.weights = v;
    }
}

