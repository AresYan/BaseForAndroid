package com.yz.base.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by YZ on 2017/3/31.
 */

public class MyFilterTextWatcher implements TextWatcher {

    private int mMaxLenth = Integer.MAX_VALUE;//设置允许输入的字符长度
    private int cou = 0;
    private String filter="";
    private String beforeStr="";
    private double maxValue=0;
    private int decimalLenth=0;
    private EditText mEditText;
    private MyTextWatcherListener mListener;

    public MyFilterTextWatcher(EditText editText){
        mEditText=editText;
    }

    public MyFilterTextWatcher(EditText editText, MyTextWatcherListener listener){
        mEditText=editText;
        mListener=listener;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public int getDecimalLenth() {
        return decimalLenth;
    }

    public void setDecimalLenth(int decimalLenth) {
        this.decimalLenth = decimalLenth;
    }

    public int getMaxLenth() {
        return mMaxLenth;
    }

    public void setMaxLenth(int maxLenth) {
        this.mMaxLenth = maxLenth;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    private String filter(String character) {
        character = character.replaceAll(filter, "");
        return character;
    }
    
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        beforeStr=s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        cou = mEditText.length();
        if (cou > mMaxLenth) {
            mEditText.setText(beforeStr);
            mEditText.setSelection(start);
        }
        String editable = mEditText.getText().toString().trim();
        if(!TextUtils.isEmpty(filter)){
            String str=filter(editable);//过滤特殊字符
            if (!editable.equals(str)) {
                mEditText.setText(str);
                mEditText.setSelection(start);
            } 
        }
        if(!TextUtils.isEmpty(beforeStr)&&beforeStr.contains(".")&&!TextUtils.isEmpty(editable)){
            try {
                CharSequence sub1 = editable.subSequence(0,start);
                CharSequence sub2 = editable.subSequence(start,start+count);
                CharSequence sub3 = editable.subSequence(start+count,editable.length());
                if (sub2.toString().contains(".")&&(sub1.toString().contains(".")||sub3.toString().contains("."))) {
                    StringBuffer sb=new StringBuffer();
                    sb.append(sub1).append(sub3);
                    String str=sb.toString();
                    mEditText.setText(str);
                }
            }catch (Exception e){
                MyLogger.e(e.getMessage(),e);
            }
        }
        if(!TextUtils.isEmpty(editable)&&maxValue>0){
            try {
                if(editable.contains(".")){
                    String[] ss=editable.split("\\.");
                    String ss0=ss[0];
                    String ss1=ss[1];
                    if(!TextUtils.isEmpty(ss0)){
                        double d0= Double.parseDouble(ss0);
                        if(Math.abs(d0)>maxValue){
                            mEditText.setText(beforeStr);
                            mEditText.setSelection(mEditText.length());
                        }else if(Math.abs(d0)==maxValue&&!TextUtils.isEmpty(ss1)){
                            double d1= Double.parseDouble(ss1);
                            if(d1>0){
                                mEditText.setText(beforeStr);
                                mEditText.setSelection(mEditText.length());
                            }
                        }
                    }
                }else{
                    double d= Double.parseDouble(editable);
                    if(Math.abs(d)>maxValue){
                        mEditText.setText(beforeStr);
                        mEditText.setSelection(mEditText.length());
                    }
                }
            }catch (Exception e){
                MyLogger.e(e.getMessage(),e);
            }
        }
        if(!TextUtils.isEmpty(editable)&&decimalLenth>0){
            try {
                if(editable.contains(".")){
                    String[] ss=editable.split("\\.");
                    String end=ss[1];
                    if(!TextUtils.isEmpty(end)&&end.length()>decimalLenth){
                        mEditText.setText(beforeStr);
                        mEditText.setSelection(mEditText.length());
                    }
                }
            }catch (Exception e){
                MyLogger.e(e.getMessage(),e);
            }
        }
        if(mListener!=null){
            mListener.afterTextChanged(mEditText.getText().toString().trim());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public interface MyTextWatcherListener {
        void afterTextChanged(String s);
    }
}
