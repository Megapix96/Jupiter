package cn.nukkit.network.protocol.types;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.transaction.action.CreativeInventoryAction;
import cn.nukkit.inventory.transaction.action.DropItemAction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.InventoryTransactionPacket;

public class NetworkInventoryAction {

    public static final int SOURCE_CONTAINER = 0;

    public static final int SOURCE_WORLD = 2; //drop/pickup item entity
    public static final int SOURCE_CREATIVE = 3;
    public static final int SOURCE_TODO = 99999;

    public static final int SOURCE_TYPE_CRAFTING_ADD_INGREDIENT = -2;
    public static final int SOURCE_TYPE_CRAFTING_REMOVE_INGREDIENT = -3;
    public static final int SOURCE_TYPE_CRAFTING_RESULT = -4;
    public static final int SOURCE_TYPE_CRAFTING_USE_INGREDIENT = -5;

    public static final int SOURCE_TYPE_ANVIL_INPUT = -10;
    public static final int SOURCE_TYPE_ANVIL_MATERIAL = -11;
    public static final int SOURCE_TYPE_ANVIL_RESULT = -12;
    public static final int SOURCE_TYPE_ANVIL_OUTPUT = -13;

    public static final int SOURCE_TYPE_ENCHANT_INPUT = -15;
    public static final int SOURCE_TYPE_ENCHANT_MATERIAL = -16;
    public static final int SOURCE_TYPE_ENCHANT_OUTPUT = -17;

    public static final int SOURCE_TYPE_TRADING_INPUT_1 = -20;
    public static final int SOURCE_TYPE_TRADING_INPUT_2 = -21;
    public static final int SOURCE_TYPE_TRADING_USE_INPUTS = -22;
    public static final int SOURCE_TYPE_TRADING_OUTPUT = -23;

    public static final int SOURCE_TYPE_BEACON = -24;

    public static final int SOURCE_TYPE_CONTAINER_DROP_CONTENTS = -100;


    public int sourceType;
    public int windowId;
    public long unknown;
    public int inventorySlot;
    public Item oldItem;
    public Item newItem;

    public NetworkInventoryAction read(InventoryTransactionPacket packet) {
        this.sourceType = (int) packet.getUnsignedVarInt();

        switch (this.sourceType) {
            case SOURCE_CONTAINER:
                this.windowId = packet.getVarInt();
                break;
            case SOURCE_WORLD:
                this.unknown = packet.getUnsignedVarInt();
                break;
            case SOURCE_CREATIVE:
                break;
            case SOURCE_TODO:
                this.windowId = packet.getVarInt();
                break;
        }

        this.inventorySlot = (int) packet.getUnsignedVarInt();
        this.oldItem = packet.getSlot();
        this.newItem = packet.getSlot();

        return this;
    }

    public void write(InventoryTransactionPacket packet) {
        packet.putUnsignedVarInt(this.sourceType);

        switch (this.sourceType) {
            case SOURCE_CONTAINER:
                packet.putVarInt(this.windowId);
                break;
            case SOURCE_WORLD:
                packet.putUnsignedVarInt(this.unknown);
                break;
            case SOURCE_CREATIVE:
                break;
            case SOURCE_TODO:
                packet.putVarInt(this.windowId);
                break;
        }

        packet.putUnsignedVarInt(this.inventorySlot);
        packet.putSlot(this.oldItem);
        packet.putSlot(this.newItem);
    }

    public InventoryAction createInventoryAction(Player player) {
        switch (this.sourceType) {
            case SOURCE_CONTAINER:
                if (this.windowId == ContainerIds.ARMOR) {
                    this.inventorySlot += 36;
                    this.windowId = ContainerIds.INVENTORY;
                }

                Inventory window = player.getWindowById(this.windowId);
                if (window != null) {
                    return new SlotChangeAction(window, this.inventorySlot, this.oldItem, this.newItem);
                }

                throw new RuntimeException("Player " + player.getName() + " has no open container with window ID " + this.windowId);
            case SOURCE_WORLD:
                if (this.inventorySlot != InventoryTransactionPacket.ACTION_MAGIC_SLOT_DROP_ITEM) {
                    throw new RuntimeException("Only expecting drop-item world actions from the client!");
                }

                return new DropItemAction(this.oldItem, this.newItem);
            case SOURCE_CREATIVE:
                int type;

                switch (this.inventorySlot) {
                    case InventoryTransactionPacket.ACTION_MAGIC_SLOT_CREATIVE_DELETE_ITEM:
                        type = CreativeInventoryAction.TYPE_DELETE_ITEM;
                        break;
                    case InventoryTransactionPacket.ACTION_MAGIC_SLOT_CREATIVE_CREATE_ITEM:
                        type = CreativeInventoryAction.TYPE_CREATE_ITEM;
                        break;
                    default:
                        throw new RuntimeException("Unexpected creative action type " + this.inventorySlot);

                }

                return new CreativeInventoryAction(this.oldItem, this.newItem, type);
            case SOURCE_TODO:
                switch (this.windowId) {
                    case SOURCE_TYPE_CRAFTING_ADD_INGREDIENT:
                    case SOURCE_TYPE_CRAFTING_REMOVE_INGREDIENT:
                        System.out.println("crafting change ingredient, old: " + this.oldItem + "   new: " + this.newItem + "    slot: " + this.inventorySlot);
                        window = player.getCraftingGrid();
                        return new SlotChangeAction(window, this.inventorySlot, this.oldItem, this.newItem);
                    case SOURCE_TYPE_CRAFTING_RESULT:
                        window = player.getCraftingGrid();

                        System.out.println("crafting result, old: " + this.oldItem + "   new: " + this.newItem + "    slot: " + this.inventorySlot);
                        break;
                    case SOURCE_TYPE_CRAFTING_USE_INGREDIENT:
                        System.out.println("crafting use ingredient, old: " + this.oldItem + "   new: " + this.newItem + "    slot: " + this.inventorySlot);
                        break;
                    case SOURCE_TYPE_CONTAINER_DROP_CONTENTS:
                        window = player.getCraftingGrid();

                        inventorySlot = window.first(this.oldItem, true);
                        if (inventorySlot == -1) {
                            throw new RuntimeException("Fake container " + window.getClass().getName() + " for " + player.getName() + " does not contain " + this.oldItem);
                        }
                        return new SlotChangeAction(window, inventorySlot, this.oldItem, this.newItem);
                }

                throw new RuntimeException("Player " + player.getName() + " has no open container with window ID " + this.windowId);
            default:
                throw new RuntimeException("Unknown inventory source type " + this.sourceType);
        }
    }
}