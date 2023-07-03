package oasis.oasisserver;

import oasis.economyx.EconomyX;
import oasis.oasisserver.listeners.asset.AssetDephysicalizeListener;
import oasis.oasisserver.listeners.asset.AssetPhysicalizeListener;
import oasis.oasisserver.listeners.banking.BankAccountCloseListener;
import oasis.oasisserver.listeners.banking.BankAccountOpenListener;
import oasis.oasisserver.listeners.banking.BankDepositListener;
import oasis.oasisserver.listeners.banking.BankWithdrawListener;
import oasis.oasisserver.listeners.card.CardActivateListener;
import oasis.oasisserver.listeners.card.CardIssueListener;
import oasis.oasisserver.listeners.card.CardRevokeListener;
import oasis.oasisserver.listeners.card.CardTerminalCreateListener;
import oasis.oasisserver.listeners.contract.ContractCreateListener;
import oasis.oasisserver.listeners.contract.ContractForgiveListener;
import oasis.oasisserver.listeners.contract.OptionExerciseListener;
import oasis.oasisserver.listeners.create.CreateCompanyListener;
import oasis.oasisserver.listeners.create.CreateInstitutionListener;
import oasis.oasisserver.listeners.create.CreateNationListener;
import oasis.oasisserver.listeners.create.CreateOrganizationListener;
import oasis.oasisserver.listeners.guarantee.GuaranteeIssueListener;
import oasis.oasisserver.listeners.guarantee.GuaranteeRevokeListener;
import oasis.oasisserver.listeners.market.MarketCancelOrderListener;
import oasis.oasisserver.listeners.market.MarketPlaceOrderListener;
import oasis.oasisserver.listeners.pay.PayListener;
import oasis.oasisserver.listeners.property.PropertyAbandonListener;
import oasis.oasisserver.listeners.property.PropertyAuthorizeProtectionListener;
import oasis.oasisserver.listeners.property.PropertyClaimListener;
import oasis.oasisserver.listeners.property.PropertySetProtectorListener;
import oasis.oasisserver.listeners.social.DirectMessageListener;
import oasis.oasisserver.listeners.state.StateGetListener;
import oasis.oasisserver.listeners.vaulting.VaultAuthorizeKeepingListener;
import oasis.oasisserver.listeners.vaulting.VaultCreateListener;
import oasis.oasisserver.listeners.vaulting.VaultSetKeeperListener;
import oasis.oasisserver.listeners.voting.VoteCastListener;
import oasis.oasisserver.listeners.voting.VoteCreateListener;
import oasis.oasisserver.listeners.warfare.HostilityDeclareListener;
import oasis.oasisserver.listeners.warfare.HostilityRevokeListener;
import oasis.oasisserver.types.Message;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Main class of OasisServer.
 * <p>
 *     Oasis uses a custom Minecraft client, and handles network IO via packets. (was supposed to)
 *     Now uses command preprocess events due to time constraints.
 *     In-game commands are not used. (even for basic messaging or teleportation)
 *     Vanilla clients can still join, but cannot use any of the features.
 *     (unless they create their own client compatible with this implementation)
 * </p>
 */
public final class OasisServer extends EconomyX {
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

    @Override
    public void onEnable() {
        super.onEnable();

        Bukkit.getLogger().info("Starting OasisServer bootstrap.");
        Bukkit.getLogger().info("This is an EconomyX product.");

        // Input
        registerListeners();

        // Output


        Bukkit.getLogger().info("OasisServer bootstrap complete!");
    }

    @Override
    public void onDisable() {
        super.onDisable();

        Bukkit.getLogger().info("OasisServer is shutting down.");
        Bukkit.getLogger().info("This is an EconomyX product.");
    }

    private void registerListeners() {
        new AssetDephysicalizeListener(this, getRawState());
        new AssetPhysicalizeListener(this, getRawState());

        new BankAccountCloseListener(this, getRawState());
        new BankAccountOpenListener(this, getRawState());
        new BankDepositListener(this, getRawState());
        new BankWithdrawListener(this, getRawState());

        new CardActivateListener(this, getRawState());
        new CardIssueListener(this, getRawState());
        new CardRevokeListener(this, getRawState());
        new CardTerminalCreateListener(this, getRawState());

        new ContractCreateListener(this, getRawState());
        new ContractForgiveListener(this, getRawState());
        new OptionExerciseListener(this, getRawState());

        new CreateCompanyListener(this, getRawState());
        new CreateInstitutionListener(this, getRawState());
        new CreateNationListener(this, getRawState());
        new CreateOrganizationListener(this, getRawState());

        new GuaranteeIssueListener(this, getRawState());
        new GuaranteeRevokeListener(this, getRawState());

        new MarketCancelOrderListener(this, getRawState());
        new MarketPlaceOrderListener(this, getRawState());

        new PayListener(this, getRawState());

        new PropertyAbandonListener(this, getRawState());
        new PropertyAuthorizeProtectionListener(this, getRawState());
        new PropertyClaimListener(this, getRawState());
        new PropertySetProtectorListener(this, getRawState());

        new DirectMessageListener(this, getRawState());

        new StateGetListener(this, getRawState());

        new VaultAuthorizeKeepingListener(this, getRawState());
        new VaultSetKeeperListener(this, getRawState());
        new VaultCreateListener(this, getRawState());

        new VoteCastListener(this, getRawState());
        new VoteCreateListener(this, getRawState());

        new HostilityDeclareListener(this, getRawState());
        new HostilityRevokeListener(this, getRawState());
    }
}
