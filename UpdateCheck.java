package com.mycompany.dbprogrammet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class UpdateCheck {

    private final Epost epost = new Epost();
    
    private URL url;
    private URL url2;
    private final String FILE = "https://www.emilohlund.se/programmet/Programmet.jar";

    FileChannel fileChannel;
    FileChannel fileChannel2;
    FileOutputStream fileOutputStream;
    FileOutputStream fileOutputStream2;
    ReadableByteChannel readableByteChannel;
    ReadableByteChannel readableByteChannel2;

    public void check_update() throws MalformedURLException, IOException {

        this.url = new URL(FILE);
        this.url2 = new URL(FILE);

        File temp = new File(System.getProperty("user.dir") + "/temp");

        if (!temp.exists()) {
            temp.mkdirs();
        }

        File uppdatering = new File(System.getProperty("user.dir") + "/temp/Programmet.jar");
        File nuvarande = new File(System.getProperty("user.dir") + "/Programmet.jar");

        if(nuvarande.exists()) {
            readableByteChannel = Channels.newChannel(url.openStream());
            fileOutputStream = new FileOutputStream(uppdatering);
            fileChannel = fileOutputStream.getChannel();

            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

            fileOutputStream.close();
            fileChannel.close();

            readableByteChannel2 = Channels.newChannel(url2.openStream());
            fileOutputStream2 = new FileOutputStream(nuvarande);
            fileChannel2 = fileOutputStream2.getChannel();
            fileOutputStream2.getChannel()
            .transferFrom(readableByteChannel2, 0, Long.MAX_VALUE);
            fileOutputStream2.close();
            fileChannel2.close();
            uppdatering.delete();
            temp.delete();

            readableByteChannel.close();
        }
        
    }

}
