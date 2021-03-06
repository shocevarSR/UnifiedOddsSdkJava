/*
 * Copyright (C) Sportradar AG. See LICENSE for full license governing this code
 */

package com.sportradar.unifiedodds.sdk.impl;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.sportradar.uf.sportsapi.datamodel.Producer;
import com.sportradar.uf.sportsapi.datamodel.Producers;
import com.sportradar.uf.sportsapi.datamodel.ResponseCode;
import com.sportradar.unifiedodds.sdk.SDKInternalConfiguration;
import com.sportradar.unifiedodds.sdk.cfg.Environment;
import com.sportradar.unifiedodds.sdk.exceptions.internal.DataProviderException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 03/07/2017.
 * // TODO @eti: Javadoc
 */
public class ProducerDataProviderImpl implements ProducerDataProvider {
    private final DataProvider<Producers> dataProvider;
    private final SDKInternalConfiguration config;

    @Inject
    public ProducerDataProviderImpl(SDKInternalConfiguration configuration,
                                    LogHttpDataFetcher logHttpDataFetcher,
                                    @Named("ApiJaxbDeserializer")  Deserializer deserializer) {
        Preconditions.checkNotNull(configuration);
        Preconditions.checkNotNull(logHttpDataFetcher);
        Preconditions.checkNotNull(deserializer);

        this.config = configuration;
        this.dataProvider = new DataProvider<>("/descriptions/producers.xml", configuration, logHttpDataFetcher, deserializer);
    }

    @Override
    public List<ProducerData> getAvailableProducers() {
        Producers producers = null;
        try {
            producers = dataProvider.getData();
        } catch (DataProviderException e) {
            // request failed
        }

        if (producers != null && producers.getResponseCode() == ResponseCode.OK) {
            return buildProducersData(producers.getProducer());
        } else {
            return Collections.emptyList();
        }
    }

    private List<ProducerData> buildProducersData(List<Producer> producer) {
        Preconditions.checkNotNull(producer);

        return producer.stream().map(p -> new ProducerData(
                Math.toIntExact(p.getId()),
                p.getName(),
                p.getDescription(),
                p.isActive(),
                config.getEnvironment() != Environment.Custom
                        ? p.getApiUrl()
                        : ReplaceProducerApiUrl(p.getApiUrl()),
                p.getScope(),
                p.getStatefulRecoveryWindowInMinutes()
        )).collect(Collectors.toList());
    }

    private String ReplaceProducerApiUrl(String url)
    {
        if (url.contains(UnifiedFeedConstants.INTEGRATION_API_HOST))
        {
            return url.replace(UnifiedFeedConstants.INTEGRATION_API_HOST, config.getAPIHost());
        }
        return url.replace(UnifiedFeedConstants.PRODUCTION_API_HOST, config.getAPIHost());
    }
}
