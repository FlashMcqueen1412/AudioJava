import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    /**
     * Plays an audio file (.wav, .aiff, .au) using Java Sound API.
     * @param filePath Path to the audio file
     */
    public static void playSound(String filePath) {
        File audioFile = new File(filePath);

        // Validate file existence
        if (!audioFile.exists() || !audioFile.isFile()) {
            System.err.println("Error: File not found -> " + filePath);
            return;
        }

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
            // Get audio format and create a clip
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // Ensure system supports the audio format
            if (!AudioSystem.isLineSupported(info)) {
                System.err.println("Error: Audio format not supported.");
                return;
            }

            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);

            // Add a listener to close the clip when done
            audioClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    audioClip.close();
                }
            });

            // Start playback
            audioClip.start();
            System.out.println("Playing: " + filePath);

            // Keep program alive until audio finishes
            while (audioClip.isRunning()) {
                Thread.sleep(100);
            }

        } catch (UnsupportedAudioFileException e) {
            System.err.println("Error: Unsupported audio file format.");
        } catch (LineUnavailableException e) {
            System.err.println("Error: Audio line unavailable.");
        } catch (IOException e) {
            System.err.println("Error: Could not read the audio file.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        // Example: Replace with your audio file path
        String filePath = "src/Dixie-horn-sound.wav";
        playSound(filePath);
    }
}