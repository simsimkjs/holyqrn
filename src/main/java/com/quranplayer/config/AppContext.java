package com.quranplayer.config;

import com.quranplayer.service.LastReadService;
import com.quranplayer.service.QuranIndexer;
import com.quranplayer.service.QuranService;
import com.quranplayer.service.SearchService;

public final class AppContext {

    private static final QuranService quranService =
            new QuranService();

    private static final QuranIndexer indexer =
            new QuranIndexer(quranService);

    private static final SearchService searchService =
            new SearchService(indexer);
    
    private static final LastReadService lastReadService =
        new LastReadService();

    private AppContext() {
    }

    public static QuranService getQuranService() {
        return quranService;
    }

    public static QuranIndexer getIndexer() {
        return indexer;
    }

    public static SearchService getSearchService() {
        return searchService;
    }
    public static LastReadService getLastReadService() {
        return lastReadService;
    }

}