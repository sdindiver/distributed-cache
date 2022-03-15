package com.hallodoc.distributedcache.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hallodoc.distributedcache.core.DistributedCache;
import com.hallodoc.distributedcache.core.DistributedCacheAttribute;
import com.hallodoc.distributedcache.enums.ResponseStatus;
import com.hallodoc.distributedcache.model.CacheAttribute;
import com.hallodoc.distributedcache.request.CacheRequest;
import com.hallodoc.distributedcache.response.AppResponse;

@RestController
public class CacheController {

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppResponse> saveCache(@RequestBody CacheRequest cacheRequest, HttpServletRequest request,
			HttpServletResponse httpReseponse) {
		DistributedCache cache = DistributedCache.getInstance();
		cache.put(cacheRequest.getCacheKey(), cacheRequest.getCacheAttributes());
		AppResponse response = new AppResponse(ResponseStatus.SUCCESS.getStatus(), "value is cached");
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@GetMapping(value = "/{cacheKey}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DistributedCacheAttribute>> findCache(@PathVariable("cacheKey") String cacheKey) {
		DistributedCache cache = DistributedCache.getInstance();
		return ResponseEntity.status(HttpStatus.OK).body(cache.get(cacheKey));
	}

	@DeleteMapping(value = "/{cacheKey}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppResponse> deleteCache(@PathVariable("cacheKey") String cacheKey) {
		DistributedCache cache = DistributedCache.getInstance();
		cache.delete(cacheKey);
		AppResponse response = new AppResponse(ResponseStatus.SUCCESS.getStatus(),
				"cache has been deleted for key " + cacheKey);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> findCacheKeys(@RequestBody List<CacheAttribute> cacheFilters) {
		DistributedCache cache = DistributedCache.getInstance();
		return ResponseEntity.status(HttpStatus.OK).body(cache.get(cacheFilters));
	}

}
