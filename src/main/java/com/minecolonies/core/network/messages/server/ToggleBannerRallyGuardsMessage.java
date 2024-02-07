package com.minecolonies.core.network.messages.server;

import com.ldtteam.common.network.AbstractServerPlayMessage;
import com.ldtteam.common.network.PlayMessageType;
import com.minecolonies.api.util.InventoryUtils;
import com.minecolonies.api.util.ItemStackUtils;
import com.minecolonies.api.util.MessageUtils;
import com.minecolonies.api.util.constant.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

import static com.minecolonies.api.util.constant.TranslationConstants.COM_MINECOLONIES_BANNER_RALLY_GUARDS_GUI_ERROR;
import static com.minecolonies.core.items.ItemBannerRallyGuards.toggleBanner;

/**
 * Toggles a rallying banner
 */
public class ToggleBannerRallyGuardsMessage extends AbstractServerPlayMessage
{
    public static final PlayMessageType<?> TYPE = PlayMessageType.forServer(Constants.MOD_ID, "toggle_banner_rally_guards", ToggleBannerRallyGuardsMessage::new);

    /**
     * The banner to be toggled.
     */
    private final ItemStack banner;

    /**
     * Toggle the banner
     *
     * @param banner The banner to be toggled.
     */
    public ToggleBannerRallyGuardsMessage(final ItemStack banner)
    {
        super(TYPE);
        this.banner = banner;
    }

    protected ToggleBannerRallyGuardsMessage(final FriendlyByteBuf buf, final PlayMessageType<?> type)
    {
        super(buf, type);
        banner = buf.readItem();
    }

    @Override
    protected void toBytes(@NotNull final FriendlyByteBuf buf)
    {
        buf.writeItem(banner);
    }

    @Override
    protected void onExecute(final PlayPayloadContext ctxIn, final ServerPlayer player)
    {
        final int slot = InventoryUtils.findFirstSlotInItemHandlerWith(new InvWrapper(player.getInventory()),
          (itemStack -> ItemStackUtils.compareItemStacksIgnoreStackSize(itemStack, banner)));

        if (slot == -1)
        {
            MessageUtils.format(COM_MINECOLONIES_BANNER_RALLY_GUARDS_GUI_ERROR).sendTo(player);
            return;
        }

        toggleBanner(player.getInventory().getItem(slot), player);
    }
}
