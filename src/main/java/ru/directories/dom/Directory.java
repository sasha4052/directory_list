package ru.directories.dom;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Directory")
public class Directory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private  String path;
    private String date;
    private int dirs=0;
    private int files=0;
    private String size="0 kb";
    private String flist;

    public Directory(int id, String path, String date, int dirs, int files, String size, String flist) {
        this.id = id;
        this.path = path;
        this.date = date;
        this.dirs = dirs;
        this.files = files;
        this.size = size;
        this.flist = flist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDirs() {
        return dirs;
    }

    public void setDirs(int dirs) {
        this.dirs = dirs;
    }

    public int getFiles() {
        return files;
    }

    public void setFiles(int files) {
        this.files = files;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String Size) {
        this.size = Size;
    }

    public String getFlist() {
        return flist;
    }

    public void setFlist(String flist) {
        this.flist = flist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Directory dir = (Directory) o;

        return id == dir.id;

    }

    public Directory() {
    }

    public Directory(String path) {
            this.path = path;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.date = LocalDateTime.now().format(formatter);
            DecimalFormat df = new DecimalFormat("#.#");
            this.size = df.format(getDirSize(new File(path)) / 1024) + " kb";
            getGetCountFiles(new File(path));
            this.flist = getFilesFromDirectoryString();
    }

    private static double  getDirSize(File dir) {
        long size = 0;
        if (dir.isFile()) {
            size = dir.length();
        } else {
            File[] subFiles = dir.listFiles();
            for (File file : subFiles) {
                if (file.isFile()) {
                    size += file.length();
                } else {
                    size += getDirSize(file);
                }
            }
        }
        return size;
    }


   private  void  getGetCountFiles(File dir) {
        if (dir.isFile()) {
            this.files++;
        } else {
            this.dirs++;
            File[] subFiles = dir.listFiles();
            for (File file : subFiles) {
                if (file.isFile()) {
                    this.files++;
                } else {
                    getGetCountFiles(file);
                }
            }
        }
    }

     List<String> getFilesFromDirectory(String path){
        File file = new File(path);
        LinkedList<String> files = new LinkedList<String>();
        DecimalFormat df = new DecimalFormat("#.#");
        for(File f: file.listFiles()){
            if(f.isDirectory()) files.addFirst(f.getName()+" "+ (f.isDirectory() ? "<DIR>": (f.length()+"b")));
            else files.addLast(f.getName()+" "+ (f.isDirectory() ? "<DIR>": (df.format(f.length()/1024)+"kb")));
        };
        return  files;
    }
    String getFilesFromDirectoryString(){
         String res = "";
        for(String el: getFilesFromDirectory()){
            res = res+el+";";
        }
        return res;
    }

     private List<String> getFilesFromDirectory(){
        File file = new File(this.path);
        LinkedList<String> files = new LinkedList<String>() ;
         DecimalFormat df = new DecimalFormat("#.#");
        for(File f: file.listFiles()){
            if(f.isDirectory()) files.addFirst(f.getName()+" "+ (f.isDirectory() ? "<DIR>": (f.length()+"b")));
            else files.addLast(f.getName()+" "+ (f.isDirectory() ? "<DIR>": (df.format(f.length()/1024)+"kb")));
        };
        files.sort((f1, f2) ->{
            if((!f1.split(" ")[1].equals("<DIR>") && !f2.split(" ")[1].equals("<DIR>")) ||(f1.split(" ").equals("<DIR>") && f2.split(" ").equals("<DIR>"))) {
                for (int i = 0; i < f1.length(); i++) {
                    if (f1.toLowerCase().charAt(i) < f2.toLowerCase().charAt(i)) {
                        return 1;
                    } else {
                        if (f1.toLowerCase().charAt(i) < f2.toLowerCase().charAt(i)) {
                            return -1;
                        }
                    }
                }
                return 0;
            } else {
                return f1.split(" ")[1].equals("<DIR>") ? -1 : 1;
            }
        });
        return  files;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
