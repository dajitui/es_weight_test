package com.example.demo.dao;

import com.example.demo.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created on 2019/8/16.
 *
 * @author yangsen
 */
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {
}
