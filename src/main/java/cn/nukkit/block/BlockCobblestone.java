package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.utils.BlockColor;

/**
 * author: Angelic47
 * Nukkit Project
 */
public class BlockCobblestone extends BlockSolid {

    public BlockCobblestone() {
        this(0);
    }

    public BlockCobblestone(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return COBBLESTONE;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public double getResistance() {
        return 30;
    }
    
    
    @Override
    public BlockColor getColor(){
    	return BlockColor.STONE_BLOCK_COLOR;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public String getName() {
        return "Cobblestone";
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe() && item.getTier() >= ItemTool.TIER_WOODEN) {
            return new Item[]{
                    toItem()
            };
        } else {
            return new Item[0];
        }
    }

}
