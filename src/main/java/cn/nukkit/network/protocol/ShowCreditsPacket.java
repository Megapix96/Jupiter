package cn.nukkit.network.protocol;

public class ShowCreditsPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.SHOW_CREDITS_PACKET;

    public static final int STATUS_START_CREDITS = 0;
    public static final int STATUS_END_CREDITS = 1;

    public long entityRuntimeId;
    public int status;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.putEntityRuntimeId(this.entityRuntimeId);
        this.putVarInt(this.status);
    }
}
