package com.quranplayer.ui;

import com.quranplayer.model.Ayah;
import javafx.scene.control.ListView;

public class AyahHighlighter {

    private final ListView<Ayah> listView;

    private int currentIndex=-1;

    public AyahHighlighter(ListView<Ayah> listView){

        this.listView=listView;

    }

    public void highlight(int index){

        if(index==currentIndex)
            return;

        currentIndex=index;

        listView.getSelectionModel().select(index);

        listView.scrollTo(
                Math.max(index-2,0));

    }

}