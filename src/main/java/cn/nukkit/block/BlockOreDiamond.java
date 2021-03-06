package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemDiamond;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.BlockColor;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class BlockOreDiamond extends BlockSolid {


    public BlockOreDiamond() {
        this(0);
    }

    public BlockOreDiamond(int meta) {
        super(0);
    }

    @Override
    public int getId() {
        return DIAMOND_ORE;
    }
    
    @Override
    public BlockColor getColor(){
    	return BlockColor.STONE_BLOCK_COLOR;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public double getResistance() {
        return 15;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public String getName() {
        return "Diamond Ore";
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe() && item.getTier() >= ItemTool.TIER_IRON) {
            if (item.isSilkTouch()){
                return new Item[]{
                        this.toItem()
                };
            } else {
                return new Item[]{
                        new ItemDiamond()
                };
            }
        } else {
            return new Item[0];
        }
    }

    @Override
    public int getDropExp() {
        return new NukkitRandom().nextRange(3, 7);
    }
}
