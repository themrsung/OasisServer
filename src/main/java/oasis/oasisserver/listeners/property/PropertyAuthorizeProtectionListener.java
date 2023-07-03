package oasis.oasisserver.listeners.property;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.state.EconomyState;
import oasis.oasisserver.OasisServer;
import oasis.oasisserver.listeners.OasisCommandTranslator;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PropertyAuthorizeProtectionListener extends OasisCommandTranslator {
    public PropertyAuthorizeProtectionListener(@NonNull OasisServer server, @NonNull EconomyState state) {
        super(server, state);
    }

    @Override
    protected void onOasisRequest(@NonNull Actor executor, @NonNull String[] args, @NonNull AccessLevel accessLevel) {
        
    }
}
