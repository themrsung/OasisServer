package oasis.oasisserver;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import oasis.economyx.EconomyX;
import oasis.oasisserver.listeners.banking.BankAccountClosePacketAdapter;
import oasis.oasisserver.listeners.banking.BankAccountOpenPacketAdapter;
import oasis.oasisserver.listeners.banking.BankDepositPacketAdapter;
import oasis.oasisserver.listeners.banking.BankWithdrawPacketAdapter;
import oasis.oasisserver.listeners.card.CardActivatePacketAdapter;
import oasis.oasisserver.listeners.card.CardIssuePacketAdapter;
import oasis.oasisserver.listeners.card.CardRevokePacketAdapter;
import oasis.oasisserver.listeners.card.CardTerminalCreatePacketAdapter;
import oasis.oasisserver.listeners.create.CreateCompanyPacketAdapter;
import oasis.oasisserver.listeners.create.CreateInstitutionPacketAdapter;
import oasis.oasisserver.listeners.create.CreateNationPacketAdapter;
import oasis.oasisserver.listeners.create.CreateOrganizationPacketAdapter;
import oasis.oasisserver.listeners.guarantee.GuaranteeIssuePacketAdapter;
import oasis.oasisserver.listeners.guarantee.GuaranteeRevokePacketAdapter;
import oasis.oasisserver.listeners.market.MarketDelistAssetPacketAdapter;
import oasis.oasisserver.listeners.market.MarketListAssetPacketAdapter;
import oasis.oasisserver.listeners.pay.PayPacketAdapter;
import oasis.oasisserver.listeners.property.PropertyAbandonPacketAdapter;
import oasis.oasisserver.listeners.property.PropertyAuthorizeProtectionPacketAdapter;
import oasis.oasisserver.listeners.property.PropertyClaimPacketAdapter;
import oasis.oasisserver.listeners.property.PropertySetProtectorPacketAdapter;

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

        final OasisServer os = this;
        final ListenerPriority lp = ListenerPriority.MONITOR;
        final PacketType pt = PacketType.Play.Client.CUSTOM_PAYLOAD;

        PROTOCOL_MANAGER.addPacketListener(new CreateCompanyPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CreateInstitutionPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CreateNationPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CreateOrganizationPacketAdapter(os, lp, pt));

        PROTOCOL_MANAGER.addPacketListener(new BankAccountOpenPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new BankAccountClosePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new BankDepositPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new BankWithdrawPacketAdapter(os, lp, pt));

        PROTOCOL_MANAGER.addPacketListener(new CardActivatePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CardIssuePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CardRevokePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new CardTerminalCreatePacketAdapter(os, lp, pt));

        PROTOCOL_MANAGER.addPacketListener(new GuaranteeIssuePacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new GuaranteeRevokePacketAdapter(os, lp, pt));

        PROTOCOL_MANAGER.addPacketListener(new PayPacketAdapter(os, lp, pt));

        PROTOCOL_MANAGER.addPacketListener(new PropertyClaimPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new PropertyAbandonPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new PropertySetProtectorPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new PropertyAuthorizeProtectionPacketAdapter(os, lp, pt));

        PROTOCOL_MANAGER.addPacketListener(new MarketListAssetPacketAdapter(os, lp, pt));
        PROTOCOL_MANAGER.addPacketListener(new MarketDelistAssetPacketAdapter(os, lp, pt));

        // order#create#order
        // order#cancel#order

        // vault#create
        // vault#setkeeper (called by holder)
        // vault#authroizekeeping (called by keeper)

        // vote#create
        // vote#cast#votes

        // hostility#declare
        // hostility#revoke

        // dm#send (Handled through OasisServer)
    }
}
