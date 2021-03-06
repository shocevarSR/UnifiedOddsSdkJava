/*
 * Copyright (C) Sportradar AG. See LICENSE for full license governing this code
 */

package com.sportradar.unifiedodds.sdk.caching.impl.ci;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.inject.Inject;
import com.sportradar.uf.sportsapi.datamodel.*;
import com.sportradar.unifiedodds.sdk.ExceptionHandlingStrategy;
import com.sportradar.unifiedodds.sdk.SDKInternalConfiguration;
import com.sportradar.unifiedodds.sdk.caching.*;
import com.sportradar.utils.URN;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created on 19/10/2017.
 * // TODO @eti: Javadoc
 */
public class CacheItemFactoryImpl implements CacheItemFactory {
    private final DataRouterManager dataRouterManager;
    private final Locale defaultLocale;
    private final ExceptionHandlingStrategy exceptionHandlingStrategy;
    private final Cache<URN, Date> fixtureTimestampCache;

    @Inject
    CacheItemFactoryImpl(DataRouterManager dataRouterManager, SDKInternalConfiguration configuration, Cache<URN, Date> fixtureTimestampCache) {
        Preconditions.checkNotNull(dataRouterManager);
        Preconditions.checkNotNull(configuration);
        Preconditions.checkNotNull(fixtureTimestampCache);

        this.dataRouterManager = dataRouterManager;
        this.defaultLocale = configuration.getDefaultLocale();
        this.exceptionHandlingStrategy = configuration.getExceptionHandlingStrategy();
        this.fixtureTimestampCache = fixtureTimestampCache;
    }

    @Override
    public MatchCI buildMatchCI(URN id) {
        return new MatchCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, fixtureTimestampCache);
    }

    @Override
    public MatchCI buildMatchCI(URN id, SAPISportEvent data, Locale dataLocale) {
        return new MatchCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale, fixtureTimestampCache);
    }

    @Override
    public MatchCI buildMatchCI(URN id, SAPISportEventChildren.SAPISportEvent data, Locale dataLocale) {
        return new MatchCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale, fixtureTimestampCache);
    }

    @Override
    public MatchCI buildMatchCI(URN id, SAPIFixture data, Locale dataLocale) {
        return new MatchCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale, fixtureTimestampCache);
    }

    @Override
    public MatchCI buildMatchCI(URN id, SAPIMatchSummaryEndpoint data, Locale dataLocale) {
        return new MatchCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale, fixtureTimestampCache);
    }

    @Override
    public TournamentCI buildTournamentCI(URN id) {
        return new TournamentCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy);
    }

    @Override
    public TournamentCI buildTournamentCI(URN id, SAPITournament endpointData, Locale dataLocale) {
        return new TournamentCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, endpointData, dataLocale);
    }

    @Override
    public TournamentCI buildTournamentCI(URN id, SAPITournamentExtended endpointData, Locale dataLocale) {
        return new TournamentCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, endpointData, dataLocale);
    }

    @Override
    public TournamentCI buildTournamentCI(URN id, SAPITournamentInfoEndpoint endpointData, Locale dataLocale) {
        return new TournamentCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, endpointData, dataLocale);
    }

    @Override
    public StageCI buildStageCI(URN id, SAPIStageSummaryEndpoint endpointData, Locale dataLocale) {
        return new RaceStageCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, endpointData, dataLocale, fixtureTimestampCache);
    }

    @Override
    public StageCI buildStageCI(URN id, SAPISportEvent endpointData, Locale dataLocale) {
        return new RaceStageCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, endpointData, dataLocale, fixtureTimestampCache);
    }

    @Override
    public StageCI buildStageCI(URN id, SAPISportEventChildren.SAPISportEvent endpointData, Locale dataLocale) {
        return new RaceStageCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, endpointData, dataLocale, fixtureTimestampCache);
    }

    @Override
    public StageCI buildStageCI(URN id, SAPIFixture endpointData, Locale dataLocale) {
        return new RaceStageCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, endpointData, dataLocale, fixtureTimestampCache);
    }

    @Override
    public StageCI buildStageCI(URN id, SAPITournament endpointData, Locale dataLocale) {
        return new TournamentStageCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, endpointData, dataLocale);
    }

    @Override
    public StageCI buildStageCI(URN id, SAPITournamentInfoEndpoint endpointData, Locale dataLocale) {
        return new TournamentStageCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, endpointData, dataLocale);
    }

    @Override
    public SportCI buildSportCI(URN id, SAPISport sport, List<URN> categories, Locale dataLocale) {
        return new SportCIImpl(id, sport, categories, dataLocale);
    }

    @Override
    public CategoryCI buildCategoryCI(URN id, SAPICategory category, List<URN> tournamentIds, URN associatedSportCiId, Locale dataLocale) {
        return new CategoryCIImpl(id, category, tournamentIds, associatedSportCiId, dataLocale);
    }

    @Override
    public PlayerProfileCI buildPlayerProfileCI(URN id) {
        return new PlayerProfileCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy);
    }

    @Override
    public PlayerProfileCI buildPlayerProfileCI(URN id, SAPIPlayerExtended data, Locale dataLocale) {
        return new PlayerProfileCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale);
    }

    @Override
    public PlayerProfileCI buildPlayerProfileCI(URN id, SAPIPlayerCompetitor data, Locale dataLocale) {
        return new PlayerProfileCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale);
    }

    @Override
    public CompetitorCI buildCompetitorProfileCI(URN id) {
        return new CompetitorCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy);
    }

    @Override
    public CompetitorCI buildCompetitorProfileCI(URN id, SAPICompetitorProfileEndpoint data, Locale dataLocale) {
        return new CompetitorCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale);
    }

    @Override
    public CompetitorCI buildCompetitorProfileCI(URN id, SAPITeam data, Locale dataLocale) {
        return new CompetitorCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale);
    }

    @Override
    public CompetitorCI buildCompetitorProfileCI(URN id, SAPIPlayerCompetitor data, Locale dataLocale) {
        return new CompetitorCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale);
    }

    @Override
    public CompetitorCI buildCompetitorProfileCI(URN id, SAPISimpleTeamProfileEndpoint data, Locale dataLocale) {
        return new CompetitorCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale);
    }

    @Override
    public LotteryCI buildLotteryCI(URN id) {
        return new LotteryCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy);
    }

    @Override
    public LotteryCI buildLotteryCI(URN id, SAPILottery data, Locale dataLocale) {
        return new LotteryCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale);
    }

    @Override
    public DrawCI buildDrawCI(URN id) {
        return new DrawCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy);
    }

    @Override
    public DrawCI buildDrawCI(URN id, SAPIDrawFixture data, Locale dataLocale) {
        return new DrawCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale);
    }

    @Override
    public DrawCI buildDrawCI(URN id, SAPIDrawEvent data, Locale dataLocale) {
        return new DrawCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale);
    }

    @Override
    public DrawCI buildDrawCI(URN id, SAPIDrawSummary data, Locale dataLocale) {
        return new DrawCIImpl(id, dataRouterManager, defaultLocale, exceptionHandlingStrategy, data, dataLocale);
    }

    @Override
    public Cache<URN, Date> getFixtureTimestampCache() {
        return fixtureTimestampCache;
    }
}
