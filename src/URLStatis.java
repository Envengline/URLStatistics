import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class URLStatis {
    static String filePath="C:\\Users\\kitty\\Desktop\\TestDATA\\";
    static Logger log = Logger.getLogger("writer");
    static int HASHLEN = 1000; //hash  modulus
    static List<String> list=new ArrayList<>(); //Save file number

    public static void main(String[] args) {
        log.setLevel(Level.INFO);

        log.info("---- begin cutting files ----");
        //the original filename is init.txt.
        readAndCut(filePath+"init.txt");
        log.info("---- cutting files over----");

        log.info("---- begin counting URLs ----");
        TreeMap<Integer,String> res = new TreeMap<>();
        res = readAndCount();
        log.info("---- counting URLs over ----");

        //the outcome is saved in out.txt
        Iterator iter= res.keySet().iterator();
        while (iter.hasNext()) {
            Object key= iter.next();
            System.out.println(key+" "+res.get(key));
            writeFile(filePath+"out.txt",key+" "+res.get(key));
        }
    }


    /**
     * Read the original file and cut it by hash
     *
     * @param f:filePath
     */
    public static  void readAndCut(String f) {
        File file = new File(f);
        BufferedReader reader = null;
        String fileNo = null;
        String fileName =null;
        try {
            reader = new BufferedReader(new FileReader(file), 250 * 1024 * 1024);
            String line = null;
            while ((line = reader.readLine()) != null) {
                //According to hash method,  break it up into 1000 small files
                fileNo =String.valueOf( Math.abs(hash(line.toCharArray())));
                if(!list.contains(fileNo)) list.add(fileNo);
                fileName =filePath+"out\\out"+fileNo+".txt";
                writeFile(fileName,line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("file is not found! ");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    static int hash(char[] word) {
        int index = 0;
        int i=0;
        while(i<word.length) {
            index += index * 31 + word[i];
            i++;
        }
        return index % HASHLEN;
    }

    /**
     * write files
     * @param filePath:filePath
     * @param fileContent:content
     */
    public static void writeFile(String filePath, String fileContent) {
        File file = new File(filePath);
        // if file doesnt exists, then create it
        FileWriter fw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fileContent);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read the cut files and make statistics
     * @return
     * Returns a statistical map
     */
    public static TreeMap<Integer,String> readAndCount(){
        String fileName =null;
        //In the Map, the data is organized in descending order.
        TreeMap<Integer,String> map = new TreeMap<>(new Comparator<Integer>(){
            /*
             * int compare(Object o1, Object o2) return int data，
             * Return a negative number means o1 < o2,
             * Return zero means o1 = o2,
             * Return A positive number means o1 > o2.
             */
            public int compare(Integer a,Integer b){
                return b-a;
            }
        });

        for(int i=0;i<list.size();i++){
            fileName = filePath+"out\\out"+list.get(i)+".txt";
            File file = new File(fileName);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file),5 * 1024 * 1024);
                String line = null;
                int count =0;
                while ((line = reader.readLine()) != null) {
                    if(!map.containsValue(line)){
                            map.put(1,line);
                    } else{
                        Iterator iter= map.keySet().iterator();
                        while (iter.hasNext()) {
                            Object key= iter.next();
                            count =Integer.valueOf(key.toString());
                            if(map.get(key).equals(line)){
                                iter.remove();
                                break;
                            }
                        }
                        map.put(count+1,line);
                    }
                }
                //if the map's capacity is bigger than 100, delete the records after 100
                if(map.size()>100) deleteMap(map);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.info("file is not found! ");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return map;
    }



    static void deleteMap(TreeMap<Integer,String> map){
        int i=0;
        Iterator iter= map.keySet().iterator();
        while (iter.hasNext()) {
            Object key= iter.next();
            if(i>99){
                iter.remove();
            }
            i++;
        }
    }
}
