package cn.nukkit.inventory.transaction;

import java.util.Set;

import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.transaction.action.InventoryAction;

/**
 * @author CreeperFace
 */
public interface InventoryTransaction {

    long getCreationTime();

    Set<InventoryAction> getActions();

    Set<Inventory> getInventories();

    void addAction(InventoryAction action);

    boolean canExecute();

    boolean execute();

    boolean hasExecuted();
}