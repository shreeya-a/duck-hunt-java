package game_object;

public class HP {
    double Max_hp;
    double current_hp;

    public double getMax_hp() {
        return Max_hp;
    }

    public void setMax_hp(double max_hp) {
        this.Max_hp = max_hp;
    }
    public double getCurrent_hp() {
        return current_hp;
    }

    public void setCurrent_hp(double current_hp) {
        this.current_hp = current_hp;
    }
    public HP(){

    }
    public HP (double max_hp, double current_hp){
        this.Max_hp = max_hp;
        this.current_hp = current_hp;
    }
}
