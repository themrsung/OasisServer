package oasis.oasisserver.listeners.card;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.state.EconomyState;
import oasis.oasisserver.OasisServer;
import oasis.oasisserver.listeners.OasisCommandTranslator;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CardActivateListener extends OasisCommandTranslator {
    public CardActivateListener(@NonNull OasisServer server, @NonNull EconomyState state) {
        super(server, state);
    }

    @Override
    protected void onOasisRequest(@NonNull Actor executor, @NonNull String[] args, @NonNull AccessLevel accessLevel) {
        
    }
}
