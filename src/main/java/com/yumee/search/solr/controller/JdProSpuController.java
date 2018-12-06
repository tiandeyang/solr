package com.yumee.search.solr.controller;


import com.yumee.search.solr.entity.JdProSpu;
import com.yumee.search.solr.entity.spec.ProductSpec;
import com.yumee.search.solr.service.JdProSpuService;
import com.yumee.search.solr.solrdemo.SolrUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.noggit.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/solr")
public class JdProSpuController {
    //依赖注入
    @Autowired
    JdProSpuService jdProSpuService;

    //增加索引数据
    @RequestMapping(value = "/list")
    public  List<JdProSpu> pic(@Param("pageNumber") Integer startOfPage, @Param("pageSize")Integer numberOfPage) {
        //调用dao层
        List<JdProSpu> jdProSpuList = jdProSpuService.selectJdProSpuList(startOfPage,numberOfPage);
        List<JdProSpu> jdProSpuList1 =  new ArrayList<>();
        for (JdProSpu jdProSpu:jdProSpuList){
          JdProSpu jdProSpu1 =  new JdProSpu();
          jdProSpu1.setId(jdProSpu.getSpuid());
          jdProSpu1.setSpu_name(jdProSpu.getSpu_name());
          jdProSpuList1.add(jdProSpu1);
        }
        try {
               if (jdProSpuList1!=null){
                   SolrUtil.batchSaveOrUpdate(jdProSpuList1);
               }
        }catch (Exception e){
            e.printStackTrace();
        }
        return jdProSpuList;
    }

    // 搜索,分页效果
    @RequestMapping(value = "/query")
    public SolrDocumentList query(@Param("keywords") String keywords, @Param("startOfPage") int startOfPage, @Param("numberOfPage") int numberOfPage){
        SolrDocumentList documents = jdProSpuService.query(keywords,startOfPage,numberOfPage);
        return documents;
    }

    // 删除
    @RequestMapping(value = "/del")
    public int delSolr(@Param("spuid") String spuid){
        int i = jdProSpuService.delSolr(spuid);
        return i;
    }

    // 更新
    @RequestMapping(value = "/update")
    public int update(@Param("spuid") String spuid,@Param("spu_name") String spu_name){
        int i = jdProSpuService.update(spuid,spu_name);
        return i;
    }


    // 添加
    @RequestMapping(value = "/add")
    public int add(@Param("spuid") String spuid,@Param("spu_name") String spu_name,@Param("price") float price){
        int i = jdProSpuService.add(spuid,spu_name,price);
        return i;
    }


   //  由于spring的RequestParam注解接收的参数是来自于requestHeader中，即请求头，也就是在url中，格式为xxx?username=123&password=456，而RequestBody注解接收的参数则是来自于requestBody中，即请求体中

    // 添加
    @PostMapping(value = "/multiquery")
    public Object muatiquery(@RequestBody ProductSpec spec) throws IOException, SolrServerException {
       // System.out.println(JSONUtil.toJSON(spec));
        SolrDocumentList documents = jdProSpuService.queryMulti(spec);
        return documents;
    }









}
