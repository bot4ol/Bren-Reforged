package committee.nova.mods.bren.common.network;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import committee.nova.mods.bren.client.network.ClientPacketHandlers;

import java.util.function.Supplier;

/**
 * S2CRecoilPack
 *
 * @author cnlimiter
 */
public class S2CRecoilPack {
    private final float recoil;


    public S2CRecoilPack(FriendlyByteBuf buf) {
        this.recoil = buf.readFloat();
    }

    public S2CRecoilPack(float recoil) {
        this.recoil = recoil;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(this.recoil);
    }

    public void run(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                ClientPacketHandlers.handleRecoil(this.recoil)
            );
        });
        ctx.get().setPacketHandled(true);
    }
}
