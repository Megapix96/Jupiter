package cn.nukkit.entity.monster;

import cn.nukkit.Player;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;

/**
 * @author Box.
 */
public class EntityEndermite extends EntityMonster {
    public static final int NETWORK_ID = 55;

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    public EntityEndermite(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected void initEntity() {
    	this.setMaxHealth(8);
        super.initEntity();
    }
    
    @Override
    public float getWidth() {
        return 1.0f;
    }

    @Override
    public float getLength() {
        return 1.0f;
    }

    @Override
    public float getHeight() {
        return 0.5f;
    }

    @Override
    public String getName() {
        return "Endermite";
    }

    @Override
    public void spawnTo(Player player) {
        AddEntityPacket pk = new AddEntityPacket();
        pk.type = this.getNetworkId();
        pk.entityUniqueId = this.getId();
        pk.entityRuntimeId = this.getId();
        pk.x = (float) this.x;
        pk.y = (float) this.y;
        pk.z = (float) this.z;
        pk.speedX = (float) this.motionX;
        pk.speedY = (float) this.motionY;
        pk.speedZ = (float) this.motionZ;
        pk.metadata = this.dataProperties;
        player.dataPacket(pk);

        super.spawnTo(player);
    }
}
