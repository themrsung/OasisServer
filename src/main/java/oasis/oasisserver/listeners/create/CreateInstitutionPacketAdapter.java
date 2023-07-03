package oasis.oasisserver.listeners.create;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketEvent;
import oasis.oasisserver.listeners.OasisPacketAdapter;
import org.bukkit.plugin.Plugin;

public final class CreateInstitutionPacketAdapter extends OasisPacketAdapter {
    public CreateInstitutionPacketAdapter(Plugin plugin, ListenerPriority listenerPriority, PacketType type) {
        super(plugin, listenerPriority, type);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        super.onPacketReceiving(event);

        // TODO Send initial capital from founder to created actor
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        super.onPacketSending(event);
    }
}
