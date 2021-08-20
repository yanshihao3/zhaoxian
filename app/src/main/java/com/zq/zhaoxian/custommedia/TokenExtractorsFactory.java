package com.zq.zhaoxian.custommedia;

import android.net.Uri;

import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;

import java.util.List;
import java.util.Map;

/**
 * @program: zhaoxian
 * @description:
 * @author: 闫世豪
 * @create: 2021-08-19 15:33
 **/
public class TokenExtractorsFactory implements ExtractorsFactory {
    @Override
    public Extractor[] createExtractors() {
        return new Extractor[0];
    }

    @Override
    public Extractor[] createExtractors(Uri uri, Map<String, List<String>> responseHeaders) {
        return new Extractor[0];
    }
}
