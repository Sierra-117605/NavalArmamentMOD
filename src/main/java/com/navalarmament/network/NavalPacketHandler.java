package com.navalarmament.network;

import com.navalarmament.NavalArmamentMod;
import com.navalarmament.system.TargetData;
import com.navalarmament.tileentity.base.TENavalWeapon;
import com.navalarmament.tileentity.usn.TECandD;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class NavalPacketHandler {
    public static final SimpleNetworkWrapper INSTANCE =
        cpw.mods.fml.common.network.NetworkRegistry.INSTANCE
            .newSimpleChannel("navalarmament");

    public static void init() {
        INSTANCE.registerMessage(ModeChangeHandler.class,    ModeChangePacket.class,    0, Side.SERVER);
        INSTANCE.registerMessage(WeaponSettingHandler.class, WeaponSettingPacket.class, 1, Side.SERVER);
        INSTANCE.registerMessage(AmmoSyncHandler.class,      AmmoSyncPacket.class,      2, Side.CLIENT);
        INSTANCE.registerMessage(CandDSyncHandler.class,     CandDSyncPacket.class,     3, Side.CLIENT);
    }

    public static void sendModeChange(int x, int y, int z, int mode) {
        INSTANCE.sendToServer(new ModeChangePacket(x, y, z, mode));
    }

    public static void sendWeaponSetting(int x, int y, int z, int mode, String ammoClass, String targetType) {
        INSTANCE.sendToServer(new WeaponSettingPacket(x, y, z, mode, ammoClass, targetType));
    }

    public static void sendAmmoSync(net.minecraft.entity.player.EntityPlayerMP player, int x, int y, int z, int ammo) {
        INSTANCE.sendTo(new AmmoSyncPacket(x, y, z, ammo), player);
    }

    public static void sendCandDSync(net.minecraft.world.World world, int x, int y, int z, List<TargetData> targets) {
        INSTANCE.sendToAllAround(
            new CandDSyncPacket(x, y, z, targets),
            new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 64.0));
    }

    // ModeChangePacket
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
    public static class ModeChangeHandler implements IMessageHandler<ModeChangePacket, IMessage> {
        @Override
        public IMessage onMessage(ModeChangePacket pkt, MessageContext ctx) {
            net.minecraft.world.World world = ctx.getServerHandler().playerEntity.worldObj;
            TileEntity te = world.getTileEntity(pkt.x, pkt.y, pkt.z);
            if (te instanceof TENavalWeapon)
                ((TENavalWeapon) te).setEngagementMode(pkt.mode);
            return null;
        }
    }

    // WeaponSettingPacket
    public static class WeaponSettingPacket implements IMessage {
        public int x, y, z, mode;
        public String ammoClass, targetType;
        public WeaponSettingPacket() {}
        public WeaponSettingPacket(int x, int y, int z, int mode, String ammoClass, String targetType) {
            this.x = x; this.y = y; this.z = z;
            this.mode = mode; this.ammoClass = ammoClass; this.targetType = targetType;
        }
        @Override public void fromBytes(ByteBuf buf) {
            x = buf.readInt(); y = buf.readInt(); z = buf.readInt();
            mode = buf.readInt();
            ammoClass  = ByteBufUtils.readUTF8String(buf);
            targetType = ByteBufUtils.readUTF8String(buf);
        }
        @Override public void toBytes(ByteBuf buf) {
            buf.writeInt(x); buf.writeInt(y); buf.writeInt(z);
            buf.writeInt(mode);
            ByteBufUtils.writeUTF8String(buf, ammoClass);
            ByteBufUtils.writeUTF8String(buf, targetType);
        }
    }
    public static class WeaponSettingHandler implements IMessageHandler<WeaponSettingPacket, IMessage> {
        @Override
        public IMessage onMessage(WeaponSettingPacket pkt, MessageContext ctx) {
            net.minecraft.world.World world = ctx.getServerHandler().playerEntity.worldObj;
            TileEntity te = world.getTileEntity(pkt.x, pkt.y, pkt.z);
            if (te instanceof TENavalWeapon) {
                TENavalWeapon w = (TENavalWeapon) te;
                w.setEngagementMode(pkt.mode);
                w.setPreferredAmmoClass(pkt.ammoClass);
                w.setPreferredTargetType(pkt.targetType);
            }
            return null;
        }
    }

    // AmmoSyncPacket (Server -> Client)
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
            net.minecraft.world.World world = Minecraft.getMinecraft().theWorld;
            if (world == null) return null;
            TileEntity te = world.getTileEntity(pkt.x, pkt.y, pkt.z);
            if (te instanceof TENavalWeapon)
                ((TENavalWeapon) te).setClientAmmoCount(pkt.ammo);
            return null;
        }
    }

    // CandDSyncPacket (Server -> Client): C&DターゲットリストをGUI表示用に同期
    public static class CandDSyncPacket implements IMessage {
        public int x, y, z;
        public List<String> names     = new ArrayList<String>();
        public List<String> types     = new ArrayList<String>();
        public List<Integer> distances = new ArrayList<Integer>();
        public List<Boolean> assigned  = new ArrayList<Boolean>();

        public CandDSyncPacket() {}

        public CandDSyncPacket(int x, int y, int z, List<TargetData> targets) {
            this.x = x; this.y = y; this.z = z;
            for (TargetData td : targets) {
                names.add(td.entity.getClass().getSimpleName().replace("Entity", ""));
                types.add(td.targetType != null ? td.targetType.name() : "UNK");
                distances.add((int) td.distance);
                assigned.add(td.assigned);
            }
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(x); buf.writeInt(y); buf.writeInt(z);
            buf.writeInt(names.size());
            for (int i = 0; i < names.size(); i++) {
                ByteBufUtils.writeUTF8String(buf, names.get(i));
                ByteBufUtils.writeUTF8String(buf, types.get(i));
                buf.writeInt(distances.get(i));
                buf.writeBoolean(assigned.get(i));
            }
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            x = buf.readInt(); y = buf.readInt(); z = buf.readInt();
            int count = buf.readInt();
            names     = new ArrayList<String>();
            types     = new ArrayList<String>();
            distances = new ArrayList<Integer>();
            assigned  = new ArrayList<Boolean>();
            for (int i = 0; i < count; i++) {
                names.add(ByteBufUtils.readUTF8String(buf));
                types.add(ByteBufUtils.readUTF8String(buf));
                distances.add(buf.readInt());
                assigned.add(buf.readBoolean());
            }
        }
    }

    public static class CandDSyncHandler implements IMessageHandler<CandDSyncPacket, IMessage> {
        @Override
        public IMessage onMessage(CandDSyncPacket pkt, MessageContext ctx) {
            net.minecraft.world.World world = Minecraft.getMinecraft().theWorld;
            if (world == null) return null;
            TileEntity te = world.getTileEntity(pkt.x, pkt.y, pkt.z);
            if (!(te instanceof TECandD)) return null;
            List<TECandD.ClientTargetInfo> list = new ArrayList<TECandD.ClientTargetInfo>();
            for (int i = 0; i < pkt.names.size(); i++) {
                TECandD.ClientTargetInfo info = new TECandD.ClientTargetInfo();
                info.entityName     = pkt.names.get(i);
                info.targetTypeName = pkt.types.get(i);
                info.distance       = pkt.distances.get(i);
                info.assigned       = pkt.assigned.get(i);
                list.add(info);
            }
            ((TECandD) te).setClientTargets(list);
            return null;
        }
    }
}