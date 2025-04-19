package com.kam.andromate.view.fragmentUx;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.kam.andromate.utils.TimeUtils;

public class ReportSection {

    private static final String BLACK_COLOR_HTML_CODE = "<font color='#000000'><b>";
    private static final String FONT_HTML_CODE = "</font>";
    private static final String HTML_B_FONT_END = "</b></font>";
    private static final String BR_END = "<br/>";
    private static final String RED_COLOR_HTML_CODE = "<font color='#FF0000'><b>";
    private static final String GREEN_COLOR_HTML_CODE = "<font color='#00A000'><b>";
    private static final String BLUE_COLOR_HTML_CODE = "<font color='#0000A0'><b>";


    TextView terminalView = null;

    boolean margin = false;

    public ReportSection(TextView terminalView) {
        this.terminalView = terminalView;
        this.terminalView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void incMargin() {
        margin = true;
    }

    public void discMargin() {
        margin = false;
    }

    private String getTimeStampViewFormat() {
        return margin ? TimeUtils.getCurrentTimeAsSimpleFormat() + "-|"
                : TimeUtils.getCurrentTimeAsSimpleFormat() + "-" ;
    }

    public void appendMsg(String text) {
        String htmlText = BLACK_COLOR_HTML_CODE + getTimeStampViewFormat() + HTML_B_FONT_END
                + text + BR_END;
        terminalView.append(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
    }

    public void appendFmvKey(String key, String text) {
        String htmlText = BLACK_COLOR_HTML_CODE + getTimeStampViewFormat() + key+": " + HTML_B_FONT_END
                + text + BR_END;
        terminalView.append(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
    }

    public void info(String text) {
        String htmlText = BLACK_COLOR_HTML_CODE + getTimeStampViewFormat() + HTML_B_FONT_END +
                GREEN_COLOR_HTML_CODE + text + FONT_HTML_CODE+BR_END;
        terminalView.append(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
    }


    public void errorMsg(String text) {
        String htmlText = BLACK_COLOR_HTML_CODE + getTimeStampViewFormat() + HTML_B_FONT_END +
                RED_COLOR_HTML_CODE +"E-"+ text + FONT_HTML_CODE+BR_END;
        terminalView.append(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
    }

    public void appendTitle(String text) {
        String htmlText = BLACK_COLOR_HTML_CODE + getTimeStampViewFormat() + HTML_B_FONT_END +
                BLUE_COLOR_HTML_CODE +text + FONT_HTML_CODE+BR_END;
        terminalView.append(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
    }

}
