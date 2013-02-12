package android.ygo.core;

public enum Race {
    NULL(0),
    ZHAN_SHI(1),
    MO_FA_SHI(2),
    TIAN_SHI(4),
    E_MO(8),
    BU_SI(16),
    JI_XIE(32),
    SHUI(64),
    YAN(128),
    YAN_SHI(256),
    NIAO_SHOU(512),
    ZHI_WU(1024),
    KUN_CHONG(2048),
    LEI(4096),
    LONG(8192),
    SHOU(16384),
    SHOU_ZHAN_SHI(32768),
    KONG_LONG(65536),
    YU(131072),
    HAI_LONG(262144),
    PA_CHONG(524288),
    NIAN_DONG_LI(1048576),
    HUAN_SHEN_SHOU(2097152),
    CHUANG_SHI_SHEN(4194304)
    ;

    Race(int code){
        this.code = code;
    }
    private int code;

    public Race getAttribute(int code) {
        for(Race race : Race.values()) {
            if(race.code == code) {
                return race;
            }
        }
        return NULL;
    }
}
