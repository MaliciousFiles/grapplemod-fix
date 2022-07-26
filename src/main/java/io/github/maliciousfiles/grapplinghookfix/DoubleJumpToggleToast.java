package io.github.maliciousfiles.grapplinghookfix;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yyon.grapplinghook.common.CommonSetup;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class DoubleJumpToggleToast implements IToast {

    private boolean enabled;
    private long firstDrawTime;
    private boolean newDisplay;

    public DoubleJumpToggleToast(boolean enabled) {
        this.enabled = enabled;
    }

    public IToast.Visibility func_230444_a_(MatrixStack matrixStack, ToastGui gui, long time) {
        if (this.newDisplay) {
            this.firstDrawTime = time;
            this.newDisplay = false;
        }

        gui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);

        ITextComponent title = new TranslationTextComponent("toast.toggle_double_jump.title");
        ITextComponent description = new TranslationTextComponent("toast.toggle_double_jump.description."+(enabled ? "enabled" : "disabled"));

        gui.blit(matrixStack, 0, 0, 0, 0, this.func_230445_a_(), this.func_238540_d_());

        gui.getMinecraft().fontRenderer.drawText(matrixStack, title, 30.0F, 7.0F, -256);
        gui.getMinecraft().fontRenderer.drawText(matrixStack, description, 30.0F, 18.0F, -1);

        ItemStack stack = new ItemStack(Items.DIAMOND_BOOTS);
        stack.addEnchantment(CommonSetup.doubleJumpEnchantment, 1);

        gui.getMinecraft().getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(stack, 8, 8);
        return time - this.firstDrawTime < 1500L ? Visibility.SHOW : Visibility.HIDE;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.newDisplay = true;
    }

    public static void addOrUpdate(ToastGui gui, boolean enabled) {
        DoubleJumpToggleToast toast = gui.getToast(DoubleJumpToggleToast.class, NO_TOKEN);

        if (toast == null) {
            gui.add(new DoubleJumpToggleToast(enabled));
        } else {
            toast.setEnabled(enabled);
        }
    }
}
