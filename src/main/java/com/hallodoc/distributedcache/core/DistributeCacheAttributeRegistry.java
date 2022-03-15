package com.hallodoc.distributedcache.core;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.hallodoc.distributedcache.model.CacheAttribute;
import com.hallodoc.distributedcache.util.TypeFinder;

public final class DistributeCacheAttributeRegistry {

	private static ConcurrentHashMap<String, Class<?>> attributeTypeRepo;

	private static DistributeCacheAttributeRegistry instance;

	private DistributeCacheAttributeRegistry() {
		super();
	}

	public static DistributeCacheAttributeRegistry getInstance() {
		synchronized (instance) {
			if (instance == null) {
				synchronized (instance) {
					instance = new DistributeCacheAttributeRegistry();
				}
			}
			return instance;
		}
	}

	public void validateAndFill(List<CacheAttribute> attributes) {
		for (CacheAttribute cacheAttribute : attributes) {
			if (!attributeTypeRepo.contains(cacheAttribute.getAttributeName())) {
				attributeTypeRepo.put(cacheAttribute.getAttributeName(),
						TypeFinder.find(cacheAttribute.getAttributeValue()));
			} else {

				Class<?> type = attributeTypeRepo.get(cacheAttribute.getAttributeName());
				if (type != TypeFinder.find(cacheAttribute.getAttributeValue())) {
					throw new IllegalArgumentException("Illegal value has been passed");
				}
			}

		}
	}

}
