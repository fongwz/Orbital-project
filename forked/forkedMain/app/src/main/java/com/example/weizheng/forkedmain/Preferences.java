package com.example.weizheng.forkedmain;

/** constructor class for user preferences */

public class Preferences {

    /** insert attributes, constructors, setters, getters **********/

    /** cuisines */
    private int _chinese;
    private int _malay;
    private int _indian;
    private int _western;
    private int _korean;

    /** tastes */
    private int _sweet;
    private int _sour;
    private int _spicy;

    /** dish types */
    private int _meat;
    private int _seafood;
    private int _veg;
    private int _dessert;

    public Preferences(){
        /** empty constructor */
    }

    public Preferences(int _chinese, int _malay, int _indian, int _western, int _korean, int _sweet, int _sour, int _spicy, int _meat, int _seafood, int _veg, int _dessert) {
        this._chinese = _chinese;
        this._malay = _malay;
        this._indian = _indian;
        this._western = _western;
        this._korean = _korean;
        this._sweet = _sweet;
        this._sour = _sour;
        this._spicy = _spicy;
        this._meat = _meat;
        this._seafood = _seafood;
        this._veg = _veg;
        this._dessert = _dessert;
    }

    /** getter methods *********************************************/

    public int is_chinese() {
        return _chinese;
    }

    public int is_malay() {
        return _malay;
    }

    public int is_indian() {
        return _indian;
    }

    public int is_western() {
        return _western;
    }

    public int is_korean() {
        return _korean;
    }

    public int is_sweet() {
        return _sweet;
    }

    public int is_sour() {
        return _sour;
    }

    public int is_spicy() {
        return _spicy;
    }

    public int is_meat() {
        return _meat;
    }

    public int is_seafood() {
        return _seafood;
    }

    public int is_veg() {
        return _veg;
    }

    public int is_dessert() {
        return _dessert;
    }

    /** setter methods ***********************************************/

    public void set_chinese(int _chinese) {
        this._chinese = _chinese;
    }

    public void set_malay(int _malay) {
        this._malay = _malay;
    }

    public void set_indian(int _indian) {
        this._indian = _indian;
    }

    public void set_western(int _western) {
        this._western = _western;
    }

    public void set_korean(int _korean) {
        this._korean = _korean;
    }

    public void set_sweet(int _sweet) {
        this._sweet = _sweet;
    }

    public void set_sour(int _sour) {
        this._sour = _sour;
    }

    public void set_spicy(int _spicy) {
        this._spicy = _spicy;
    }

    public void set_meat(int _meat) {
        this._meat = _meat;
    }

    public void set_seafood(int _seafood) {
        this._seafood = _seafood;
    }

    public void set_veg(int _veg) {
        this._veg = _veg;
    }

    public void set_dessert(int _dessert) {
        this._dessert = _dessert;
    }
}
