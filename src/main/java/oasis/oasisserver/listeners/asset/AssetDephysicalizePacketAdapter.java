package oasis.oasisserver.listeners.asset;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketEvent;
import oasis.oasisserver.listeners.OasisPacketAdapter;
import org.bukkit.plugin.Plugin;

public final class AssetDephysicalizePacketAdapter extends OasisPacketAdapter {
    public AssetDephysicalizePacketAdapter(Plugin plugin, ListenerPriority listenerPriority, PacketType type) {
        super(plugin, listenerPriority, type);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        super.onPacketReceiving(event);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        super.onPacketSending(event);
    }
}
