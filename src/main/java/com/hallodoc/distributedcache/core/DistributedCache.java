package com.hallodoc.distributedcache.core;

import java.util.List;
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
		return  repository.entrySet().stream().filter(cache-> cacheFilters.stream().anyMatch(filter-> 
		cache.getValue().contains(new DistributedCacheAttribute(filter.getAttributeName(),
				filter.getAttributeValue())))).map(z->z.getKey()).collect(Collectors.toList());
	}

}
