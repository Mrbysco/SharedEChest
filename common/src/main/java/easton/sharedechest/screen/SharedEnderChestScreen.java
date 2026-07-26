package easton.sharedechest.screen;

import easton.sharedechest.Constants;
import easton.sharedechest.platform.Services;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.ArrayList;
import java.util.List;

public class SharedEnderChestScreen extends AbstractContainerScreen<AbstractContainerMenu> {
	//A path to the gui texture. In this example we use the texture from the dispenser
	private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");

	private static final Identifier PERSONAL_SPRITE = Constants.modLoc("shared_buttons/personal");
	private static final Identifier PERSONAL_SELECTED_SPRITE = Constants.modLoc("shared_buttons/personal_selected");
	private static final Identifier SHARED_SPRITE = Constants.modLoc("shared_buttons/shared");
	private static final Identifier SHARED_SELECTED_SPRITE = Constants.modLoc("shared_buttons/shared_selected");

	private final List<BaseButtonWidget> buttons = new ArrayList<>();
	private BaseButtonWidget personal_button;
	private BaseButtonWidget shared_button;

	public SharedEnderChestScreen(AbstractContainerMenu handler, Inventory inventory, Component title) {
		super(handler, inventory, title);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
		super.extractRenderState(graphics, mouseX, mouseY, partialTick);
		this.extractTooltip(graphics, mouseX, mouseY);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
		super.extractBackground(graphics, mouseX, mouseY, partialTick);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j, 0, 0, this.imageWidth, 3 * 18 + 17, 256, 256);
		graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j + 3 * 18 + 17, 0, 126, this.imageWidth, 96, 256, 256);
	}

	private <T extends BaseButtonWidget> void addButton(T button) {
		this.addRenderableWidget(button);
		this.buttons.add(button);
	}

	@Override
	protected void init() {
		super.init();
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.personal_button = new BaseButtonWidget(i + 134, j + 4, false, Component.literal("Personal Ender Chest"));
		this.personal_button.pressed = true;
		this.shared_button = new BaseButtonWidget(i + 152, j + 4, true, Component.literal("Shared Ender Chest"));
		this.addButton(this.personal_button);
		this.addButton(this.shared_button);
	}

	private class BaseButtonWidget extends AbstractButton {

		private boolean pressed = false;
		private final boolean shared;

		protected BaseButtonWidget(int i, int j, boolean shared, Component tooltip) {
			super(i, j, 16, 12, Component.empty());
			this.shared = shared;
			setTooltip(Tooltip.create(tooltip));
		}

		@Override
		public void onPress(InputWithModifiers inputWithModifiers) {
			for (BaseButtonWidget button : SharedEnderChestScreen.this.buttons)
				button.pressed = false;
			this.pressed = true;

			Services.PLATFORM.onSharedButtonPress(shared);
		}

		@Override
		protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
			Identifier sprite;
			if (this.shared) {
				sprite = this.pressed ? SHARED_SELECTED_SPRITE : SHARED_SPRITE;
			} else {
				sprite = this.pressed ? PERSONAL_SELECTED_SPRITE : PERSONAL_SPRITE;
			}
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, this.getX(), this.getY(), this.width, this.height, ARGB.white(this.alpha));
		}

		@Override
		protected void updateWidgetNarration(NarrationElementOutput builder) {
			this.defaultButtonNarrationText(builder);
		}
	}

}
