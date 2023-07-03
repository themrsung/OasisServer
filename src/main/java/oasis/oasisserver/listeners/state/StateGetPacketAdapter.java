package oasis.oasisserver.listeners.state;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.oasisserver.listeners.OasisPacketAdapter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class StateGetPacketAdapter extends OasisPacketAdapter {
    public StateGetPacketAdapter(Plugin plugin, ListenerPriority listenerPriority, PacketType type) {
        super(plugin, listenerPriority, type);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        super.onPacketReceiving(event);
        Player player = event.getPlayer();
        Person person = getCopiedState().getPerson(player.getUniqueId());

        EconomyState state = getCensoredState(person);

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()).registerModule(new JodaModule());

        try {
            String serialized = mapper.writeValueAsString(state);
            // TODO send(state);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
