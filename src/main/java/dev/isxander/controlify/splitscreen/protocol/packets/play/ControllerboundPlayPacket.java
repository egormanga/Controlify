package dev.isxander.controlify.splitscreen.protocol.packets.play;

import dev.isxander.controlify.utils.CUtil;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.resources.ResourceLocation;

public interface ControllerboundPlayPacket extends Packet<ControllerPlayPacketListener> {
    static <T extends Packet<?>> PacketType<T> createType(String id) {
        return new PacketType<>(PacketFlow.SERVERBOUND, CUtil.rl(id));
    }
}
