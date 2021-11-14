package com.example.seminardam_teme;

public final class UtilityFormulae {

    public static int prettyLowerBound(int n) {
        int k = (int)Math.log10(n>1? n-1:1);
        int t = (int)Math.pow(10, k);
        return (n/t)*t;
    }

}
