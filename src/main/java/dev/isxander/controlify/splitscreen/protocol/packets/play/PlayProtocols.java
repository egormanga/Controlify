package dev.isxander.controlify.splitscreen.protocol.packets.play;

import dev.isxander.controlify.splitscreen.protocol.packets.common.CommonProtocols;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.ProtocolInfo;
import net.minecraft.network.protocol.ProtocolInfoBuilder;

public final class PlayProtocols {
    public static final ProtocolInfo<ControllerPlayPacketListener> CONTROLLERBOUND = ProtocolInfoBuilder.<ControllerPlayPacketListener, FriendlyByteBuf>serverboundProtocol(
            ConnectionProtocol.PLAY,
            builder -> BidirectionalPlayPacketListener.withBidirectionalPackets(
                    CommonProtocols.addControllerboundPackets(builder)
                            .addPacket(ControllerboundHelloPacket.TYPE, ControllerboundHelloPacket.CODEC)
                            .addPacket(ControllerboundKeepAlivePacket.TYPE, ControllerboundKeepAlivePacket.CODEC)
            )
    ).bind(FriendlyByteBuf::new);

    public static final ProtocolInfo<PawnPlayPacketListener> PAWNBOUND = ProtocolInfoBuilder.<PawnPlayPacketListener, FriendlyByteBuf>clientboundProtocol(
            ConnectionProtocol.PLAY,
            builder -> BidirectionalPlayPacketListener.withBidirectionalPackets(
                    CommonProtocols.addPawnboundPackets(builder)
                            .addPacket(PawnboundJoinMyServer.TYPE, PawnboundJoinMyServer.CODEC)
                            .addPacket(PawnboundKeepAlivePacket.TYPE, PawnboundKeepAlivePacket.CODEC)
                            .addPacket(PawnboundConfigureSplitscreenPacket.TYPE, PawnboundConfigureSplitscreenPacket.CODEC)
            )
    ).bind(FriendlyByteBuf::new);
}
