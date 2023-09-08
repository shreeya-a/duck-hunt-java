package Dcomponent;
import game_obj_sound.Sound;
import game_object.Bullet;
import game_object.Duck;
import game_object.Effect;
import game_object.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class dcom  extends JComponent  {
    private Graphics2D g;
    private BufferedImage img;
    private boolean start = true;
    private Thread thread;
    private Keyboards key;
    private  int shortTime ;
    private final int FPS = 60;//frame per second
    private final int Target_time = 1000000000/FPS;
    private Sound sound;
    private Player player;
    public int width;
    public int height;
    private List<Bullet> bullets;
    private  List<Duck> ducks;
    private  List<Effect> boomEffects;
    private int score = 0;
    public void start(){
         width = getWidth();
         height = getHeight();
        img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (start){

                    long startTime = System.nanoTime();
                    backGround();
                    game();
                    render();
                    long time = System.nanoTime()-startTime;
                    if(time<Target_time){
                        long sleep = (Target_time-time)/1000000;//convert nano second to milisecond
                        sleep(sleep);
                    }
                }
            }

            private void backGround() {
                g.setColor(new Color(10,10,10));
                g.fillRect(0,0,width,height);
            }
            private void game(){
                if (player.isAlive()){
                    player.draw(g);
                }
                for(int i=0;i<bullets.size();i++){
                    Bullet bullet = bullets.get(i);
                    if(bullet!=null){
                        bullet.draw(g);
                    }
                }
                for (int i=0;i<ducks.size();i++){
                    Duck duck = ducks.get(i);
                    if (duck != null){
                        duck.draw(g);
                    }
                }
                for (int i=0;i<boomEffects.size();i++){
                    Effect boomEffect = boomEffects.get(i);
                    if (boomEffect != null) boomEffect.draw(g);
                }
                g.setColor(Color.WHITE);
                g.setFont(getFont().deriveFont(Font.BOLD, 15f));
                g.drawString("Score : "+score,10, 20);
                if (!player.isAlive()){
                    String text = "Game Over";
                    String textKey = "Press enter to continue";
                    g.setFont(getFont().deriveFont(Font.BOLD, 50f));
                    FontMetrics fm = g.getFontMetrics();
                    Rectangle2D r2 =  fm.getStringBounds(text, g);
                    double textWidth = r2.getWidth();
                    double textHeight = r2.getHeight();
                   double x = (width-textWidth)/2;
                   double y = (height-textHeight)/2;
                   g.drawString(text,(int) x,(int)y + fm.getAscent());
                   g.setFont(getFont().deriveFont(Font.BOLD, 15f));
                   fm = g.getFontMetrics();
                   r2 = fm.getStringBounds(textKey,g);
                   textWidth = r2.getWidth();
                   textHeight = r2.getHeight();
                   x = (width-textWidth)/2;
                   y = (height-textHeight)/2;
                   g.drawString(textKey,(int) x, (int)y+ fm.getAscent()+50);

                }
            }
            private void render(){
                Graphics g = getGraphics();
                g.drawImage(img,0,0,null);
                g.dispose();
            }
        });
        initObjectGame();
        initKeyboard();
        initBullets();
        thread.start();
        }
        private void sleep(long speed){
            try {
                Thread.sleep(speed);
            }catch (InterruptedException e){
                System.err.println(e);
            }

        }
        private void addDucks(){
//        location of y-axis
            Random random = new Random();
            int locationY = random.nextInt(height-50)+25;
            Duck duck = new Duck();
            duck.changeLocation(0,locationY);   //random values
            duck.changeAngle(0);                    //moving from left to right
            ducks.add(duck);
            int locationY2 = random.nextInt(height-50)+25;
            Duck duck2 = new Duck();
            duck2.changeLocation(width,locationY2);   //random values
            duck2.changeAngle(180);                    //moving from  right to left
            ducks.add(duck2);

        }
        private void initObjectGame(){
        sound= new Sound();
        player= new Player();
        player.changeLocation(150,150);
        ducks = new ArrayList<>();
        boomEffects = new ArrayList<>();
//        thread to call addDuck method
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start){
                    addDucks();
                    sleep(3000);
                }
            }
        }).start();
        }
        private void initBullets(){
            bullets= new ArrayList<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(start){
                         for(int i=0;i<bullets.size();i++){
                             Bullet bullet = bullets.get(i);
                             if(bullet != null){
                                 bullet.update();
                                 checkBullets(bullet);
                                 if(!bullet.check(width,height)){
                                     bullets.remove(bullet);
                                 }
                             }
                         }
                         for (int i = 0; i<boomEffects.size();i++){
                             Effect boomEffect = boomEffects.get(i);
                             if (boomEffect != null){
                                 boomEffect.update();
                                 if (!boomEffect.check()){
                                     boomEffects.remove(boomEffect);
                                 }
                             }
                         }
                         sleep(1);
                    }
                }
            }).start();
        }
    private void checkPlayer(Duck duck){
            if (duck != null){
                Area area = new Area(player.getShape());
                area.intersect(duck.getShape());
                if (!area.isEmpty()){
                    double duckHp = duck.getHP();
                    if (!duck.updateHP(player.getHP())){      // For test
                        ducks.remove(duck);
                        sound.soundDestroy();
                        double x = duck.getX()+Duck.duck_size/2;
                        double y = duck.getY()+Duck.duck_size/2;
                        boomEffects.add(new Effect(x, y, 75,5, new Color(213, 11, 58), 5, 0.05f));
                        boomEffects.add(new Effect(x, y, 75,5, new Color(103, 13, 60), 5, 0.1f));
                        boomEffects.add(new Effect(x, y, 100,10, new Color(103, 5, 28), 10, 0.3f));
                        boomEffects.add(new Effect(x, y, 100,5, new Color(199, 21, 116), 10, 0.2f));
                        boomEffects.add(new Effect(x, y, 150,5, new Color(80, 2, 24), 10, 0.5f));

                    }
                    if (!player.updateHP(duckHp)){      // For test
                        player.setAlive(false);
                        sound.soundDestroyPlayer();
                        double x = player.getX()+Player.psize/2;
                        double y = player.getY()+Player.psize/2;
                        boomEffects.add(new Effect(x, y, 75,5, new Color(213, 11, 58), 5, 0.05f));
                        boomEffects.add(new Effect(x, y, 75,5, new Color(103, 13, 60), 5, 0.1f));
                        boomEffects.add(new Effect(x, y, 100,10, new Color(103, 5, 28), 10, 0.3f));
                        boomEffects.add(new Effect(x, y, 100,5, new Color(199, 21, 116), 10, 0.2f));
                        boomEffects.add(new Effect(x, y, 150,5, new Color(80, 2, 24), 10, 0.5f));

                    }

                    sound.soundDestroy();
                }
            }

    }
    private void checkBullets(Bullet bullet){
        for (int i=0;i<ducks.size();i++){
            Duck duck = ducks.get(i);
            if (duck != null){
                Area area = new Area(bullet.getShape());
                area.intersect(duck.getShape());
                if (!area.isEmpty()){
                    boomEffects.add(new Effect(bullet.getCenterX(), bullet.getCenterY(), 3,5, new Color(230,207,105), 60, 0.5f));
                    if (!duck.updateHP(bullet.getSize())){
                        score++;
                        ducks.remove(duck);
                        sound.soundDestroy();
                        double x = duck.getX()+Duck.duck_size/2;
                        double y = duck.getY()+Duck.duck_size/2;
                        boomEffects.add(new Effect(x, y, 75,5, new Color(213, 11, 58), 5, 0.05f));
                        boomEffects.add(new Effect(x, y, 75,5, new Color(103, 13, 60), 5, 0.1f));
                        boomEffects.add(new Effect(x, y, 100,10, new Color(103, 5, 28), 10, 0.3f));
                        boomEffects.add(new Effect(x, y, 100,5, new Color(199, 21, 116), 10, 0.2f));
                        boomEffects.add(new Effect(x, y, 150,5, new Color(80, 2, 24), 10, 0.5f));

                    }else {
                        sound.soundHit();
                    }
                    bullets.remove(bullet);
                    sound.soundDestroy();
                }
            }
        }
    }
    private void resetGame(){
        score = 0;
        ducks.clear();
        bullets.clear();
        player.changeLocation(150,150);
        player.reset();
    }
        private void initKeyboard(){

        key = new Keyboards();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKeyboards_left(true);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKeyboards_right(true);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKeyboards_space(true);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKeyboards_j(true);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKeyboards_k(true);
                }else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKeyboards_left(false);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKeyboards_right(false);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKeyboards_space(false);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKeyboards_j(false);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKeyboards_k(false);
                }else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(false);
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                float s = 0.5f;
                while (start){
                    if (player.isAlive()) {
                        float angle = player.getangle();
                        if ((key.isKeyboards_left())) {
                            angle -= s;
                        }
                        if (key.isKeyboards_right()) {
                            angle += s;
                        }
                        if ((key.isKeyboards_j() || key.isKeyboards_k())) {
                            if (shortTime == 0) {
                                if (key.isKeyboards_j()) {
                                    bullets.add(0, new Bullet(player.getX(), player.getY(), player.getangle(), 5, 3f));
                                } else {
                                    bullets.add(0, new Bullet(player.getX(), player.getY(), player.getangle(), 20, 3f));
                                }
                                sound.soundShoot();
                            }
                            shortTime++;
                            if (shortTime == 15) {
                                shortTime = 0;
                            }
                        } else {
                            shortTime = 0;
                        }
                        if (key.isKeyboards_space()) {
                            player.speedUp();
                        } else {
                            player.speedDown();
                        }
                        player.update();
                        player.changeAngle(angle);
                    }else {
                        if (key.isKey_enter()){
                            resetGame();
                        }
                    }
    //                    update location of duck using loop
                        for(int i=0; i<ducks.size();i++){
                            Duck duck = ducks.get(i);
                            if (duck != null){
                                duck.update();
                                if (!duck.check(width,height)){
                                    ducks.remove(duck);
    //                                System.out.println("test duck removed");
                                }else {
                                    if (player.isAlive()){
                                        checkPlayer(duck);
                                    }
                                }
                            }
                        }
                        sleep(5);
                }
            }
        }).start();
    }
}