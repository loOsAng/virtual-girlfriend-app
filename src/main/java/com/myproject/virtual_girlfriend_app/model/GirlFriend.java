package com.myproject.virtual_girlfriend_app.model; // 确保包名是你的项目包名

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.Column; // 如果之前用了

// 假设你使用了 Lombok，如果没有，你需要手动添加 getters/setters/constructors
// import lombok.Getter;
// import lombok.Setter;
// import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor;

@Entity
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
public class GirlFriend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String home;
    private String work;
    private String hobby;
    private String characterAttr; // 原 'character'
    private String birthday;
    private String fromWorld;     // 原 'from'

    // --- 构造函数 ---
    public GirlFriend() {
    }

    public GirlFriend(String name, int age, String home, String work, String hobby, String characterAttr, String birthday, String fromWorld) {
        this.name = name;
        this.age = age;
        this.home = home;
        this.work = work;
        this.hobby = hobby;
        this.characterAttr = characterAttr;
        this.birthday = birthday;
        this.fromWorld = fromWorld;
    }

    // --- Getters and Setters (如果不用Lombok，请确保它们都存在) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getHome() { return home; }
    public void setHome(String home) { this.home = home; }
    public String getWork() { return work; }
    public void setWork(String work) { this.work = work; }
    public String getHobby() { return hobby; }
    public void setHobby(String hobby) { this.hobby = hobby; }
    public String getCharacterAttr() { return characterAttr; }
    public void setCharacterAttr(String characterAttr) { this.characterAttr = characterAttr; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getFromWorld() { return fromWorld; }
    public void setFromWorld(String fromWorld) { this.fromWorld = fromWorld; }


    // --- **互动行为方法 (确保这些方法存在)** ---
    public String holdHand() {
        return "和" + this.name + "牵手了";
    }

    public String Hug() {
        return "和" + this.name + "拥抱了";
    }

    public String Kiss() {
        return "和" + this.name + "接吻了";
    }

    public String Shopping() {
        return "和" + this.name + "逛街了";
    }

    public String WatchMovie() {
        return "和" + this.name + "看电影了";
    }

    public String HaveFood() {
        return "和" + this.name + "吃饭了";
    }

    public String Travel() {
        return "和" + this.name + "去旅行了";
    }

    public String Celebrate520() {
        return "和" + this.name + "过520";
    }

    public String TakePhoto() {
        return "和" + this.name + "拍照了";
    }

    public String CookFood() {
        return "和" + this.name + "做饭了";
    }

    public String Fit() {
        return "和" + this.name + "锻炼了";
    }

    public String Walk() {
        return "和" + this.name + "散步了";
    }
    // --- 互动行为方法结束 ---

    @Override
    public String toString() {
        return "GirlFriend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", home='" + home + '\'' +
                ", work='" + work + '\'' +
                ", hobby='" + hobby + '\'' +
                ", characterAttr='" + characterAttr + '\'' +
                ", birthday='" + birthday + '\'' +
                ", fromWorld='" + fromWorld + '\'' +
                '}';
    }
}