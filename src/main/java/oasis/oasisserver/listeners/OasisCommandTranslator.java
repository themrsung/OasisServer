package oasis.oasisserver.listeners;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.state.EconomyState;
import oasis.oasisserver.OasisServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public abstract class OasisCommandTranslator implements Listener {
    public OasisCommandTranslator(@NonNull OasisServer server, @NonNull EconomyState state) {
        Bukkit.getPluginManager().registerEvents(this, server);

        this.server = server;
        this.state = state;
    }

    @NonNull
    private final OasisServer server;
    @NonNull
    private final EconomyState state;

    @NonNull
    public OasisServer getServer() {
        return server;
    }

    @NonNull
    public EconomyState getState() {
        return state;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public final void onCommand(PlayerCommandPreprocessEvent e) {
        String command = e.getMessage();
        String[] args = command.split("#");

        if (args.length > 1) {
            String arg0 = args[0];
            Person caller = state.getPerson(e.getPlayer().getUniqueId());

            if (arg0.equalsIgnoreCase("/oasis")) {
                String[] argsAfter = Arrays.copyOfRange(args, 1, args.length);

                e.setCancelled(true);
                try {
                    onOasisRequest(e.getPlayer(), caller, argsAfter, AccessLevel.SELF);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else if (arg0.equalsIgnoreCase("/oasissudo") && args.length > 3) {
                UUID executorId = UUID.fromString(args[1]);

                try {
                    Actor executor = state.getActor(executorId);
                    AccessLevel level = AccessLevel.NONE;

                    if (executor instanceof Person) level = executor.equals(caller) ? AccessLevel.SELF : level;

                    if (executor instanceof Employer em) {
                        level = em.getEmployees().contains(caller) ? AccessLevel.EMPLOYEE : level;
                        level = em.getDirectors().contains(caller) ? AccessLevel.DIRECTOR : level;
                    }

                    if (executor instanceof Representable r) {
                        level = Objects.equals(r.getRepresentative(), caller) ? AccessLevel.DE_FACTO_SELF : level;
                    }

                    if (level != AccessLevel.NONE) {
                        e.setCancelled(true);
                        String[] argsAfter = Arrays.copyOfRange(args, 2, args.length);
                        try {
                            onOasisRequest(e.getPlayer(), executor, argsAfter, level);
                        } catch(Exception e1) {
                            throw new RuntimeException();
                        }
                    }
                } catch (IllegalArgumentException error) {
                    Bukkit.getLogger().info("Invalid Oasis sudo request from: " + e.getPlayer().getName());
                }
            }
        }
    }

    protected abstract void onOasisRequest(@NonNull Player player, @NonNull Actor executor, @NonNull String[] args, @NonNull AccessLevel accessLevel) throws Exception;

    // Protected to not litter indexes.
    // Not hidden for security reasons.
    protected enum AccessLevel {
        SELF,
        DE_FACTO_SELF,
        DIRECTOR,
        EMPLOYEE,
        NONE;
    }
}
