package oasis.oasisserver;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import oasis.economyx.EconomyX;

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
    @Override
    public void onEnable() {
        super.onEnable();

        // ProtocolLib
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        /**
         * Alright I'm going to bed now
         */
        final String HELP = "ME";

        // as#uuid#(whatever)

        // create#company#symbol#name#capital
        // create#organization#name#currency
        // create#nation#name#capital

        // bank#deposit#bankuuid#accountuuid#amount
        // bank#withdraw#bankuuid#accountuuid#amount

        // card#issue
        // card#activate
        // card#revoke
        // card#terminal#create

        // contract#create
        // contract#forgive

        // guarantee#issue
        // guarantee#revoke

        // pay#recipient#amount

        // property#claim
        // property#abandon
        // property#setprotector (called by holder)
        // property#authorizeprotection (called by protector)

        // market#list#listing
        // market#delist#listing

        // order#create#order
        // order#cancel#order

        // vault#create
        // vault#setkeeper (called by holder)
        // vault#authroizekeeping (called by keeper)

        // vote#create
        // vote#cast#votes

        // hostility#declare
        // hostility#revoke
    }
}
