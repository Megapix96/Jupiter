package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.event.entity.ProjectileLaunchEvent;
import cn.nukkit.level.sound.LaunchSound;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;

/**
 * @author CreeperFace
 */
public abstract class ProjectileItem extends Item {

    public ProjectileItem(int id, Integer meta, int count, String name) {
        super(id, meta, count, name);
    }

    abstract public String getProjectileEntityType();

    abstract public float getThrowForce();

    public boolean onClickAir(Player player, Vector3 directionVector) {
        CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", player.x))
                        .add(new DoubleTag("", player.y + player.getEyeHeight()))
                        .add(new DoubleTag("", player.z)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", directionVector.x))
                        .add(new DoubleTag("", directionVector.y))
                        .add(new DoubleTag("", directionVector.z)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", (float) player.yaw))
                        .add(new FloatTag("", (float) player.pitch)));

        Entity snowball = Entity.createEntity(this.getProjectileEntityType(), player.getLevel().getChunk(player.getFloorX() >> 4, player.getFloorZ() >> 4), nbt, player);
        snowball.setMotion(snowball.getMotion().multiply(this.getThrowForce()));

        this.count--;

        if (snowball instanceof EntityProjectile) {
            ProjectileLaunchEvent ev = new ProjectileLaunchEvent((EntityProjectile) snowball);

            player.getServer().getPluginManager().callEvent(ev);
            if (ev.isCancelled()) {
                snowball.kill();
            } else {
                snowball.spawnToAll();
                player.getLevel().addSound(new LaunchSound(player), player.getViewers().values());
            }
        } else {
            snowball.spawnToAll();
        }

        return true;
    }
}