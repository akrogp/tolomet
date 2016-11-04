package com.akrog.tolomet.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by gorka on 4/11/16.
 */

public class HtmlView extends TextView {
    public HtmlView(Context context) {
        super(context);
    }

    public HtmlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(Html.fromHtml(text.toString()), type);
    }
}
