package oasis.oasisserver.listeners.asset;

import oasis.economyx.events.asset.AssetDephysicalizedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.PhysicalAsset;
import oasis.oasisserver.OasisServer;
import oasis.oasisserver.listeners.OasisCommandTranslator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public final class AssetDephysicalizeListener extends OasisCommandTranslator {
    public AssetDephysicalizeListener(@NonNull OasisServer server, @NonNull EconomyState state) {
        super(server, state);
    }

    @Override
    protected void onOasisRequest(@NonNull Player player, @NonNull Actor executor, @NonNull String[] args, @NonNull AccessLevel accessLevel) throws Exception {
        ItemStack item = player.getInventory().getItemInMainHand();

        // Issued banknotes are not stored in physicalized assets; No need to check

        UUID id = UUID.fromString(item.getItemMeta().getLore().get(0));
        for (PhysicalAsset pa : getState().getPhysicalizedAssets()) {
            if (pa.getUniqueId().equals(id)) {
                Bukkit.getPluginManager().callEvent(new AssetDephysicalizedEvent(pa, executor));
                break;
            }
        }
    }
}
