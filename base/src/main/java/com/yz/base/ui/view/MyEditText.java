package com.yz.base.ui.view;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import com.yz.base.entity.BaseEntity;

/**
 * Created by YZ on 2017/4/18.
 */
public class MyEditText extends EditText {

    public MyEditText(Context context) {
        super(context);
        setNoFull();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setNoFull();
    }

    private TextWatcher textWatcher;
    private BaseEntity entity;

    public TextWatcher getTextWatcher() {
        return textWatcher;
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
        addTextChangedListener(textWatcher);
    }

    public BaseEntity getEntity() {
        return entity;
    }

    public void setEntity(BaseEntity entity) {
        this.entity = entity;
    }

    public void setNoFull(){
        setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
    }
}
