package me.ziim.ziimhud.config;

import me.ziim.ziimhud.modules.Armour.Armour;
import me.ziim.ziimhud.modules.Biome.Biome;
import me.ziim.ziimhud.modules.CoordModules.NetherPos;
import me.ziim.ziimhud.modules.CoordModules.Pos;
import me.ziim.ziimhud.modules.Direction.Direction;
import me.ziim.ziimhud.modules.FPS.FPS;
import me.ziim.ziimhud.modules.Ping.Ping;
import me.ziim.ziimhud.modules.TPS.TPS;

public class StorageConfig {
    public Armour.Storage armourStorage = new Armour.Storage();
    public TPS.Storage tpsStorage = new TPS.Storage();
    public Ping.Storage pingStorage = new Ping.Storage();
    public Biome.Storage biomeStorage = new Biome.Storage();
    public Pos.Storage posStorage = new Pos.Storage();
    public NetherPos.Storage netherPosStorage = new NetherPos.Storage();
    public Direction.Storage directionStorage = new Direction.Storage();
    public FPS.Storage fpsStorage = new FPS.Storage();
}
