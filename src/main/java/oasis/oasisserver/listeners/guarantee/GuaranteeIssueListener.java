package oasis.oasisserver.listeners.guarantee;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.state.EconomyState;
import oasis.oasisserver.OasisServer;
import oasis.oasisserver.listeners.OasisCommandTranslator;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class GuaranteeIssueListener extends OasisCommandTranslator {
    public GuaranteeIssueListener(@NonNull OasisServer server, @NonNull EconomyState state) {
        super(server, state);
    }

    @Override
    protected void onOasisRequest(@NonNull Player player, @NonNull Actor executor, @NonNull String[] args, @NonNull AccessLevel accessLevel) throws Exception {

    }
}
