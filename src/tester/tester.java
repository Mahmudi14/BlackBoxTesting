package tester;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class tester {
    public static void main(String[] args) {
        String sDir = "src/tester/";
        String sSourceCode = "Latihan.java";
        String sInput = "input.txt";
        String sOutput = "output.txt";
        
        try{
            String output = Files.readString(Path.of(sDir + sOutput));
            
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(sDir));
            processBuilder.command("cmd.exe","/c","java "+sSourceCode);
            processBuilder.redirectInput(new File(sDir+sInput));
            Process process = processBuilder.start();
            process.waitFor();
            
            String outputTesting = ExtractOutput(process);
            String errorTesting = ExtractError(process);
            
            if(errorTesting.length()>0){
                System.out.println("RESULT : ");
                System.out.println("------------------------");
                System.out.println("      PROGRAM ERROR     ");
                System.out.println("------------------------");
                System.out.println("WRONG ANSWER");
            }else{
                if(output.trim().equals(outputTesting.trim())){
                    System.out.println("RESULT : ");
                    System.out.println("PROGRAM BERHASIL");
                    System.out.println("--------------------------");
                    System.out.println(outputTesting);
                    System.out.println("--------------------------");
                    System.out.println("CORRECT");
                }else{
                    System.out.println("RESULT : ");
                    System.out.println("OUTPUT TIDAK SESUAI");
                    System.out.println("-----------------------------");
                    System.out.println(outputTesting);
                    System.out.println("-----------------------------");
                    System.out.println("WRONG ANSWER");
                }
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    static String ExtractOutput(Process process) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while((line=br.readLine())!=null){
            sb.append(line+"\n");
        }
        return sb.toString();
    }
    
    static String ExtractError(Process process) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while((line=br.readLine())!=null){
            sb.append(line+"\n");
        }
        return sb.toString();
    }
}
