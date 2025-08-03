package net.wispwood.bounty.maprenderer;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class WantedPosterRenderer extends MapRenderer {
    private final OfflinePlayer target;
    private final double pooledBounty;
    private boolean rendered = false;

    public WantedPosterRenderer(OfflinePlayer target, double pooledBounty) {
        super(true);
        this.target = target;
        this.pooledBounty = pooledBounty;
    }

    @Override
    public void render(MapView view, MapCanvas canvas, Player player) {
        if (rendered) return;
        rendered = true;

        BufferedImage parchment = loadParchment();
        if (parchment != null) {
            BufferedImage scaledParchment = scaleImage(parchment, 128, 128);
            canvas.drawImage(0, 0, scaledParchment);
        } else {
            for (int x = 0; x < 128; x++) {
                for (int y = 0; y < 128; y++) {
                    canvas.setPixel(x, y, MapPalette.BROWN);
                }
            }
        }

        BufferedImage wantedText = createTextImage("WANTED",
                new Font("Serif", Font.BOLD, 20),
                Color.RED);
        canvas.drawImage(centerX(wantedText), 8, wantedText);

        BufferedImage head = getPlayerHead(target.getName());
        int headBottomY = 34;
        if (head != null) {
            BufferedImage scaledHead = scaleImage(head, 56, 56);
            int headX = (128 - scaledHead.getWidth()) / 2;
            headBottomY = 34 + scaledHead.getHeight();
            canvas.drawImage(headX, 34, scaledHead);
        }

        String name = target.getName() != null ? target.getName() : "Unknown";
        BufferedImage nameImage = createTextImage(name,
                new Font("Serif", Font.BOLD, adjustFontSize(name, 16)),
                Color.BLACK);
        canvas.drawImage(centerX(nameImage), headBottomY + 2, nameImage);

        String bountyText = "$" + String.format("%,.0f", pooledBounty);
        BufferedImage bountyImage = createTextImage(bountyText,
                new Font("Serif", Font.BOLD, 18),
                Color.YELLOW);
        canvas.drawImage(centerX(bountyImage), 105, bountyImage);
    }

    private BufferedImage scaleImage(BufferedImage original, int width, int height) {
        Image tmp = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private BufferedImage createTextImage(String text, Font font, Color color) {
        BufferedImage img = new BufferedImage(128, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);

        g.setColor(new Color(0, 0, 0, 180));
        g.drawString(text, 6, 16);
        g.setColor(color);
        g.drawString(text, 5, 15);

        g.dispose();
        return cropImage(img);
    }

    private int adjustFontSize(String text, int baseSize) {
        if (text.length() <= 8) return baseSize;
        if (text.length() <= 12) return baseSize - 2;
        return baseSize - 4;
    }

    private BufferedImage loadParchment() {
        try (InputStream is = getClass().getResourceAsStream("/wanted_parchment.png")) {
            if (is != null) {
                return ImageIO.read(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage getPlayerHead(String playerName) {
        try {
            URL url = new URL("https://minotar.net/helm/" + playerName + "/48");
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int centerX(BufferedImage img) {
        return (128 - img.getWidth()) / 2;
    }
    private BufferedImage cropImage(BufferedImage img) {
        int minX = 128, minY = 20, maxX = 0, maxY = 0;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if ((img.getRGB(x, y) >> 24) != 0x00) {
                    if (x < minX) minX = x;
                    if (y < minY) minY = y;
                    if (x > maxX) maxX = x;
                    if (y > maxY) maxY = y;
                }
            }
        }
        if (maxX < minX || maxY < minY) return img;
        return img.getSubimage(minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }
}
