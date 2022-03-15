package com.hallodoc.distributedcache.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.hallodoc.distributedcache.model.CacheAttribute;

public final class DistributedCache {
	private static DistributedCache distributeCache;
	private static ConcurrentHashMap<String, List<DistributedCacheAttribute>> repository;
	private static DistributeCacheAttributeRegistry typeRegistry;

	private DistributedCache() {

	}

	public static DistributedCache getInstance() {
		synchronized (distributeCache) {
			if (distributeCache == null) {
				synchronized (repository) {
					repository = new ConcurrentHashMap<String, List<DistributedCacheAttribute>>();
					typeRegistry = DistributeCacheAttributeRegistry.getInstance();
				}
			}
			return distributeCache;

		}
	}

	public void put(String keyName, List<CacheAttribute> attributes) {
		typeRegistry.validateAndFill(attributes);
		List<DistributedCacheAttribute> immutableList = attributes.stream()
				.map(x -> new DistributedCacheAttribute(x.getAttributeName(), x.getAttributeValue()))
				.collect(Collectors.toList());
		repository.put(keyName, immutableList);
	}

	public List<DistributedCacheAttribute> get(String cacheKey) {
		return repository.get(cacheKey);
	}

	public void delete(String cacheKey) {
		repository.remove(cacheKey);
	}

	public List<String> get(List<CacheAttribute> cacheFilters) {
		List<String> keyList = null;
		for (Map.Entry<String, List<DistributedCacheAttribute>> entryObj : repository.entrySet()) {
			for (CacheAttribute cacheAttribute : cacheFilters) {
				if (entryObj.getValue().contains(new DistributedCacheAttribute(cacheAttribute.getAttributeName(),
						cacheAttribute.getAttributeValue()))) {
					if (keyList == null) {
						keyList = new ArrayList<String>();
					}
					keyList.add(cacheAttribute.getAttributeName());
				}
			}
		}
		return keyList == null ? Collections.emptyList() : keyList;
	}

}
