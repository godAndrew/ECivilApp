package br.com.viniciusalmada.civilapp.extras;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by vinicius-almada on 16/03/17.
 */

public class FacebookButton extends android.support.v7.widget.AppCompatButton {

    public FacebookButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/facebookfont3.ttf"));
    }
}