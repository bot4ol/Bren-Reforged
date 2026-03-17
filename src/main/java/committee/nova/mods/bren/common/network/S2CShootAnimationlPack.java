package committee.nova.mods.bren.common.network;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraft.network.FriendlyByteBuf;
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
public class S2CShootAnimationlPack {


    public S2CShootAnimationlPack(FriendlyByteBuf buf) {
    }

    public S2CShootAnimationlPack() {
    }

    public void write(FriendlyByteBuf buf) {
    }

    public void run(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                ClientPacketHandlers.handleShootAnimation()
            );
        });
        ctx.get().setPacketHandled(true);
    }
}
