package me.huiwen.example.solr.sampe;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class IndexData {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public static void main(String[] args) throws SolrServerException, IOException {
		SolrServer server = new HttpSolrServer(
					"http://dpev210.innovate.sfb.com:8983/solr/lcc");
		

		SolrInputDocument doc = new SolrInputDocument();
		
		doc.addField("docID", "Doc1");
		doc.addField("Title", "Big Data Introduction");
		doc.addField("Body", "");
		doc.addField("author", "hhuiwen@cn.ibm.com");
		server.add(doc);
		server.commit();
		
	}

}
