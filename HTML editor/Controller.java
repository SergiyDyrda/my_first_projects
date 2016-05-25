package com.javarush.test.level32.lesson15.big01;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

/**
 * Created by Serj on 11.02.2016.
 */
public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();
    }

    public void init() {
        createNewDocument();
    }

    public HTMLDocument getDocument() {
        return document;
    }

    public void resetDocument() {
        if (document != null) {
            document.removeUndoableEditListener(view.getUndoListener());
        }
        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        document.addUndoableEditListener(view.getUndoListener());
        view.update();
    }

    public void setPlainText(String text) {
        try {
            resetDocument();
            HTMLEditorKit editorKit = new HTMLEditorKit();
            StringReader reader = new StringReader(text);
            editorKit.read(reader, document, 0);
            reader.close();
        }
        catch (IOException | BadLocationException e) {
            ExceptionHandler.log(e);
        }
    }

    public String getPlainText() {
        StringWriter writer = null;
        try {
            HTMLEditorKit editorKit = new HTMLEditorKit();
            writer = new StringWriter();
            editorKit.write(writer, document, 0, document.getLength());
            writer.close();
        }
        catch (IOException | BadLocationException e) {
            ExceptionHandler.log(e);
        }
        return writer.toString();
    }


    public void exit() {
        System.exit(0);
    }

    public void createNewDocument() {
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        view.resetUndo();
        currentFile = null;
    }

    public void openDocument() {
        view.selectHtmlTab();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new HTMLFileFilter());

        if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            resetDocument();
            view.setTitle(currentFile.getName());

            FileReader fileReader = null;
            try {
                fileReader = new FileReader(currentFile);
                HTMLEditorKit editorKit = new HTMLEditorKit();
                editorKit.read(fileReader, document, 0);
                view.resetUndo();
            }
            catch (IOException | BadLocationException e) {
                ExceptionHandler.log(e);
            }
            finally {
                if (fileReader != null)
                    try {
                        fileReader.close();
                    }
                    catch (IOException e) {
                        ExceptionHandler.log(e);
                    }
            }
        }
    }

    public void saveDocument() {
        if (currentFile != null) {
            view.selectHtmlTab();

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(currentFile);
                HTMLEditorKit editorKit = new HTMLEditorKit();
                editorKit.write(fileWriter, document, 0, document.getLength());
            }
            catch (IOException | BadLocationException e) {
                ExceptionHandler.log(e);
            }
            finally {
                if (fileWriter != null)
                    try {
                        fileWriter.close();
                    }
                    catch (IOException e) {
                        ExceptionHandler.log(e);
                    }
            }
        } else saveDocumentAs();
    }

    public void saveDocumentAs() {
        view.selectHtmlTab();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new HTMLFileFilter());

        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();

            view.setTitle(currentFile.getName());

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(currentFile);
                HTMLEditorKit editorKit = new HTMLEditorKit();
                editorKit.write(fileWriter, document, 0, document.getLength());
            }
            catch (IOException | BadLocationException e) {
                ExceptionHandler.log(e);
            }
            finally {
                if (fileWriter != null)
                    try {
                        fileWriter.close();
                    }
                    catch (IOException e) {
                        ExceptionHandler.log(e);
                    }
            }
        }
    }
}
