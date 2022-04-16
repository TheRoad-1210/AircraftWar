package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.factory.EnemyFactory;
import edu.hitsz.items.AbstractProp;
import edu.hitsz.player.Player;
import edu.hitsz.player.PlayerDaoImpl;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static edu.hitsz.factory.PropFactory.prop;


/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<AbstractBullet> heroBullets;
    private final List<AbstractBullet> enemyBullets;
    private final List<AbstractProp> abstractProp;
    private final int bossScoreThreshold = 200;

    private int enemyMaxNumber = 5;

    private boolean gameOverFlag = false;


    private int score = 0;
    private int time = 0;
    public int scorer = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;

    public Game() {
        heroAircraft = HeroAircraft.getInstance();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        abstractProp = new LinkedList<>();



        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                //Boss检测
                bossTime();
                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    EnemyFactory enemyFactory = new EnemyFactory();
                    enemyAircrafts.add(enemyFactory.create());
                }
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            //道具移动
            propMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
                try {
                    paintScore();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("Game Over!");
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        //  敌机射击
        for(AbstractAircraft e:enemyAircrafts){
            enemyBullets.addAll(e.shoot());
        }


        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (AbstractBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propMoveAction(){
        for (AbstractProp prop : abstractProp) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        //  敌机子弹攻击英雄
        for (AbstractBullet enemybullet : enemyBullets) {
            if (heroAircraft.crash(enemybullet)){
                heroAircraft.decreaseHp(enemybullet.getPower());
                enemybullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (AbstractBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        //  获得分数，产生道具补给
                        score += 10;
                        scorer += 10;
                        if(enemyAircraft instanceof EliteEnemy ){
                            score += 10;
                            scorer += 10;
                        // 可能不会生成道具
                            if(Math.random()>=0.3) {
                                abstractProp.add(prop(enemyAircraft));
                            }
                        }
                        if(enemyAircraft instanceof BossEnemy ){
                            score += 40;
                            scorer += 40;
                            abstractProp.add(prop(enemyAircraft));
                            abstractProp.add(prop(enemyAircraft));
                            abstractProp.add(prop(enemyAircraft));
                            abstractProp.add(prop(enemyAircraft));
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得道具，道具生效

        for(AbstractProp p:abstractProp){
            if (heroAircraft.crash(p)){
                p.vanish();
                p.use(heroAircraft);
                }

            }
        }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        abstractProp.removeIf(AbstractProp::notValid);

    }


    /**
     * 发送boss产生信号给敌机工厂
     */
    private void bossTime(){

        boolean flag1 = true;
        boolean flag2 = false;
        if(this.scorer >= bossScoreThreshold){
            flag2 = true;
            this.scorer = this.scorer-bossScoreThreshold;
        }

        for (AbstractAircraft enemy:enemyAircrafts){
            if(enemy instanceof BossEnemy){
                flag1 = false;
            }
        }
        if( flag1 && flag2){
            EnemyFactory enemyFactory = new EnemyFactory();
            enemyFactory.boss = true;
            enemyAircrafts.add(enemyFactory.create());
        }
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制道具，再子弹，后绘制飞机
        // 这样子弹显示在飞机的下层

        paintImageWithPositionRevised(g,abstractProp);

        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(0x008EEF));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    /**
     * 输入player id，获得排名榜
     */

    private void paintScore() throws IOException, ClassNotFoundException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        Player player = new Player(this.score,dateFormat.format(date));
        player.setScore(this.score);
        System.out.println("请输入您的id");
        Scanner s = new Scanner(System.in);
        player.setName(String.valueOf(s.next()));
        PlayerDaoImpl playerDao = new PlayerDaoImpl();
        playerDao.read();
        playerDao.doAdd(player);
        playerDao.storage();
        System.out.println(
                "**************************"+
                        "得分排行榜"+
                        "**************************"
        );
        for (int i = 0; i<playerDao.getPlayers().size();i++) {
            int j = i+1;
            System.out.print("第"+ j +"名： " + playerDao.getPlayers().get(i).getName()+"\t"
                    + playerDao.getPlayers().get(i).getScore()+"\t"
                    +playerDao.getPlayers().get(i).getTime()+"\t"
            +"\n");
        }
    }
}
