package com.example.weizheng.forkedmain;

/** constructor class for user preferences */

public class Preferences {

    /** insert attributes, constructors, setters, getters **********/

    /** cuisines */
    private boolean _chinese;
    private boolean _malay;
    private boolean _indian;
    private boolean _western;
    private boolean _korean;

    /** tastes */
    private boolean _sweet;
    private boolean _sour;
    private boolean _spicy;

    /** dish types */
    //fill in dish types here

    public Preferences(){
        /** empty constructor */
    }

    public Preferences(boolean _chinese, boolean _malay, boolean _indian, boolean _western, boolean _korean, boolean _sweet, boolean _sour, boolean _spicy) {
        this._chinese = _chinese;
        this._malay = _malay;
        this._indian = _indian;
        this._western = _western;
        this._korean = _korean;
        this._sweet = _sweet;
        this._sour = _sour;
        this._spicy = _spicy;
    }

    /** getter methods *********************************************/

    public boolean is_chinese() {
        return _chinese;
    }

    public boolean is_malay() {
        return _malay;
    }

    public boolean is_indian() {
        return _indian;
    }

    public boolean is_western() {
        return _western;
    }

    public boolean is_korean() {
        return _korean;
    }

    public boolean is_sweet() {
        return _sweet;
    }

    public boolean is_sour() {
        return _sour;
    }

    public boolean is_spicy() {
        return _spicy;
    }

    /** setter methods ***********************************************/

    public void set_chinese(boolean _chinese) {
        this._chinese = _chinese;
    }

    public void set_malay(boolean _malay) {
        this._malay = _malay;
    }

    public void set_indian(boolean _indian) {
        this._indian = _indian;
    }

    public void set_western(boolean _western) {
        this._western = _western;
    }

    public void set_korean(boolean _korean) {
        this._korean = _korean;
    }

    public void set_sweet(boolean _sweet) {
        this._sweet = _sweet;
    }

    public void set_sour(boolean _sour) {
        this._sour = _sour;
    }

    public void set_spicy(boolean _spicy) {
        this._spicy = _spicy;
    }
}
