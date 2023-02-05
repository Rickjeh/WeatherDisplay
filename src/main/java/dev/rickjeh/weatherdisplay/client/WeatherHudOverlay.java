package dev.rickjeh.weatherdisplay.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static dev.rickjeh.weatherdisplay.client.WeatherdisplayClient.mc;
import static dev.rickjeh.weatherdisplay.client.WeatherdisplayClient.state;
import static dev.rickjeh.weatherdisplay.client.WeatherdisplayClient.notDisabled;
import static dev.rickjeh.weatherdisplay.client.WeatherdisplayClient.clearDisabled;
public class WeatherHudOverlay implements HudRenderCallback {
    private static final String MOD_ID = "weatherdisplay";
    private static final Identifier CLEAR_WEATHER_DAY = new Identifier(WeatherHudOverlay.MOD_ID, "weathericons/sun.png");
    private static final Identifier CLEAR_WEATHER_NIGHT = new Identifier(WeatherHudOverlay.MOD_ID, "weathericons/moon.png");
    private static final Identifier RAINY_WEATHER = new Identifier(WeatherHudOverlay.MOD_ID, "weathericons/rain.png");
    private static final Identifier THUNDERING_WEATHER = new Identifier(WeatherHudOverlay.MOD_ID, "weathericons/thunder.png");
    private static final Identifier DISPLAY_NONE = new Identifier(WeatherHudOverlay.MOD_ID, "weathericons/none.png");

    //source: https://www.flaticon.com/packs/weather-384

    @Override
    public void onHudRender(MatrixStack matrixStack, float v) {
        assert(mc != null);

        int x = 0;
        int y = 0;

        int width = mc.getWindow().getScaledWidth();
        int height = mc.getWindow().getScaledHeight();

        x = width / 2;
        y = height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        if(mc.world.getDimension().bedWorks() && notDisabled){
            if(mc.world.isThundering()){
                RenderSystem.setShaderTexture(0, THUNDERING_WEATHER);
            } else if (mc.world.isRaining()) {
                RenderSystem.setShaderTexture(0, RAINY_WEATHER);
            } else{
                if(!clearDisabled){
                    RenderSystem.setShaderTexture(0, CLEAR_WEATHER_DAY);
                }else{
                    RenderSystem.setShaderTexture(0, DISPLAY_NONE);
                }
            }

            switch (state) {
                case 0 ->  //default
                        DrawableHelper.drawTexture(matrixStack, x - -94, y - 18, 0, 0, 15, 15, 15, 15);
                case 1 ->  //top right
                        DrawableHelper.drawTexture(matrixStack, width - 20, height - height + 5, 0, 0, 15, 15, 15, 15);
                case 2 ->  //top left
                        DrawableHelper.drawTexture(matrixStack, width - width + 5, height - height + 5, 0, 0, 15, 15, 15, 15);
                case 3 ->  //bottom left
                        DrawableHelper.drawTexture(matrixStack, width - width + 5, y - 18, 0, 0, 15, 15, 15, 15);
                case 4 ->  //bottom right
                        DrawableHelper.drawTexture(matrixStack, width - 20, y - 18, 0, 0, 15, 15, 15, 15);
            }

        }

    }

}


