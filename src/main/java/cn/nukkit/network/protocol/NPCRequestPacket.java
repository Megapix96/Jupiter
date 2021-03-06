package cn.nukkit.network.protocol;

/**
 * @author Megapix96
 */
public class NPCRequestPacket extends DataPacket {

    public long entityRuntimeId;
    public int requestType;
    public String command;
    public int actionType;

    @Override
    public byte pid() {
        return ProtocolInfo.NPC_REQUEST_PACKET;
    }

    @Override
    public void decode() {
        this.entityRuntimeId = this.getEntityRuntimeId();
        this.requestType = this.getByte();
        this.command = this.getString();
        this.actionType = this.getByte();
    }

    @Override
    public void encode() {
        //TODO
    }
}
