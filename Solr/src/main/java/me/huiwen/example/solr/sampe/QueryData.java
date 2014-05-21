package me.huiwen.example.solr.sampe;

import java.util.Iterator;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public class QueryData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public SolrDocumentList queryGetSolrDocumentList(String email ,Set<String> keywordSet,
			int limit) {
		SolrDocumentList list = null;

		StringBuffer queryBuffer = new StringBuffer();
		boolean firstQueryWord = true;

		Iterator<String> iter = keywordSet.iterator();
		while (iter.hasNext()) {
			String theTerm = iter.next();
			if (theTerm != null && theTerm.length() > 1) {
				if (firstQueryWord) {
					firstQueryWord = false;
					queryBuffer.append(theTerm);
				} else {
					queryBuffer.append(" OR ").append(theTerm);
				}

			}
		}

	
		
		//
		SolrServer server = new HttpSolrServer("http://dpev210.innovate.sfb.com:8983/solr/lcc");

		SolrQuery query = new SolrQuery();
		query.setQuery(queryBuffer.toString());
		query.add("version", "2.2");
		query.addField("*");
		query.addField("score");
		query.setRows(limit);
		query.addFilterQuery("authors_emails:"+email);
		try {
			QueryResponse qr = server.query(query);
			list = qr.getResults();
		} catch (SolrServerException e) {
			
		}

		return list;

	}

}
