package recibir;

import java.io.FileReader;
import javax.speech.Central;
import javax.speech.EngineModeDesc;
import javax.speech.recognition.Recognizer;
import javax.speech.recognition.Result;
import javax.speech.recognition.ResultAdapter;
import javax.speech.recognition.ResultEvent;
import javax.speech.recognition.ResultToken;
import javax.speech.recognition.RuleGrammar;
import guardar.*;
import java.util.Locale;

public class Main extends ResultAdapter{

    static Recognizer recognizer;
    String palabra;
    String contraseña="contador";
    Boolean escribir=false;
    int m=0;
   
    @Override
    public void resultAccepted(ResultEvent re){
        try{
            Result res = (Result)(re.getSource());
            ResultToken tokens[] = res.getBestTokens();
            String args[] = new String[1];
            args[0]="";
            for (int i=0; i<tokens.length; i++){
                palabra = tokens[i].getSpokenText();
                args[0] += palabra;
                args[0]+=" ";
            }
            if(palabra.equals("salir")&&m==0){
                recognizer.deallocate();
                args[0]="Hasta la proxima curso";
                System.out.println(args[0]);
                System.exit(0);
            }
            
            else if(palabra.equals(contraseña)&&m==0){
            	args[0]="Contraseña correcta, bienvenido...";
                System.out.println(args[0]);
                m=1;
            }
            else if(palabra.equals(contraseña)==false&&m==0) {
            	args[0]="Contraseña incorrecta, intente denuevo";
                System.out.println(args[0]);
            }
            else if(palabra.equals("nuevaclave")&&m==1) {
            	args[0]="Ingrese la nueva contraseña";
                System.out.println(args[0]);
                new NewClass();
                m=2;
                recognizer.suspend();
                recognizer.resume();
            }
            else if(m==2) {
            	contraseña=palabra;
            	System.out.println(contraseña);
            	m=1;
            	args[0]="Contraseña cambiada";
                System.out.println(args[0]);
            }
            else if(palabra.equals("salir")&&m==1) {
            	args[0]="Cerrando Sesión...";
                System.out.println(args[0]);  
                m=0;
            }
            else if(palabra.equals("inicioescribir")&&m==1) {
            	args[0]="Iniciando Estritura..";
                System.out.println(args[0]);  
                escribir = true;
            }
            else if(escribir) {
            	System.out.println(args[0]); 
            }
            recognizer.suspend();
            recognizer.resume();
        }
        catch(Exception ex){
            System.out.println("ha ocurrido un error" + ex);
        }
    
    }
            
    public static void main(String[] args) {
        try{
            recognizer = Central.createRecognizer(new EngineModeDesc(Locale.ROOT));
            recognizer.allocate();
            FileReader fileReader = new FileReader("C:\\Users\\Chuber\\Documents\\Ubertito\\UNSA\\2do\\ARQUITECTURA DE COMPUTADORES\\LABORATORIO\\datos extra\\my_grammar.txt");
            RuleGrammar grammar = recognizer.loadJSGF(fileReader);
            grammar.setEnabled(true);
            recognizer.addResultListener(new Main());
            System.out.println("Empezar dictado");
            recognizer.commitChanges();
            recognizer.requestFocus();
            recognizer.resume();
        }
        catch(Exception e){
            System.out.println("Error en" + e.toString() );
            e.printStackTrace();
            System.exit(0);
        }
    }
    
}

