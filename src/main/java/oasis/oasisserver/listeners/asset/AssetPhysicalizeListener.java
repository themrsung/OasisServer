package oasis.oasisserver.listeners.asset;

import oasis.economyx.events.asset.AssetPhysicalizedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.PhysicalAsset;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.oasisserver.OasisServer;
import oasis.oasisserver.listeners.OasisCommandTranslator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public final class AssetPhysicalizeListener extends OasisCommandTranslator {
    public AssetPhysicalizeListener(@NonNull OasisServer server, @NonNull EconomyState state) {
        super(server, state);
    }

    @Override
    protected void onOasisRequest(@NonNull Player player, @NonNull Actor executor, @NonNull String[] args, @NonNull AccessLevel accessLevel) throws Exception {
        if (!(accessLevel == AccessLevel.SELF || accessLevel == AccessLevel.DE_FACTO_SELF)) return;

        UUID assetId = UUID.fromString(args[0]);
        long quantity = Long.parseLong(args[1]);

        for (AssetStack as : executor.getAssets().get()) {
            if (as.getAsset().getUniqueId().equals(assetId)) {
                if (as.getQuantity() > quantity) {
                    if (as instanceof CashStack) return; // Cash requires a NoteIssuer to physicalize.

                    AssetStack stack = as.copy();
                    stack.setQuantity(quantity);

                    PhysicalAsset physical = PhysicalAsset.physicalizeAsset(stack);

                    Bukkit.getPluginManager().callEvent(new AssetPhysicalizedEvent(
                            executor,
                            stack,
                            player
                    ));
                }
            }
        }
    }
}
