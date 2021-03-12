package com.yz.base.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

public class MyFilterTextWatcher implements TextWatcher {

    private int maxLenth = Integer.MAX_VALUE;//设置允许输入的字符长度
    private String filter;
    private boolean isNumber;
    private double maxValue;
    private double minValue;
    private int decimalLenth;
    private EditText editText;
    private MyTextWatcherListener textWatcherListener;

    private String beforeStr;
    private int cou;

    public interface MyTextWatcherListener {
        void afterTextChanged(String s);
    }

    private MyFilterTextWatcher(){
    }

    public void setMaxLenth(int maxLenth) {
        this.maxLenth = maxLenth;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setNumber(boolean number) {
        isNumber = number;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public void setDecimalLenth(int decimalLenth) {
        this.decimalLenth = decimalLenth;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public void setTextWatcherListener(MyTextWatcherListener textWatcherListener) {
        this.textWatcherListener = textWatcherListener;
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
        cou = editText.length();
        if (cou > maxLenth) {
            editText.setText(beforeStr);
            editText.setSelection(start);
        }
        String editable = editText.getText().toString().trim();
        if(!TextUtils.isEmpty(filter)){
            String str=filter(editable);//过滤特殊字符
            if (!editable.equals(str)) {
                editText.setText(str);
                editText.setSelection(start);
            } 
        }
        if(isNumber && !TextUtils.isEmpty(beforeStr) && beforeStr.contains(".") && !TextUtils.isEmpty(editable)){
            try {
                CharSequence sub1 = editable.subSequence(0,start);
                CharSequence sub2 = editable.subSequence(start,start+count);
                CharSequence sub3 = editable.subSequence(start+count,editable.length());
                if (sub2.toString().contains(".")&&(sub1.toString().contains(".")||sub3.toString().contains("."))) {
                    StringBuffer sb=new StringBuffer();
                    sb.append(sub1).append(sub3);
                    String str=sb.toString();
                    editText.setText(str);
                }
            }catch (Exception e){
                MyLogger.e(e.getMessage(),e);
            }
        }
        if(isNumber && !TextUtils.isEmpty(editable)){
            if(maxValue!=0){
                try {
                    double d= Double.parseDouble(editable);
                    if(d>maxValue){
                        editText.setText(beforeStr);
                        editText.setSelection(editText.length());
                    }
                }catch (Exception e){
                    MyLogger.e(e.getMessage(),e);
                }
            }

            if(minValue!=0){
                try {
                    double d= Double.parseDouble(editable);
                    if(d<minValue){
                        editText.setText(beforeStr);
                        editText.setSelection(editText.length());
                    }
                }catch (Exception e){
                    MyLogger.e(e.getMessage(),e);
                }
            }

            if(decimalLenth!=0){
                try {
                    if(editable.contains(".")){
                        String[] ss=editable.split("\\.");
                        String end=ss[1];
                        if(!TextUtils.isEmpty(end)&&end.length()>decimalLenth){
                            editText.setText(beforeStr);
                            editText.setSelection(editText.length());
                        }
                    }
                }catch (Exception e){
                    MyLogger.e(e.getMessage(),e);
                }
            }
        }

//        if(isNumber && !TextUtils.isEmpty(editable) && maxValue>0){
//            try {
//                if(editable.contains(".")){
//                    String[] ss=editable.split("\\.");
//                    String ss0=ss[0];
//                    String ss1=ss[1];
//                    if(!TextUtils.isEmpty(ss0)){
//                        double d0= Double.parseDouble(ss0);
//                        if(Math.abs(d0)>maxValue){
//                            mEditText.setText(beforeStr);
//                            mEditText.setSelection(mEditText.length());
//                        }else if(Math.abs(d0)==maxValue&&!TextUtils.isEmpty(ss1)){
//                            double d1= Double.parseDouble(ss1);
//                            if(d1>0){
//                                mEditText.setText(beforeStr);
//                                mEditText.setSelection(mEditText.length());
//                            }
//                        }
//                    }
//                }else{
//                    double d= Double.parseDouble(editable);
//                    if(Math.abs(d)>maxValue){
//                        mEditText.setText(beforeStr);
//                        mEditText.setSelection(mEditText.length());
//                    }
//                }
//            }catch (Exception e){
//                MyLogger.e(e.getMessage(),e);
//            }
//        }
//        if(isNumber && !TextUtils.isEmpty(editable) && decimalLenth>0){
//            try {
//                if(editable.contains(".")){
//                    String[] ss=editable.split("\\.");
//                    String end=ss[1];
//                    if(!TextUtils.isEmpty(end)&&end.length()>decimalLenth){
//                        mEditText.setText(beforeStr);
//                        mEditText.setSelection(mEditText.length());
//                    }
//                }
//            }catch (Exception e){
//                MyLogger.e(e.getMessage(),e);
//            }
//        }
        if(textWatcherListener!=null){
            textWatcherListener.afterTextChanged(editText.getText().toString().trim());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public static class Builder {

        public boolean isNumber;
        public int maxLenth;
        public double maxValue;
        public double minValue;
        public int decimalLenth;
        public String filter;
        public EditText editText;
        public MyTextWatcherListener textWatcherlistener;

        public MyFilterTextWatcher.Builder setNumber(boolean number) {
            isNumber = number;
            return this;
        }

        public MyFilterTextWatcher.Builder setMaxLenth(int maxLenth) {
            this.maxLenth=maxLenth;
            return this;
        }

        public MyFilterTextWatcher.Builder setMaxValue(double maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        public MyFilterTextWatcher.Builder setMinValue(double minValue) {
            this.minValue = minValue;
            return this;
        }

        public MyFilterTextWatcher.Builder setDecimalLenth(int decimalLenth) {
            this.decimalLenth = decimalLenth;
            return this;
        }

        public MyFilterTextWatcher.Builder setFilter(String filter) {
            this.filter = filter;
            return this;
        }

        public MyFilterTextWatcher.Builder setEditText(EditText editText) {
            this.editText = editText;
            return this;
        }

        public MyFilterTextWatcher.Builder setTextWatcherlistener(MyTextWatcherListener textWatcherlistener) {
            this.textWatcherlistener = textWatcherlistener;
            return this;
        }

        public MyFilterTextWatcher build() {
            MyFilterTextWatcher dialog = new MyFilterTextWatcher();
            dialog.setNumber(isNumber);
            dialog.setMaxLenth(maxLenth);
            dialog.setMaxValue(maxValue);
            dialog.setMinValue(minValue);
            dialog.setDecimalLenth(decimalLenth);
            dialog.setFilter(filter);
            dialog.setEditText(editText);
            dialog.setTextWatcherListener(textWatcherlistener);
            return dialog;
        }
    }
}
