package easton.sharedechest.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import easton.sharedechest.Constants;
import easton.sharedechest.platform.Services;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.ArrayList;
import java.util.List;

public class SharedEnderChestScreen extends AbstractContainerScreen<AbstractContainerMenu> {
	//A path to the gui texture. In this example we use the texture from the dispenser
	private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/generic_54.png");
	private static final ResourceLocation BUTTON_TEXTURE = Constants.modLoc("textures/gui/container/shared_buttons.png");
	private final List<BaseButtonWidget> buttons = new ArrayList<>();
	private BaseButtonWidget personal_button;
	private BaseButtonWidget shared_button;

	public SharedEnderChestScreen(AbstractContainerMenu handler, Inventory inventory, Component title) {
		super(handler, inventory, title);
	}

	@Override
	public void render(GuiGraphics matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices, mouseX, mouseY, delta);
		super.render(matrices, mouseX, mouseY, delta);
		this.renderTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		matrices.blit(TEXTURE, i, j, 0, 0, this.imageWidth, 3 * 18 + 17);
		matrices.blit(TEXTURE, i, j + 3 * 18 + 17, 0, 126, this.imageWidth, 96);
	}

	@Override
	protected void renderLabels(GuiGraphics matrices, int mouseX, int mouseY) {
		super.renderLabels(matrices, mouseX, mouseY);

		for (BaseButtonWidget baseButtonWidget : this.buttons) {
			if (baseButtonWidget.shouldRenderTooltip()) {
				baseButtonWidget.renderTooltip(matrices, mouseX - this.leftPos, mouseY - this.topPos);
				break;
			}
		}
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
		private final Component tooltip;

		protected BaseButtonWidget(int i, int j, boolean shared, Component tooltip) {
			super(i, j, 16, 12, Component.empty());
			this.shared = shared;
			this.tooltip = tooltip;
		}

		@Override
		public void onPress() {
			for (BaseButtonWidget button : SharedEnderChestScreen.this.buttons)
				button.pressed = false;
			this.pressed = true;

			Services.PLATFORM.onSharedButtonPress(shared);

		}

		@Override
		protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
			RenderSystem.setShader(GameRenderer::getPositionTexShader);

			int i = 0;
			if (this.pressed)
				i += 18;
			int j = 14;
			if (this.shared)
				j += 14;

			context.blit(SharedEnderChestScreen.BUTTON_TEXTURE, this.getX(), this.getY(), i, j, this.width, this.height);
		}

		public boolean shouldRenderTooltip() {
			return this.isHovered;
		}

		@Override
		protected void updateWidgetNarration(NarrationElementOutput builder) {
			this.defaultButtonNarrationText(builder);
		}

		public void renderTooltip(GuiGraphics matrices, int x, int y) {
			matrices.renderTooltip(SharedEnderChestScreen.this.font, this.tooltip, x, y);
		}
	}

}
