package com.example.du_an_1_nhom9.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class AppUtils {
    public static String getCurrencySymbol() {
        return "₫";
    }
    public static String formatNumber(final long number, Locale... locales) {
        Locale locale = (locales.length == 0) ? new Locale("vi", "vn") : locales[0];
        final NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setMaximumFractionDigits(0);
        String currencyfied;
        final DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) format).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) format).setDecimalFormatSymbols(decimalFormatSymbols);
        currencyfied = format.format(number);
        return currencyfied.trim().replaceAll("\\s+", "");

    }
}
