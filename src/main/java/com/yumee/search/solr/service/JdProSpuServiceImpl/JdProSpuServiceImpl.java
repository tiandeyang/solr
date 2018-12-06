package com.yumee.search.solr.service.JdProSpuServiceImpl;


import com.yumee.search.solr.dao.JdProSpuMapper;
import com.yumee.search.solr.entity.JdProSpu;
import com.yumee.search.solr.entity.spec.ProductSpec;
import com.yumee.search.solr.service.JdProSpuService;
import com.yumee.search.solr.solrdemo.SolrUtil;
import org.apache.logging.log4j.util.Strings;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.noggit.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JdProSpuServiceImpl implements JdProSpuService {

    @Autowired
    JdProSpuMapper jdProSpuMapper;

    public List<JdProSpu> selectJdProSpuList(Integer startOfPage,Integer numberOfPage){
        List<JdProSpu> jdProSpuList = jdProSpuMapper.selectJdProSpuList(startOfPage,numberOfPage);
        return jdProSpuList;
    }

    public SolrDocumentList query(String keywords, Integer startOfPage, Integer numberOfPage){
        SolrDocumentList solrDocumentList = null;
        //查询
        try{
            QueryResponse queryResponse = SolrUtil.query("spu_name:"+keywords,startOfPage,numberOfPage);
            solrDocumentList= queryResponse.getResults();

            System.out.println("累计找到的条数："+solrDocumentList.getNumFound());
            System.out.println(JSONUtil.toJSON(solrDocumentList));
        }catch (Exception e){
            e.printStackTrace();
        }
        return solrDocumentList;
    }


    @Override
    public SolrDocumentList queryMulti(String name, String category, String price, Integer page, String sort) throws IOException, SolrServerException {

      /*
      //创建查询数据对象（便于设置查询条件）
        SolrQuery solrQuery = new SolrQuery();
        //设置查询的域和值，这个在之后的项目中可以用于动态
        //方法一：参数q就代表query查询
        //solrQuery.set("q","name:佘超伟123");
        //方法二：(一般使用该方法)
        solrQuery.setQuery("name:佘超伟");
        //方法三：通过设置默认域
        //solrQuery.set("df", "name");
        //solrQuery.setQuery("佘超伟");

        //设置查询过滤条件(可以设置多个，只要域和值有改变就可以了)
        //solrQuery.set("fq", "id:haha123");
        //添加排序方式（可选内容）
        //solrQuery.addSort("需要排序的域",ORDER.asc);//升序
        //solrQuery.addSort("需要排序的域",ORDER.desc);//降序
        //设置分页处理(比如这是设置每次显示5个)
        solrQuery.setStart(0);
        solrQuery.setRows(5);
        //设置只查询显示指定的域和值(第二个参数可以是多个，之间用“逗号”分割)
        //solrQuery.set("fl", "name");
        //设置某域进行高亮显示
        */


        // 拼装查询条件
        SolrQuery query = new SolrQuery();

        //方法三：通过设置默认域
        //solrQuery.set("df", "name");
        //solrQuery.setQuery("佘超伟");
        query.set("df", "spu_name");
        // 查询条件
        if (null != name && !"".equals(name)) {
            query.setQuery(name);
        } else {
            query.setQuery("*:*");
        }
        // 商品分类名称过滤
       /* if (null != category && !"".equals(category)) {
            query.addFilterQuery("category:" + category);
        }*/
        // 价格区间过滤
        if (null != price && !"".equals(price)) {
            String[] strings = price.split("-");
            query.addFilterQuery("price:[" + strings[0] + " TO " + strings[1] + "]");
        }
        // 排序条件
        if ("1".equals(sort)) {
            query.setSort("price", SolrQuery.ORDER.desc);
        } else {
            query.setSort("price", SolrQuery.ORDER.asc);
        }


        if (null == page) {
            page = 1;
        }
        //设置默认搜索域
        query.setStart(page);
        query.setRows(10);
        // 高亮设置
        QueryResponse queryResponse = SolrUtil.queryMulti(query);
        SolrDocumentList results = queryResponse.getResults();
/*        query.setHighlight(true);
        query.addHighlightField("product_name");
        query.setHighlightSimplePre("<span style=\"color:red\">");
        query.setHighlightSimplePost("</span>");*/
        return results;
    }



    @Override
    public SolrDocumentList queryMulti(ProductSpec spec) throws IOException, SolrServerException {
      /*
      //创建查询数据对象（便于设置查询条件）
        SolrQuery solrQuery = new SolrQuery();
        //设置查询的域和值，这个在之后的项目中可以用于动态
        //方法一：参数q就代表query查询
        //solrQuery.set("q","name:佘超伟123");
        //方法二：(一般使用该方法)
        solrQuery.setQuery("name:佘超伟");
        //方法三：通过设置默认域
        //solrQuery.set("df", "name");
        //solrQuery.setQuery("佘超伟");

        //设置查询过滤条件(可以设置多个，只要域和值有改变就可以了)
        //solrQuery.set("fq", "id:haha123");
        //添加排序方式（可选内容）
        //solrQuery.addSort("需要排序的域",ORDER.asc);//升序
        //solrQuery.addSort("需要排序的域",ORDER.desc);//降序
        //设置分页处理(比如这是设置每次显示5个)
        solrQuery.setStart(0);
        solrQuery.setRows(5);
        //设置只查询显示指定的域和值(第二个参数可以是多个，之间用“逗号”分割)
        //solrQuery.set("fl", "name");
        //设置某域进行高亮显示
        */

        // 拼装查询条件
        SolrQuery query = new SolrQuery();

        //方法三：通过设置默认域
        //solrQuery.set("df", "name");
        //solrQuery.setQuery("佘超伟");
        query.set("df", "spu_name");
        // 查询条件
        if (Strings.isNotBlank(spec.getSpu_name())) {
            query.setQuery(spec.getSpu_name());
        } else {
            query.setQuery("*:*");
        }
//        // 商品分类名称过滤
//        if (Strings.isNotBlank(spec.getCategory())) {
//            System.out.println("category=="+spec.getCategory());
//            query.addFilterQuery("category:*" + spec.getCategory());
//        }
        // 价格区间过滤
        if (Strings.isNotBlank(spec.getPrice())) {
            String[] strings = spec.getPrice().split("-");
            query.addFilterQuery("price:[" + strings[0] + " TO " + strings[1] + "]");
        }
        // 排序条件
        if ("1".equals(spec.getSort())) {
            query.setSort("price", SolrQuery.ORDER.desc);
        } else {
            query.setSort("price", SolrQuery.ORDER.asc);
        }
        Integer page = spec.getPage();
        if (null == spec.getPage()) {
            page = 0;
        }
        //设置默认搜索域
        query.setStart(page);
        query.setRows(10);
        // 高亮设置
        QueryResponse queryResponse = SolrUtil.queryMulti(query);
        SolrDocumentList results = queryResponse.getResults();
/*        query.setHighlight(true);
        query.addHighlightField("product_name");
        query.setHighlightSimplePre("<span style=\"color:red\">");
        query.setHighlightSimplePost("</span>");*/
        return results;
    }


    public SolrDocumentList multi(String keywords, Integer startOfPage, Integer numberOfPage){
        // SolrQuery query = new SolrQuery();
        SolrDocumentList solrDocumentList = null;
        //查询
        try{
            QueryResponse queryResponse = SolrUtil.query("spu_name:"+keywords,startOfPage,numberOfPage);
            solrDocumentList= queryResponse.getResults();
            System.out.println("累计找到的条数："+solrDocumentList.getNumFound());
        }catch (Exception e){
            e.printStackTrace();
        }
        return solrDocumentList;
    }

    public int delSolr(String spuid){
        int i = 0;
        try {
          SolrUtil.deleteById(spuid);
          i++;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public int update(String spuid,String spu_name){
        int i = 0;
        JdProSpu jdProSpu = new JdProSpu();
        jdProSpu.setId(spuid);
        jdProSpu.setSpu_name(spu_name);
        try {
            SolrUtil.deleteById(spuid);
            SolrUtil.saveOrUpdate(jdProSpu);
            i++;
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    public int add(String spuid,String spu_name,float price){
        int i = 0;
        JdProSpu jdProSpu = new JdProSpu();
        jdProSpu.setId(spuid);
        jdProSpu.setSpu_name(spu_name);
        jdProSpu.setPrice(price);
        try {
            SolrUtil.saveOrUpdate(jdProSpu);
            i++;
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }
}
