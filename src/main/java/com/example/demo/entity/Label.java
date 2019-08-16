package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created on 2019/8/16.
 *
 * @author yangsen
 */
@Data
@Document(indexName = "label", type = "docs", shards = 1, replicas = 0)
public class Label {

    @Id
    private Long id;

    /**
     * 标签
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    /**
     * 文章id
     */
    @Field(type = FieldType.Keyword)
    private String articleId;



}
