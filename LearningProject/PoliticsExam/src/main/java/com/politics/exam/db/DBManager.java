package com.politics.exam.db;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.politics.exam.PoliticsApplication.sContext;

/**
 * Created by malijie on 2017/6/1.
 */

public class DBManager {
    public static void copyDB2Phone(CopyDBListener listener){
        String dbFileName = DBConstants.DB_PATH + DBConstants.DB_NAME;
        File dbFile = new File(dbFileName);
        InputStream is = null;
        FileOutputStream os = null;
//        if(!dbFile.exists()){
            dbFile.getParentFile().mkdirs();
            try{
                os = new FileOutputStream(dbFileName);
                is = sContext.getResources().getAssets().open(DBConstants.DB_NAME);
                byte[] buffer = new byte[1024];
                int count = 0;
                while((count=is.read(buffer))>0){
                    os.write(buffer, 0, count);
                    os.flush();
                }

            }catch(FileNotFoundException e){
                e.printStackTrace();
                listener.onFailed();
            } catch (IOException e) {
                e.printStackTrace();
                listener.onFailed();
            }finally{
                try {
                    is.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//        }
        listener.onSuccess();
    }


    public interface CopyDBListener{
        void onSuccess();
        void onFailed();
    }
}
