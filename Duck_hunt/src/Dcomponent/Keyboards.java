package Dcomponent;

public class Keyboards {
    private boolean keyboards_right;
    private boolean Keyboards_left;
    private boolean Keyboards_space;
    private boolean keyboards_j;
    private boolean keyboards_k;
    private boolean key_enter;

    public boolean isKeyboards_right() {
        return keyboards_right;
    }

    public void setKeyboards_right(boolean keyboards_right) {
        this.keyboards_right = keyboards_right;
    }

    public boolean isKeyboards_left() {
        return Keyboards_left;
    }

    public void setKeyboards_left(boolean keyboards_left) {
        Keyboards_left = keyboards_left;
    }

    public boolean isKeyboards_space() {
        return Keyboards_space;
    }

    public void setKeyboards_space(boolean keyboards_space) {
        Keyboards_space = keyboards_space;
    }

    public boolean isKeyboards_j() {
        return keyboards_j;
    }

    public void setKeyboards_j(boolean keyboards_j) {
        this.keyboards_j = keyboards_j;
    }

    public boolean isKeyboards_k() {
        return keyboards_k;
    }

    public void setKeyboards_k(boolean keyboards_k) {
        this.keyboards_k = keyboards_k;
    }

    public void setKey_enter(boolean key_enter) {
        this.key_enter=key_enter;
    }
    public boolean isKey_enter(){
        return key_enter;
    }
}

