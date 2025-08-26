# DocsToPdf

Mass applying and need to reduce the number of clicks per application? Worry no more:

**DocsToPdf** is a utility to generate customized PDF cover letters from a DOCX template.  
It replaces placeholders in your cover letter (e.g., company name, job position) and outputs a ready-to-use PDF in the same directory.

---

## 🚀 How to Run

1. Extract the ZIP file to a folder of your choice.

2. Open `config.yaml` in a text editor.

3. Update the `docs.location` field with the **absolute path** of your DOCX cover letter template.  
   Example:
   ```yaml
   docs:
     location: "C:/Users/YourName/Documents/cover_letter.docx"
4. It is important that the DOCX file contain valid placeholder formats so that they can be replaced i.e:
  I am applying for the {{jobPosition}} position at your company {{companyName}}

5. Run executor.bat and enter the required parameters.

6. Your PDF is available in the same directory as your DOCX file.

## Requirements:
Requires Java 17+ installed and PATH configured.