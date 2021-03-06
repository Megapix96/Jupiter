package cn.nukkit.entity.animal;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemChickenCooked;
import cn.nukkit.item.ItemChickenRaw;
import cn.nukkit.item.ItemFeather;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * Author: BeYkeRYkt
 * Nukkit Project
 */
public class EntityChicken extends EntityAnimal {

    public static final int NETWORK_ID = 10;

    public EntityChicken(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public float getWidth() {
        if (isBaby()) {
            return 0.2f;
        }
        return 0.4f;
    }

    @Override
    public float getHeight() {
        if (isBaby()) {
            return 0.35f;
        }
        return 0.7f;
    }

    @Override
    public float getEyeHeight() {
        if (isBaby()) {
            return 0.51f;
        }
        return 0.7f;
    }

    @Override
    public String getName() {
        return this.getNameTag();
    }

    @Override
    public Item[] getDrops() {
        if (this.isOnFire()) {
            return new Item[]{new ItemChickenCooked(), new ItemFeather(0, random.nextRange(0, 2))};
        } else {
            return new Item[]{new ItemChickenRaw(), new ItemFeather(0, random.nextRange(0, 2))};
        }
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    protected void initEntity() {
        this.setMaxHealth(4);
        super.initEntity();
    }

    @Override
    public boolean isBreedingItem(Item item) {
        int id = item.getId();

        return id == Item.WHEAT_SEEDS || id == Item.MELON_SEEDS || id == Item.PUMPKIN_SEEDS || id == Item.BEETROOT_SEEDS;
    }
}
