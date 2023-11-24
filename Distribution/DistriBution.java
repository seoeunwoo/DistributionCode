package hello.distribution.distri;

import java.time.LocalDateTime;

public class DistriBution {

    // 회원가입
    private Long id;
    private String userid;
    private String password;
    private String name;
    private int age;
    private String address;
    private String email;


    // 물류이름
    private String householdgoods = "생활용품";
    private String kitchentools = "주방용품";
    private String food = "식품";
    private String healthfood = "건강보조식품";
    private String cosmetics = "화장품";


    // 물류별 최대 저장량
    private int householdgoods_maxcount = 100;
    private int kitchentools_maxcount = 100;
    private int food_maxcount = 100;
    private int healthfood_maxcount = 100;
    private int cosmetics_maxcount = 100;

    // 물류 들어올때 카운트
    private int householdgoods_count = 0;
    private int kitchentools_count = 0;
    private int food_count = 0;
    private int healthfood_count = 0;
    private int cosmetics_count = 0;

    // 입고 카운트
    private int householdgoods_receiving = 0;
    private int kitchentools_receiving = 0;
    private int food_receiving = 0;
    private int healthfood_receiving = 0;
    private int cosmetics_receiving = 0;

    // 출고 카운트
    private int householdgoods_release = 0;
    private int kitchentools_release = 0;
    private int food_release = 0;
    private int healthfood_release = 0;
    private int cosmetics_release = 0;


    public DistriBution()
    {

    }

    public void addhouseholdgoods_count(int amount)
    {
        householdgoods_count = householdgoods_count + amount;
    }

    public void addkitchentools_count(int amount)
    {
        kitchentools_count = kitchentools_count + amount;
    }

    public void addfood_count(int amount)
    {
        food_count = food_count + amount;
    }

    public void addhealthfood_count(int amount)
    {
        healthfood_count = healthfood_count + amount;
    }

    public void addcosmetics_count(int amount)
    {
        cosmetics_count = cosmetics_count + amount;
    }

    public void minushouseholdgoods_count(int amount)
    {
        householdgoods_count = householdgoods_count - amount;
    }

    public void minuskitchentools_count(int amount)
    {
        kitchentools_count = kitchentools_count - amount;
    }

    public void minusfood_count(int amount)
    {
        food_count = food_count - amount;
    }

    public void minushealthfood_count(int amount)
    {
        healthfood_count = healthfood_count - amount;
    }

