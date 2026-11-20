# GroupDocs Redaction Java – Redact Outlook EML Messages

## Overview
This showcase project demonstrates how to use **GroupDocs Redaction** (Java) to find personal data in an Outlook EML message and replace the matches. It is a minimal, runnable Maven project that helps developers redact names, email addresses, phone numbers, and attachment file-name references that appear in the message body.

## Prerequisites
- **Java Development Kit (JDK) 8** or higher
- **Apache Maven** 3.5+ installed and added to your `PATH`
- An **Internet connection** for Maven to download dependencies

## License
GroupDocs Redaction requires a license file to work without evaluation limitations (trial badge, one opened document per process, and so on).

1. **Obtain a temporary license** – Visit https://purchase.groupdocs.com/temporary-license/ to get a free 30-day temporary license.
2. **Place the license file** – Save the downloaded `GroupDocs.Redaction.Java.lic` file in the **project root directory** (the same level where `pom.xml` resides).
3. **Without a license** – The library runs in evaluation mode, which adds a trial badge and opens only one document per process.

## Project Structure
```
redact-outlook-msg-eml-headers-attachments-groupdocs-redaction-java/
│   pom.xml
│   README.md
│   GroupDocs.Redaction.Java.lic   (optional – place your license here)
│
├───src
│   └───main
│       └───java
│           └───com
│               └───groupdocs
│                   └───redaction
│                       └───examples
│                           └───RedactOutlookEmailHeadersExample.java
│
├───resources
│   ├───input
│   │   └───sample.eml             (sample Outlook EML – replace with your own)
│   └───output                     (the redacted message will be saved here)
```

## Setup & Run
1. **Clone or copy the project**
   ```bash
   git clone https://github.com/your-repo/redact-outlook-msg-eml-headers-attachments-groupdocs-redaction-java.git
   cd redact-outlook-msg-eml-headers-attachments-groupdocs-redaction-java
   ```
2. **Add your license file** (optional but recommended) – copy `GroupDocs.Redaction.Java.lic` into the project root.
3. **Place an EML file** you want to redact inside `resources/input/`. The project already contains `sample.eml`, which you can replace. If you have an Outlook MSG, save or export it as EML first — MSG is not supported by GroupDocs Redaction.
4. **Build the project**
   ```bash
   mvn clean compile
   ```
5. **Run the example**
   ```bash
   mvn exec:java
   ```

## What Happens When You Run It?
- The program loads the license (if present).
- It checks `resources/input/sample.eml`. If the file is missing, execution stops with an error message.
- An output directory `resources/output/` is created if it does not already exist.
- `RegexRedaction` replaces `John Doe`, `Jane Smith`, email addresses, US-style phone numbers, and `JohnDoe_2024_CONTRACT.pdf` with `[REDACTED]`. All terms are kept in one expression so evaluation mode still applies them.
- Rasterization is turned off, so the file is saved as `resources/output/sample_redacted.eml`.
- Console output informs you about the progress and the status of each redaction.

## Customization
- **Redact other terms** – change `SEARCH`. Separate alternatives with `|`, and keep `(?i)` when the match should ignore case.
- **Hide a match with a color box** – pass `new ReplacementOptions(java.awt.Color.BLACK)` instead of a replacement string.
- **Redact another message** – replace `resources/input/sample.eml`, or change `INPUT_FILE` in the source.

## Notes
- The included `sample.eml` is a short Contoso/Fabrikam contract thread. It contains the names, addresses, phone number, and attachment file-name reference used by the sample pattern.
- GroupDocs Redaction redacts text from the email body. It does not rewrite individual MIME header fields in place the way a mail API would, and it does not open native Outlook `.msg` files.
- Evaluation mode applies only one redaction object. Keep additional terms inside the same regular expression.
- `Redactor.save()` with no arguments writes a rasterized PDF. This example disables rasterization so the output stays an EML-like message.
- Ensure the `resources/input/` and `resources/output/` directories are at the same level as `pom.xml` as shown in the structure above.

---

*This is a showcase project for GroupDocs Redaction. It is intended for demonstration and educational purposes.*
