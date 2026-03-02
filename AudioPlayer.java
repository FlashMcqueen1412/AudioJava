import javax.sound.sampled.*;
import java.io.File;

public class AudioPlayer {

    public static void main(String[] args) {

        // Nom du fichier audio (dans le même dossier)
        String fileName = "src/Dixie-horn-sound.wav";

        try {
            File audioFile = new File(fileName);

            if (!audioFile.exists()) {
                System.out.println("Fichier introuvable !");
                System.out.println("Chemin : " + audioFile.getAbsolutePath());
                return;
            }

            System.out.println("Lecture du fichier : " + audioFile.getAbsolutePath());

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Volume normal (0 dB)
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(0.0f);
            }

            clip.start();

            // Attendre que le son se termine
            Thread.sleep(clip.getMicrosecondLength() / 1000);

            clip.close();
            audioStream.close();

            System.out.println("Lecture terminée.");

        } catch (UnsupportedAudioFileException e) {
            System.out.println("Format non supporté (utilise WAV PCM 16-bit).");
        } catch (LineUnavailableException e) {
            System.out.println("Ligne audio indisponible.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
