package committee.nova.mods.bren.common.network;

import net.minecraft.network.FriendlyByteBuf;
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
public class S2CShootSoundPack {
    private final float volume;


    public S2CShootSoundPack(FriendlyByteBuf buf) {
        this.volume = buf.readFloat();
    }

    public S2CShootSoundPack(float volume) {
        this.volume = volume;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(this.volume);
    }

    public void run(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                ClientPacketHandlers.handleShootSound(this.volume)
            );
        });
        ctx.get().setPacketHandled(true);
    }
}
