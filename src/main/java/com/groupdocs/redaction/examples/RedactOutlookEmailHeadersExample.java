package com.groupdocs.redaction.examples;

import com.groupdocs.redaction.Redactor;
import com.groupdocs.redaction.redactions.ExactPhraseRedaction;
import com.groupdocs.redaction.options.LoadOptions;
import com.groupdocs.redaction.redactions.RegexRedaction;
import com.groupdocs.redaction.options.SaveOptions;
import com.groupdocs.redaction.licensing.License;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Demonstrates redaction of personal information from Outlook MSG and EML files,
 * including headers and attachments, using GroupDocs Redaction for Java.
 * <p>
 * This example:
 * <ul>
 *   <li>Loads MSG/EML files from input folder</li>
 *   <li>Applies exact-phrase and regex-based redactions to headers and body</li>
 *   <li>Redacts attachment metadata (e.g., filenames, sender info)</li>
 *   <li>Saves redacted output to output folder</li>
 * </ul>
 * <p>
 * Note: Real input files must be placed in {@code src/main/resources/input/} before running.
 * See README.md for setup instructions.
 */
public class RedactOutlookEmailHeadersExample {

    /**
     * Applies redactions to Outlook MSG/EML files.
     * <p>
     * Uses exact phrase and regex redactions to remove PII from headers and attachments.
     */
    public static void run() throws IOException {
        // -----------------------------------------------------------------
        // Step 1. Set up license (evaluation fallback if no license file exists).
        // -----------------------------------------------------------------
        License license = new License();
        String licensePath = "src/main/resources/groupdocs.redaction.lic";
        File licenseFile = new File(licensePath);
        if (licenseFile.exists()) {
            license.setLicense(licensePath);
        } else {
            System.out.println("No license file found. Running in evaluation mode.");
            System.out.println("Get a free 30-day temporary license: https://purchase.groupdocs.com/temporary-license");
        }

        // -----------------------------------------------------------------
        // Step 2. Prepare input/output directories.
        // -----------------------------------------------------------------
        Path inputDir = Paths.get("src/main/resources/input");
        Path outputDir = Paths.get("src/main/resources/output");
        Files.createDirectories(outputDir);

        // -----------------------------------------------------------------
        // Step 3. Process each MSG/EML file in the input directory.
        // -----------------------------------------------------------------
        Files.list(inputDir)
                .filter(path -> path.toString().toLowerCase().endsWith(".msg") || path.toString().toLowerCase().endsWith(".eml"))
                .forEach(path -> {
                    try {
                        redactEmailFile(path.toString(), outputDir.resolve(path.getFileName()).toString());
                    } catch (Exception e) {
                        System.err.println("Error processing " + path + ": " + e.getMessage());
                    }
                });

        System.out.println("Redaction completed. Output files saved to: " + outputDir.toAbsolutePath());
    }

    /**
     * Redacts personal information from a single email file (MSG/EML).
     *
     * @param inputPath  Path to the source email file
     * @param outputPath Path to save the redacted file
     */
    private static void redactEmailFile(String inputPath, String outputPath) throws IOException {
        // Load the email file
        Redactor redactor = new Redactor(inputPath);
        try {
            // -----------------------------------------------------------------
            // Step 4. Redact exact personal names from headers and body.
            // -----------------------------------------------------------------
            redactor.apply(new ExactPhraseRedaction("John Doe", new com.groupdocs.redaction.redactions.ReplacementOptions("[REDACTED_NAME]")));
            redactor.apply(new ExactPhraseRedaction("Jane Smith", new com.groupdocs.redaction.redactions.ReplacementOptions("[REDACTED_NAME]")));

            // -----------------------------------------------------------------
            // Step 5. Redact email addresses using regex (generic pattern).
            // -----------------------------------------------------------------
            redactor.apply(new RegexRedaction("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}",
                    new com.groupdocs.redaction.redactions.ReplacementOptions("[REDACTED_EMAIL]")));

            // -----------------------------------------------------------------
            // Step 6. Redact phone numbers (US format example).
            // -----------------------------------------------------------------
            redactor.apply(new RegexRedaction("\\b\\d{3}[-.]?\\d{3}[-.]?\\d{4}\\b",
                    new com.groupdocs.redaction.redactions.ReplacementOptions("[REDACTED_PHONE]")));

            // -----------------------------------------------------------------
            // Step 7. Redact attachment metadata (e.g., filenames with PII) — applied to document metadata.
            // -----------------------------------------------------------------
            // Note: GroupDocs Redaction for Java redacts content and metadata, but attachment-level
            // redaction is limited to what's exposed in the main document text/metadata.
            // For advanced attachment handling, consider post-processing or using GroupDocs.Viewer.
            redactor.apply(new RegexRedaction("\\b[A-Z][a-z]+_\\d{4}_[A-Z]+\\.pdf\\b",
                    new com.groupdocs.redaction.redactions.ReplacementOptions("[REDACTED_FILENAME]")));

            // -----------------------------------------------------------------
            // Step 8. Save the redacted email file.
            // -----------------------------------------------------------------
            redactor.save(outputPath);
            System.out.println("Redacted: " + inputPath + " → " + outputPath);
        } finally {
            redactor.close();
        }
    }

    public static void main(String[] args) {
        try {
            run();
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
