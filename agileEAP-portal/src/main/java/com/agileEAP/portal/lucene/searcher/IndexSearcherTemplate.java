package com.agileEAP.portal.lucene.searcher;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.lucene.search.IndexSearcher;

public class IndexSearcherTemplate {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private IndexSearcherManager searcherManager;

	public interface SearcherCallback {
		<T extends Object> T doCallback(IndexSearcher indexSearcher)
				throws IOException;
	}

	public <T extends Object> T excute(SearcherCallback callback) {
		T result = null;
		IndexSearcher indexSearcher = searcherManager.get();
		try {
			result = callback.doCallback(indexSearcher);
		} catch (IOException e) {
			log.error("do callback failed.", e);
		} finally {
			try {
				searcherManager.release(indexSearcher);
			} catch (IOException e) {
				log.error("release searcher error.", e);
			}
		}
		return result;
	}

	public void setSearcherManager(IndexSearcherManager searcherManager) {
		this.searcherManager = searcherManager;
	}
}
