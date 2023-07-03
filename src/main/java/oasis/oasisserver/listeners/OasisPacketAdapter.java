package oasis.oasisserver.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.state.EconomyState;
import oasis.oasisserver.OasisServer;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public abstract class OasisPacketAdapter extends PacketAdapter {
    public OasisPacketAdapter(Plugin plugin, ListenerPriority listenerPriority, PacketType type) {
        super(plugin, listenerPriority, type);
    }

    /**
     * Used for normal permission checks.
     */
    protected boolean checkPermission(@NonNull Actor actor, @NonNull Person executor) {
        boolean hasAccess = actor.equals(executor);

        if (actor instanceof Representable r) {
            hasAccess = hasAccess || Objects.equals(r.getRepresentative(), executor);
        }

        if (actor instanceof Employer e) {
            hasAccess = hasAccess || e.getEmployees().contains(executor);
            hasAccess = hasAccess || e.getDirectors().contains(executor);
        }

        return hasAccess;
    }

    /**
     * Used for sensitive permission checks.
     */
    protected boolean checkAdminPermission(@NonNull Actor actor, @NonNull Person executor) {
        boolean hasAccess = actor.equals(executor);

        if (actor instanceof Representable r) {
            hasAccess = hasAccess || Objects.equals(r.getRepresentative(), executor);
        }

        if (actor instanceof Employer e) {
            hasAccess = hasAccess || e.getDirectors().contains(executor);
        }

        return hasAccess;
    }

    @Override
    public OasisServer getPlugin() {
        if (!(super.getPlugin() instanceof OasisServer)) throw new RuntimeException();

        return (OasisServer) super.getPlugin();
    }

    @NonNull
    protected EconomyState getCopiedState() {
        return getPlugin().getCopiedState();
    }

    @NonNull
    protected EconomyState getCensoredState(@NonNull Person viewer) {
        return getPlugin().getCensoredState(viewer);
    }
}
