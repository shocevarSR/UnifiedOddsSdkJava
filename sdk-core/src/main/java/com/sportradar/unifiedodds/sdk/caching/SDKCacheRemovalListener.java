/*
 * Copyright (C) Sportradar AG. See LICENSE for full license governing this code
 */

package com.sportradar.unifiedodds.sdk.caching;

import com.google.common.base.Preconditions;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 27/10/2017.
 * // TODO @eti: Javadoc
 */
public class SDKCacheRemovalListener<K, V> implements RemovalListener<K, V> {
    private final static Logger logger = LoggerFactory.getLogger(SDKCacheRemovalListener.class);
    private final String cacheName;
    private final boolean useDebugLog;

    public SDKCacheRemovalListener(String cacheName) {
        this(cacheName, false);
    }

    public SDKCacheRemovalListener(String cacheName, boolean useDebugLog) {
        this.useDebugLog = useDebugLog;
        Preconditions.checkNotNull(cacheName);

        this.cacheName = cacheName;
    }


    @Override
    public void onRemoval(RemovalNotification<K, V> notification) {
        if (!useDebugLog) {
            logger.info("{}: CacheItem[{}] invalidated, reason: {}", cacheName, notification.getKey(), notification.getCause());
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("{}: CacheItem[{}] invalidated, reason: {}", cacheName, notification.getKey(), notification.getCause());
        }
    }
}
