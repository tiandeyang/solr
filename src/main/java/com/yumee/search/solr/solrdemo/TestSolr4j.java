package com.yumee.search.solr.solrdemo;

import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

public class TestSolr4j {

	public static void main(String[] args) throws SolrServerException, IOException {
		List<Product> products = ProductUtil.file2list("D:\\yuemee-solr\\xv-module-solr\\src\\main\\java\\com\\yixin\\xv\\model\\controller\\solrdemo\\140k_products.txt");
		SolrUtil.batchSaveOrUpdate(products);
	}

}
