package com.devinsight.mapsearchpractice.dodoApi.data;
// 통신에 직접적으로 쓰지 않음, 하지만 header.item,num과 같인 level의 상위 카테고리
public class Header {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Header(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
