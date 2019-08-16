package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.dao.ArticleRepository;
import com.example.demo.dao.ItemRepository;
import com.example.demo.dao.LabelRepository;
import com.example.demo.entity.Article;
import com.example.demo.entity.Item;
import com.example.demo.entity.Label;
import com.example.demo.util.IdUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Resource
    ElasticsearchTemplate esTemplate;

    @Test
    public void insert() {
        esTemplate.createIndex(Article.class);
    }

    @Test
    public void aba() {
        esTemplate.createIndex(Item.class);
        //esTemplate.deleteIndex(Item.class);
    }

    @Autowired
    private ItemRepository itemRepository;



    /**
     * @Description:定义批量新增方法
     * @Author: https://blog.csdn.net/chen_2890
     */
    @Test
    public void insertList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(6L, "鸡车", " 哈哈",
                4D));
        list.add(new Item(7L, "鸡脖", "x",
                8.2D));
        list.add(new Item(8L, "鸡胸肉", " 吃",
                2.5D));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    @Test
    public void a() {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("title", "斩男"))
                .should(QueryBuilders.matchQuery("label", "斩男").minimumShouldMatch("100%"));
        //请求精度QueryBuilders.matchQuery("category","吃").minimumShouldMatch()

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        // 搜索，获取结果
        Page<Article> articles = this.articleRepository.search(searchQuery);
        // 总条数
        long total = articles.getTotalElements();
        System.out.println("总条数 = " + total);
        // 总页数
        System.out.println("总页数 = " + articles.getTotalPages());
        // 当前页
        System.out.println("当前页：" + articles.getNumber());
        // 每页大小
        System.out.println("每页大小：" + articles.getSize());

        for (Article article : articles) {
            System.out.println(JSON.toJSON(article));
        }
    }

    @Test
    public void search() {
        /*System.out.println(JSON.toJSON(itemRepository.findAll()));
        //倒序
        System.out.println(JSON.toJSON(itemRepository.findAll(Sort.by("weights").descending())));*/
        /*System.out.println(JSON.toJSON(itemRepository.findByWeightsBetween(0,1.5D)));
        System.out.println(JSON.toJSON(itemRepository.findByCategoryOrderByWeights("吃")));*/
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("title", "鸡胸"));
        // 分页：
        int page = 0;
        int size = 10;
        queryBuilder.withPageable(PageRequest.of(page, size));

        // 搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        // 总页数
        System.out.println("总页数 = " + items.getTotalPages());
        // 当前页
        System.out.println("当前页：" + items.getNumber());
        // 每页大小
        System.out.println("每页大小：" + items.getSize());

        for (Item item : items) {
            System.out.println(JSON.toJSON(item));
        }
    }

    @Resource
    ArticleRepository articleRepository;

    @Test
    public void insertxx() {
        List<Article> list = new ArrayList<>();
        String[] titles = {"美妆", "护肤", "口红", "眼影", "粉底液", "遮瑕液", "眼霜", "亮白", "清洁", "肿眼泡", "发型", "大小眼","底妆","懂彩妆","爱种草","单眼皮",
                            "礼物","好物分享","上新","内双"};
        String[] labels = {"脸基尼、DIY面膜…当代护肤迷惑行为大赏，笑skr人", "完子护肤Q&A:根据肤质选择适合自己的卸妆产品", "超全眼型大解析 | 眼妆一直画不好，原来是因为这个！",
                "分情况选择适合自己的面膜", "如何选择适合自己的卸妆产品", "学会堪比微整，化妆师最怕被偷师的7个技巧", "变美不用挨针！如何用最高性价比的单品，get水光肌？",
                "涨姿势 | 斩男必备的眼唇配色小心机，让你妆容美到犯规！", "全民沉迷哪吒仿妆？我却被这些超实用的清透眼妆圈粉了！", "敏感星人最爱的“全能”成分，补水修复舒缓一步到位！"
                ,"十分钟出门妆 | 直男无法分辨的超自然伪素颜！","如何用最高性价比的单品，get水光肌","气味贩卖 | 哪一种味道，能让ta立刻注意到你"
                ,"人无完人","拯救黄皮 | 什么妆容适合黄皮？看这篇教科书级示范~","6月减肉难题 | 带妆运动是作死还是精致，终于有了答案","当代忙碌女青年的夏天，请一切从简"
                ,"美到报警的12色动物盘，全4盘眼妆笔记"};
        for (int i = 0; i < 100; i++) {
            Article article = new Article();
            article.setId(IdUtils.getId());
            article.setTitle(titles[getRandom(titles.length)]+","+titles[getRandom(titles.length)]);
            article.setArticleId(IdUtils.getId());
            article.setLabel(labels[getRandom(labels.length)]);
            list.add(article);
        }
        // 接收对象集合，实现批量新增
        articleRepository.saveAll(list);
    }

    @Test
    public void dd(){
        Article article = new Article();
        article.setId(IdUtils.getId());
        article.setTitle("斩男");
        article.setArticleId(IdUtils.getId());
        article.setLabel("按司农爱上你才送");
        articleRepository.save(article);
    }


    public static int getRandom(int n) {
        return (int) (Math.random() * n);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.print(IdUtils.getId()+" "+IdUtils.getId());
        }
    }

}
