package committee.nova.mods.bren.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import committee.nova.mods.bren.client.network.ClientPacketHandlers;

import java.util.function.Supplier;

/**
 * S2CTotemPacket
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/28 14:02
 */
public class S2CShotPack {
    private final Vec3 origin;
    private final Vec3 direction;
    private final boolean ejectCasing;

    public S2CShotPack(FriendlyByteBuf buf) {
        this.origin = new  Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.direction = new  Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.ejectCasing = buf.readBoolean();
    }

    public S2CShotPack(Vec3 origin, Vec3 direction, boolean ejectCasing) {
        this.origin = origin;
        this.direction = direction;
        this.ejectCasing = ejectCasing;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(this.origin.x);
        buf.writeDouble(this.origin.y);
        buf.writeDouble(this.origin.z);
        buf.writeDouble(this.direction.x);
        buf.writeDouble(this.direction.y);
        buf.writeDouble(this.direction.z);
        buf.writeBoolean(this.ejectCasing);
    }

    public void run(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                ClientPacketHandlers.handleShot(this.origin, this.direction, this.ejectCasing)
            );
        });
        ctx.get().setPacketHandled(true);
    }
}
