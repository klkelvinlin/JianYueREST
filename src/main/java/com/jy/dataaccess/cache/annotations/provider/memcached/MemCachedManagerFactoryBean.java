package com.jy.dataaccess.cache.annotations.provider.memcached;

import java.util.List;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.jy.dataaccess.cache.annotations.provider.AbstractCacheManagerFactoryBean;

/**
 * memcached cache provider factory bean
 * @author wdong
 * 
 */
public class MemCachedManagerFactoryBean extends
		AbstractCacheManagerFactoryBean<MemCachedClient> {

	/**
	 * The Constant CACHE_PROVIDER_NAME.
	 */
	private static final String CACHE_PROVIDER_NAME = "MemCachedProvider";

	private MemCachedClient memCachedClient;

	private SockIOPool pool;

	private List<String> servers;

	private List<Integer> weights;

	private int initConn;

	private int minConn;

	private int maxConn;

	private int maxIdle;

	private int maintSleep;

	public MemCachedManagerFactoryBean() {

	}

	public MemCachedManagerFactoryBean(MemCachedClient memCachedClient,
			SockIOPool pool, List<String> servers, int initConn, int minConn,
			int maxConn, int maxIdle, int maintSleep) {
		super();
		this.memCachedClient = memCachedClient;
		this.pool = pool;
		this.servers = servers;
		this.initConn = initConn;
		this.minConn = minConn;
		this.maxConn = maxConn;
		this.maxIdle = maxIdle;
		this.maintSleep = maintSleep;
	}
	
	public void setMemCachedClient(MemCachedClient memCachedClient) {
		this.memCachedClient = memCachedClient;
	}

	public void setPool(SockIOPool pool) {
		this.pool = pool;
	}

	public void setInitConn(int initConn) {
		this.initConn = initConn;
	}

	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public void setMaintSleep(int maintSleep) {
		this.maintSleep = maintSleep;
	}

	@Override
	public MemCachedClient getObject() throws Exception {
		return this.memCachedClient;
	}

	@Override
	public Class<? extends MemCachedClient> getObjectType() {
		return (memCachedClient != null) ? memCachedClient.getClass() : MemCachedClient.class;
	}

	@Override
	protected void createCacheManager() throws Exception {
		if (pool == null) {
			pool = SockIOPool.getInstance();
			pool.setServers((String[]) servers.toArray(new String[servers
					.size()]));

			pool.setInitConn(initConn);
			pool.setMinConn(minConn);
			pool.setMaxConn(maxConn);
			pool.setMaxIdle(maxIdle);

			pool.setMaintSleep(maintSleep);
			pool.initialize();
		}
		memCachedClient = new MemCachedClient();
	}

	@Override
	protected void destroyCacheManager() throws Exception {
		memCachedClient = null;
	}

	@Override
	protected String getCacheProviderName() {
		return CACHE_PROVIDER_NAME;
	}

	public void setServers(List<String> servers) {
		this.servers = servers;
	}

	public void setWeights(List<Integer> weights) {
		this.weights = weights;
	}
	
	public List<Integer> getWeights(){
		return this.weights;
	}
}
