package me.ziim.ziimhud.config;

import me.ziim.ziimhud.modules.Armour.Armour;
import me.ziim.ziimhud.modules.Biome.Biome;
import me.ziim.ziimhud.modules.CoordModules.NetherPos;
import me.ziim.ziimhud.modules.CoordModules.Pos;
import me.ziim.ziimhud.modules.Direction.Direction;
import me.ziim.ziimhud.modules.FPS.FPS;
import me.ziim.ziimhud.modules.IP.IP;
import me.ziim.ziimhud.modules.Ping.Ping;
import me.ziim.ziimhud.modules.Potions.Potions;
import me.ziim.ziimhud.modules.TPS.TPS;
import me.ziim.ziimhud.modules.Time.Time;
import me.ziim.ziimhud.modules.XP.XP;

public class StorageConfig {
    public Armour.Storage armourStorage = new Armour.Storage();
    public TPS.Storage tpsStorage = new TPS.Storage();
    public Potions.Storage potionStorage = new Potions.Storage();
    public Ping.Storage pingStorage = new Ping.Storage();
    public Biome.Storage biomeStorage = new Biome.Storage();
    public Pos.Storage posStorage = new Pos.Storage();
    public NetherPos.Storage netherPosStorage = new NetherPos.Storage();
    public Direction.Storage directionStorage = new Direction.Storage();
    public FPS.Storage fpsStorage = new FPS.Storage();
    public Time.Storage timeStorage = new Time.Storage();
    public XP.Storage xpStorage = new XP.Storage();
    public IP.Storage ipStorage = new IP.Storage();
}
