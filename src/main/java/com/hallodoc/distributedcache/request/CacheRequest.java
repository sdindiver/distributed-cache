package com.hallodoc.distributedcache.request;

import java.util.List;

import com.hallodoc.distributedcache.model.CacheAttribute;

public class CacheRequest {
	
	private String cacheKey;
	private List<CacheAttribute> cacheAttributes;
	
	public String getCacheKey() {
		return cacheKey;
	}
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	public List<CacheAttribute> getCacheAttributes() {
		return cacheAttributes;
	}
	public void setCacheAttributes(List<CacheAttribute> cacheAttributes) {
		this.cacheAttributes = cacheAttributes;
	}
	
	

}
