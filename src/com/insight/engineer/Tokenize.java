package com.insight.engineer;

import java.util.*;

// customized StringTokenizer
public class Tokenize extends StringTokenizer {
    protected boolean lastWasDelim = true;
    String delims;

    public Tokenize(String s, String _delims) {
        super(s, _delims, true);
        delims = _delims;
    }

    public String nextToken() {
        String token = super.nextToken();
        boolean isDelim = token.length() > 0 && delims.indexOf(token.charAt(0)) != -1;

        token = isDelim ? (lastWasDelim ? "" : null) : token;
        lastWasDelim = isDelim;
        return token==null ? nextToken() : token;
    }
}