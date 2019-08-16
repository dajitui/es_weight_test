package com.example.demo.dao;

import com.example.demo.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created on 2019/8/12.
 *
 * @author yangsen
 */
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

    List<Item> findByWeightsBetween(double price1, double price2);

    List<Item> findByCategoryOrderByWeights(String category);

}
