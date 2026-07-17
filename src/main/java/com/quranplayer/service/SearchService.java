package com.quranplayer.service;

import com.quranplayer.model.SearchResult;
import com.quranplayer.util.ArabicNormalizer;

import java.util.ArrayList;
import java.util.List;

public class SearchService {

    private final QuranIndexer indexer;

    public SearchService(QuranIndexer indexer) {
        this.indexer = indexer;
    }

    /**
     * البحث في القرآن.
     */
    public List<SearchResult> search(String keyword) {

        keyword = ArabicNormalizer.normalize(keyword);

        List<SearchResult> result = new ArrayList<>();

        for (SearchResult item : indexer.getIndex()) {

            String text = item.getNormalizedText();

            if (text.contains("الله")) {
                System.out.println("FOUND ALLAH:");
                System.out.println(text);
                break;
            }

            if (text.contains(keyword)) {
                result.add(item);
            }
        }

        return result;
    }

}