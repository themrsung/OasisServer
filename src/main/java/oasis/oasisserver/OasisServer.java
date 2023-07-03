package oasis.oasisserver;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;

import oasis.economyx.EconomyX;

import oasis.oasisserver.listeners.asset.AssetDephysicalizeListener;
import oasis.oasisserver.listeners.asset.AssetPhysicalizeListener;
import oasis.oasisserver.listeners.banking.*;
import oasis.oasisserver.listeners.card.*;
import oasis.oasisserver.listeners.create.*;
import oasis.oasisserver.listeners.guarantee.*;
import oasis.oasisserver.listeners.market.*;
import oasis.oasisserver.listeners.pay.*;
import oasis.oasisserver.listeners.property.*;
import oasis.oasisserver.listeners.social.*;
import oasis.oasisserver.listeners.state.StateGetPacketAdapter;
import oasis.oasisserver.listeners.vaulting.*;
import oasis.oasisserver.listeners.voting.*;
import oasis.oasisserver.listeners.warfare.*;

import oasis.oasisserver.types.Message;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Main class of OasisServer.
 * <p>
 *     Oasis uses a custom Minecraft client, and handles network IO via packets.
 *     In-game commands are not used. (even for basic messaging or teleportation)
 *     Vanilla clients can still join, but cannot use any of the features.
 *     (unless they create their own client compatible with this implementation)
 * </p>
 */
public final class OasisServer extends EconomyX {
    private static final ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

    @Override
    public void onEnable() {
        super.onEnable();

        Bukkit.getLogger().info("Starting OasisServer bootstrap.");
        Bukkit.getLogger().info("This is an EconomyX product.");

        final OasisServer os = this;
        final ListenerPriority lp = ListenerPriority.MONITOR;
        final PacketType pt = PacketType.Play.Client.CUSTOM_PAYLOAD;

        // Asset
        PROTOCOL_MANAGER.addPacketListener(new AssetPhysicalizeListener(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new AssetDephysicalizeListener(os, lp, pt));

        // Creation
        PROTOCOL_MANAGER.addPacketListener(new CreateCompanyPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CreateInstitutionPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CreateNationPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CreateOrganizationPacketAdapter(os, lp, pt));

        // Banking
        PROTOCOL_MANAGER.addPacketListener(new BankAccountOpenPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new BankAccountClosePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new BankDepositPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new BankWithdrawPacketAdapter(os, lp, pt));

        // Card
        PROTOCOL_MANAGER.addPacketListener(new CardActivatePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CardIssuePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CardRevokePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CardTerminalCreatePacketAdapter(os, lp, pt));

        // Guarantee
        PROTOCOL_MANAGER.addPacketListener(new GuaranteeIssuePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new GuaranteeRevokePacketAdapter(os, lp, pt));

        // Payment
        PROTOCOL_MANAGER.addPacketListener(new PayPacketAdapter(os, lp, pt));

        // Property
        PROTOCOL_MANAGER.addPacketListener(new PropertyClaimPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new PropertyAbandonPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new PropertySetProtectorPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new PropertyAuthorizeProtectionPacketAdapter(os, lp, pt));

        // Market
        PROTOCOL_MANAGER.addPacketListener(new MarketListAssetPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new MarketDelistAssetPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new MarketPlaceOrderPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new MarketCancelOrderPacketAdapter(os, lp, pt));

        // Vault
        PROTOCOL_MANAGER.addPacketListener(new VaultCreatePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new VaultAuthorizeKeepingPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new VaultSetKeeperPacketAdapter(os, lp, pt));

        // Vote
        PROTOCOL_MANAGER.addPacketListener(new VoteCreatePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new VoteCastPacketAdapter(os, lp, pt));

        // Warfare
        PROTOCOL_MANAGER.addPacketListener(new HostilityDeclarePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new HostilityRevokePacketAdapter(os, lp, pt));

        // Social
        PROTOCOL_MANAGER.addPacketListener(new DirectMessagePacketAdapter(os, lp, pt));

        // State
        PROTOCOL_MANAGER.addPacketListener(new StateGetPacketAdapter(os, lp, pt));

        Bukkit.getLogger().info("OasisServer bootstrap complete!");
    }

    @Override
    public void onDisable() {
        super.onDisable();

        Bukkit.getLogger().info("OasisServer is shutting down.");
        Bukkit.getLogger().info("This is an EconomyX product.");
    }

    private transient final List<Message> messages = new ArrayList<>();

    public List<Message> getMessagesByRecipient(@NonNull UUID recipient) {
        List<Message> list = new ArrayList<>();

        for (Message m : messages) {
            if (m.recipient().equals(recipient)) {
                list.add(m);
            }
        }

        return list;
    }

    public List<Message> getMessagesBySender(@NonNull UUID sender) {
        List<Message> list = new ArrayList<>();

        for (Message m : messages) {
            if (m.sender().equals(sender)) {
                list.add(m);
            }
        }

        return list;
    }

    public void addMessage(@NonNull Message message) {
        this.messages.add(message);
    }
}
