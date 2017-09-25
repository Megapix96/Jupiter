package cn.nukkit.network.protocol;

import cn.nukkit.math.BlockVector3;

/**
 * @author Nukkit Project Team
 */
public class PlayerActionPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_ACTION_PACKET;

    public static final byte ACTION_START_BREAK = 0;
    public static final byte ACTION_ABORT_BREAK = 1;
    public static final byte ACTION_STOP_BREAK = 2;
    public static final byte ACTION_GET_UPDATED_BLOCK = 3;
    public static final byte ACTION_DROP_ITEM = 4;
    public static final byte ACTION_START_SLEEPING = 5;
    public static final byte ACTION_STOP_SLEEPING = 6;
    public static final byte ACTION_RESPAWN = 7;
    public static final byte ACTION_JUMP = 8;
    public static final byte ACTION_START_SPRINT = 9;
    public static final byte ACTION_STOP_SPRINT = 10;
    public static final byte ACTION_START_SNEAK = 11;
    public static final byte ACTION_STOP_SNEAK = 12;
    public static final byte ACTION_DIMENSION_CHANGE = 13;
    public static final byte ACTION_DIMENSION_CHANGE_ACK = 14;
    public static final byte ACTION_START_GLIDE = 15;
    public static final byte ACTION_STOP_GLIDE = 16;
    public static final byte ACTION_WORLD_IMMUTABLE = 17;
    public static final byte ACTION_CONTINUE_BREAK = 18;

    public static final byte ACTION_SET_ENCHANTMENT_SEED = 20;

    public long entityRuntimeId;
    public int action;
    public int x;
    public int y;
    public int z;
    public int face;


    @Override
    public void decode() {
        this.entityRuntimeId = this.getVarLong();
        this.action = this.getVarInt();
        BlockVector3 v = this.getBlockVector3();
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.face = this.getVarInt();
    }

    @Override
    public void encode() {
        this.reset();
        this.putVarLong(this.entityRuntimeId);
        this.putVarInt(this.action);
        this.putBlockVector3(this.x, this.y, this.z);
        this.putVarInt(this.face);
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

}