    public void minuscosmetics_count(int amount)
    {
        cosmetics_count = cosmetics_count - amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHouseholdgoods() {
        return householdgoods;
    }

    public void setHouseholdgoods(String householdgoods) {
        this.householdgoods = householdgoods;
    }

    public String getKitchentools() {
        return kitchentools;
    }

    public void setKitchentools(String kitchentools) {
        this.kitchentools = kitchentools;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getHealthfood() {
        return healthfood;
    }

    public void setHealthfood(String healthfood) {
        this.healthfood = healthfood;
    }

    public String getCosmetics() {
        return cosmetics;
    }

    public void setCosmetics(String cosmetics) {
        this.cosmetics = cosmetics;
    }

    public int getHouseholdgoods_maxcount() {
        return householdgoods_maxcount;
    }

    public void setHouseholdgoods_maxcount(int householdgoods_maxcount) {
        this.householdgoods_maxcount = householdgoods_maxcount;
    }

    public int getKitchentools_maxcount() {
        return kitchentools_maxcount;
    }

    public void setKitchentools_maxcount(int kitchentools_maxcount) {
        this.kitchentools_maxcount = kitchentools_maxcount;
    }

    public int getFood_maxcount() {
        return food_maxcount;
    }

    public void setFood_maxcount(int food_maxcount) {
        this.food_maxcount = food_maxcount;
    }

    public int getHealthfood_maxcount() {
        return healthfood_maxcount;
    }

    public void setHealthfood_maxcount(int healthfood_maxcount) {
        this.healthfood_maxcount = healthfood_maxcount;
    }

    public int getCosmetics_maxcount() {
        return cosmetics_maxcount;
    }

    public void setCosmetics_maxcount(int cosmetics_maxcount) {
        this.cosmetics_maxcount = cosmetics_maxcount;
    }

    public int getHouseholdgoods_count() {
        return householdgoods_count;
    }

    public void setHouseholdgoods_count(int householdgoods_count) {
        this.householdgoods_count = householdgoods_count;
    }

    public int getKitchentools_count() {
        return kitchentools_count;
    }

    public void setKitchentools_count(int kitchentools_count) {
        this.kitchentools_count = kitchentools_count;
    }

    public int getFood_count() {
        return food_count;
    }

    public void setFood_count(int food_count) {
        this.food_count = food_count;
    }

    public int getHealthfood_count() {
        return healthfood_count;
    }

    public void setHealthfood_count(int healthfood_count) {
        this.healthfood_count = healthfood_count;
    }

    public int getCosmetics_count() {
        return cosmetics_count;
    }

    public void setCosmetics_count(int cosmetics_count) {
        this.cosmetics_count = cosmetics_count;
    }

    public int getHouseholdgoods_receiving() {
        return householdgoods_receiving;
    }

    public void setHouseholdgoods_receiving(int householdgoods_receiving) {
        this.householdgoods_receiving = householdgoods_receiving;
    }

    public int getKitchentools_receiving() {
        return kitchentools_receiving;
    }

    public void setKitchentools_receiving(int kitchentools_receiving) {
        this.kitchentools_receiving = kitchentools_receiving;
    }

    public int getFood_receiving() {
        return food_receiving;
    }

    public void setFood_receiving(int food_receiving) {
        this.food_receiving = food_receiving;
    }

    public int getHealthfood_receiving() {
        return healthfood_receiving;
    }

    public void setHealthfood_receiving(int healthfood_receiving) {
        this.healthfood_receiving = healthfood_receiving;
    }

    public int getCosmetics_receiving() {
        return cosmetics_receiving;
    }

    public void setCosmetics_receiving(int cosmetics_receiving) {
        this.cosmetics_receiving = cosmetics_receiving;
    }

    public int getHouseholdgoods_release() {
        return householdgoods_release;
    }

    public void setHouseholdgoods_release(int householdgoods_release) {
        this.householdgoods_release = householdgoods_release;
    }

    public int getKitchentools_release() {
        return kitchentools_release;
    }

    public void setKitchentools_release(int kitchentools_release) {
        this.kitchentools_release = kitchentools_release;
    }

    public int getFood_release() {
        return food_release;
    }

    public void setFood_release(int food_release) {
        this.food_release = food_release;
    }

    public int getHealthfood_release() {
        return healthfood_release;
    }

    public void setHealthfood_release(int healthfood_release) {
        this.healthfood_release = healthfood_release;
    }

    public int getCosmetics_release() {
        return cosmetics_release;
    }

    public void setCosmetics_release(int cosmetics_release) {
        this.cosmetics_release = cosmetics_release;
    }

    @Override
    public String toString() {
        return "DistriBution{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", householdgoods='" + householdgoods + '\'' +
                ", kitchentools='" + kitchentools + '\'' +
                ", food='" + food + '\'' +
                ", healthfood='" + healthfood + '\'' +
                ", cosmetics='" + cosmetics + '\'' +
                ", householdgoods_maxcount=" + householdgoods_maxcount +
                ", kitchentools_maxcount=" + kitchentools_maxcount +
                ", food_maxcount=" + food_maxcount +
                ", healthfood_maxcount=" + healthfood_maxcount +
                ", cosmetics_maxcount=" + cosmetics_maxcount +
                ", householdgoods_count=" + householdgoods_count +
                ", kitchentools_count=" + kitchentools_count +
                ", food_count=" + food_count +
                ", healthfood_count=" + healthfood_count +
                ", cosmetics_count=" + cosmetics_count +
                ", householdgoods_receiving=" + householdgoods_receiving +
                ", kitchentools_receiving=" + kitchentools_receiving +
                ", food_receiving=" + food_receiving +
                ", healthfood_receiving=" + healthfood_receiving +
                ", cosmetics_receiving=" + cosmetics_receiving +
                ", householdgoods_release=" + householdgoods_release +
                ", kitchentools_release=" + kitchentools_release +
                ", food_release=" + food_release +
                ", healthfood_release=" + healthfood_release +
                ", cosmetics_release=" + cosmetics_release +
                '}';
    }
}
