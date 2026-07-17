package com.quranplayer.service;

import com.quranplayer.model.Ayah;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimelineService {

    private List<Ayah> ayahs;

    private final Map<Integer, Ayah> ayahMap =
            new HashMap<>();

    public void setAyahs(List<Ayah> ayahs) {

        this.ayahs = ayahs;

        ayahMap.clear();

        if (ayahs != null) {

            for (Ayah ayah : ayahs) {

                ayahMap.put(
                        ayah.getAyahNumber(),
                        ayah);

            }

        }

    }

    public long getStartTime(int ayahNumber) {

        Ayah ayah = ayahMap.get(ayahNumber);

        return ayah == null ? 0 : ayah.getStartMs();

    }

    public Ayah getCurrentAyah(long millis) {

        if (ayahs == null || ayahs.isEmpty())
            return null;

        int low = 0;
        int high = ayahs.size() - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            long value = ayahs.get(mid).getStartMs();

            if (value == millis)
                return ayahs.get(mid);

            if (value < millis)
                low = mid + 1;
            else
                high = mid - 1;

        }

        return ayahs.get(Math.max(0, high));
    }

}