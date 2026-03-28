package com.navalarmament.network;

import com.navalarmament.NavalArmamentMod;
import com.navalarmament.tileentity.base.TENavalWeapon;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class NavalPacketHandler {

    public static final SimpleNetworkWrapper INSTANCE =
        cpw.mods.fml.common.network.NetworkRegistry.INSTANCE
            .newSimpleChannel("navalarmament");

    public static void init() {
        INSTANCE.registerMessage(ModeChangeHandler.class, ModeChangePacket.class, 0, Side.SERVER);
        INSTANCE.registerMessage(AmmoSyncHandler.class, AmmoSyncPacket.class, 2, Side.CLIENT);
    }

    public static void sendModeChange(int x, int y, int z, int mode) {
        INSTANCE.sendToServer(new ModeChangePacket(x, y, z, mode));
    }

    // パケット定義
    public static class ModeChangePacket implements IMessage {
        public int x, y, z, mode;
        public ModeChangePacket() {}
        public ModeChangePacket(int x, int y, int z, int mode) {
            this.x = x; this.y = y; this.z = z; this.mode = mode;
        }
        @Override public void fromBytes(ByteBuf buf) {
            x = buf.readInt(); y = buf.readInt(); z = buf.readInt(); mode = buf.readInt();
        }
        @Override public void toBytes(ByteBuf buf) {
            buf.writeInt(x); buf.writeInt(y); buf.writeInt(z); buf.writeInt(mode);
        }
    }

    // AmmoSyncパケット（サーバー→クライアント）
    public static class AmmoSyncPacket implements IMessage {
        public int x, y, z, ammo;
        public AmmoSyncPacket() {}
        public AmmoSyncPacket(int x, int y, int z, int ammo) {
            this.x = x; this.y = y; this.z = z; this.ammo = ammo;
        }
        @Override public void fromBytes(ByteBuf buf) {
            x = buf.readInt(); y = buf.readInt(); z = buf.readInt(); ammo = buf.readInt();
        }
        @Override public void toBytes(ByteBuf buf) {
            buf.writeInt(x); buf.writeInt(y); buf.writeInt(z); buf.writeInt(ammo);
        }
    }

    public static class AmmoSyncHandler implements IMessageHandler<AmmoSyncPacket, IMessage> {
        @Override
        public IMessage onMessage(final AmmoSyncPacket pkt, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override public void run() {
                    net.minecraft.world.World world = Minecraft.getMinecraft().theWorld;
                    if (world == null) return;
                    TileEntity te = world.getTileEntity(pkt.x, pkt.y, pkt.z);
                    if (te instanceof TENavalWeapon)
                        ((TENavalWeapon) te).setClientAmmoCount(pkt.ammo);
                }
            });
            return null;
        }
    }

    // ハンドラ
    public static class ModeChangeHandler implements IMessageHandler<ModeChangePacket, IMessage> {
        @Override
        public IMessage onMessage(ModeChangePacket pkt, MessageContext ctx) {
            net.minecraft.world.World world = ctx.getServerHandler().playerEntity.worldObj;
            TileEntity te = world.getTileEntity(pkt.x, pkt.y, pkt.z);
            if (te instanceof TENavalWeapon) {
                ((TENavalWeapon) te).setEngagementMode(pkt.mode);
                NavalArmamentMod.logger.info("Mode changed to " + pkt.mode + " at " + pkt.x + "," + pkt.y + "," + pkt.z);
            }
            return null;
        }
    }
}