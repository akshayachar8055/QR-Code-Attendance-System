package com.attendance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

 
public class FileSearcher {
        /**
        *
        * @param match - partial for full file name to search sd for
        * @return List of files that contain the match were looking for
        */
        public List<File> searchFullSDCard(String match)
        {
                List<File> matches = new ArrayList<File>();
                File f = new File("/sdcard/sharing/");
                findMatch(matches,f,match);   
                return matches;
        }  
        /**  
        *
        * @param match partial for full file name to search sd for
        * @param dir Starting directory
        * <pre>        Note: Starting directory must be on the SD Card!</pre>
        * @return List of files that contain the match were looking for
        */
        public static List<File>searchFromDirectory(String match,File dir)
        {
                List<File> matches = new ArrayList<File>();
                findMatch(matches,dir,match);
                return matches;
        }
        private static void findMatch(List<File>matches,File curDir,String match)
        {
                File[]files = curDir.listFiles();
                if(files==null)
                        return;
                for(File f: files)
                {
                        if(f.isDirectory())
                                findMatch(matches,f,match);
                        else
                        {
                        		String name = f.getName();
                        		String nameNext = stripExtension(name);
                        		
                        		String[] temp = name.split("\\.");
                        		String temp1[] = match.split("\\.");
                        		
                                if(temp[0].toLowerCase().contains(temp1[0].toLowerCase())){
                        		
                                        matches.add(f);
                                  }
                        }     
                }
        }      
 
        static String stripExtension (String str) {
            // Handle null case specially.

            if (str == null) return null;

            // Get position of last '.'.

            int pos = str.lastIndexOf(".");

            // If there wasn't any '.' just return the string as is.

            if (pos == -1) return str;

            // Otherwise return the string, up to the dot.

            return str.substring(0, pos);
        }
}       