package android.ygo.core;

public enum Race {
    NULL(0, ""),
    ZHAN_SHI(1, "战士"),
    MO_FA_SHI(2, "魔法师"),
    TIAN_SHI(4, "天使"),
    E_MO(8, "恶魔"),
    BU_SI(16, "不死"),
    JI_XIE(32, "机械"),
    SHUI(64, "水"),
    YAN(128, "炎"),
    YAN_SHI(256, "岩石"),
    NIAO_SHOU(512, "鸟兽"),
    ZHI_WU(1024, "植物"),
    KUN_CHONG(2048, "昆虫"),
    LEI(4096, "雷"),
    LONG(8192, "龙"),
    SHOU(16384, "兽"),
    SHOU_ZHAN_SHI(32768, "兽战士"),
    KONG_LONG(65536, "恐龙"),
    YU(131072, "鱼"),
    HAI_LONG(262144, "海龙"),
    PA_CHONG(524288, "爬虫"),
    NIAN_DONG_LI(1048576, "念动力"),
    HUAN_SHEN_SHOU(2097152, "换神兽"),
    CHUANG_SHI_SHEN(4194304, "创世神")
    ;

    Race(int code, String chn){
        this.code = code;
        this.chn = chn;
    }
    private int code;
    private String chn;

    public Race getAttribute(int code) {
        for(Race race : Race.values()) {
            if(race.code == code) {
                return race;
            }
        }
        return NULL;
    }

    @Override
    public String toString() {
        return chn;
    }
}
