package com.example.devilsgod.binimoy.Model;

public class ImportNotesModel {
    private String noteName;
    private String noteUrl;

    public ImportNotesModel() {
    }

    public ImportNotesModel(String noteName, String noteUrl) {
        this.noteName = noteName;
        this.noteUrl = noteUrl;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteUrl() {
        return noteUrl;
    }

    public void setNoteUrl(String noteUrl) {
        this.noteUrl = noteUrl;
    }
}
