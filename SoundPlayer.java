import javax.sound.sampled.*;
import java.io.File;

class SoundPlayer {

    public static void play(String filePath) {
        try {
            File audioFile = new File(filePath);

            if (!audioFile.exists()) {
                System.out.println("Fichier introuvable : " + audioFile.getAbsolutePath());
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Volume normal
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(0.0f);
            }

            clip.start();

            // Attendre la fin du son
            Thread.sleep(clip.getMicrosecondLength() / 1000);

            clip.close();
            audioStream.close();

        } catch (UnsupportedAudioFileException e) {
            System.out.println("Format non supporté (WAV PCM 16-bit requis)");
        } catch (LineUnavailableException e) {
            System.out.println("Ligne audio indisponible");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        // Appel de la classe SoundPlayer
        SoundPlayer.play("src/Dixie-horn-sound.wav");

        System.out.println("Programme terminé.");
    }
}



    