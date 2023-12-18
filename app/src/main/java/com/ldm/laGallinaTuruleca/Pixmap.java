package com.ldm.laGallinaTuruleca;

import com.ldm.laGallinaTuruleca.Graficos.PixmapFormat;

public interface Pixmap {
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}

