package com.yumee.search.solr.service;

import com.yumee.search.solr.entity.JdProSpu;
import com.yumee.search.solr.entity.spec.ProductSpec;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.List;

public interface JdProSpuService {

    List<JdProSpu> selectJdProSpuList(Integer startOfPage, Integer numberOfPage);

    SolrDocumentList query(String keywords, Integer startOfPage, Integer numberOfPage);

    SolrDocumentList queryMulti(String queryString, String catalog_name, String price, Integer page, String sort) throws IOException, SolrServerException;

    SolrDocumentList queryMulti(ProductSpec spec) throws IOException, SolrServerException;

    int delSolr(String spuid);

    int update(String spuid, String spu_name);

    int add(String spuid, String spu_name,float price);
}
