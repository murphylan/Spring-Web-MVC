package com.utils.xml;

enum Gender{
    MALE(true),
    FEMALE (false);
    @SuppressWarnings("unused")
	private boolean value;
    Gender(boolean _value){
        value = _value;
    }
}