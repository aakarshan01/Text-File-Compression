import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * @author Aakarshan
 * this is my decode class.
 * it decodes the binary text file, into tokens with the help of encoding table.
 */

public class decode{
	
	 /**
	 *it is the hash table array containing the binary codes. 
	 */
	 public String hcode[];
	 
	
     /**
      * it is the hash table array containing the tokens of the corresponding binary code in the same index.
      */
	 public String htable[];
	 
	 /**
	  * stores the size of the hash table, initially set to zero.
	  */
	 public int size=0;
	 /**
	  * this array stores the tokens which are read from the encoding table file(file which contains token and its binary code).
	  */
	 public String tokens[];
	 
	 /**
	  * this array stores the binary codes which are read from the encoding table file(file which contains token and its binary code).
	  */
	 
	 public String bin_codes[];
	
	 /**
	  * this function stores the hash function.
	  * this function inputs a string, passes it into the hash function and returns the hash value.
	  * @param s
	  * it is the string whose hash value have to be determined.
	  * @return
	  * returns the hash value of the string.
	  */
	public int hash_code(String s)
	 {
		 
		 int i,h=0;
		 for(i=0;i<s.length();i++)
		 {
			 h=(h<<2)|(h>>30);             //I have used my hash function as rotating bits.
			 h=h+(int)s.charAt(i);         //my hash function rotates two bits from end to start.
		 }
		 
		 h=Math.abs(h);                    //h is made positive since index cannot be negative.
		 h=h%(size);                       //take mod with the size of the hashtable.
		 
		 return h;                         //returns the hash value of the string.
	 }
	
	/**
	 * this function creates the output text file.
	 * 
	 * @param s
	 * stores the name of the file as a string in which encoding table is stored.
	 * 
	 * @param bin
	 * stores the name of the file as a string in which i have encoded, file containing only 0's and 1's.
	 * 
	 * @throws FileNotFoundException
	 * if file is not found then this exception occurs.
	 */
	public static void output(String s,String bin) throws FileNotFoundException
	{
		//s=encoded table.
		//bin=binary text file.
		int m=0,p=0;
		Scanner input1=null;
		Scanner inp=null;
		
	    String word;
	    
	    decode obj2=new decode();     //obj2 is an object of class decode.

	    File cbc=new File(s);
	    
	    
	    String st=new String();
	    
	    //i am reading the encoded_table file character by character.
	    
	    int q=0,r=0;
	    try{
	     	FileInputStream character =new FileInputStream(cbc); //character is an object of class FileInputStream.
	        FileInputStream char1=new FileInputStream(cbc);      //char1 is an object of class FileInputStream.
	        
	     	char d,e;
	       
            int p1=0,p2=0,p3=0,ctr=0;     
          
           while(character.available()>0)  //this loop works till next character is present.
               {
        	
                     d=(char)character.read();   //character is stored in d.
                     
                     st=st+Character.toString(d);     //converts d to string and then concatenates to string st.
                   if(d=='^')                        // if we encounter a '^' symbol.
                   {
                	   ctr++;                      //increase the counter.
                   }
                   if(ctr==3)          //if counter becomes three,i.e. we have found three power symbols,then increase the size.
                   {
                	   obj2.size++;
                	   st=new String();  // make st=null
                       ctr=0;           //  set counter to zero.
                   }
                   
               } 
        
           //above loop was to calculate the size
           
        obj2.size=obj2.size*2+500;
        
        obj2.tokens=new String[obj2.size];
   	    obj2.bin_codes=new String[obj2.size];
   	    //we again read the file character by character.
   	    
        while(char1.available()>0)        //while loop works if the character is present.
           {
                 e=(char)char1.read();            //character is stored in e.
                 st=st+Character.toString(e);     //converts e to string and then concatenates to string st.
                 
                 if(e=='^')                      // if we encounter a '^' symbol.
                 {
              	   ctr++;                       //increase the counter.
                 }
                 if(ctr==3)                //if counter becomes three,i.e. we have found three power symbols.
                 {                        //then we extract token and its corresponding code out of the string 'st'.
                	m=0;
                	for(p=0;p<st.length();p++) 
                	{   
                		if(st.charAt(p)=='^'&&m==0)
                			{
                			  p1=p;            //p1 stores the index of first power symbol.
                 			  m++;
                			}
                		else if(st.charAt(p)=='^'&&m==1)
                		{
                			p2=p;              //p2 stores the index of the second power symbol.
                			m++;
                		}
                		else if(st.charAt(p)=='^'&&m==2)
                		{
                			p3=p;             //p3 stores the index of  the third power symbol.
                			m++;
                		}
                		
                	}
                	obj2.tokens[q]=st.substring(p1+1,p2);//extract the token and store it in tokens[] array.
                	          q++; 
                	
                	obj2.bin_codes[r]=st.substring(p2+1,p3); //extract the binary code and store it in bin_codes array.
              	              r++;
              	              
              	
                	p1=0;p2=0;p3=0;        //set p1,p2 and p3 to zero.
              	   st=new String();        //set string 'st' to null.
                   ctr=0;                 //set counter to zero.
                 }
 
           }
      
		}
	catch(IOException e){
		e.printStackTrace();
	}

		int i;
		int h;
		
		   obj2.htable=new String[obj2.size];       //htable is made, this is the hash table array containing the token.
      	   obj2.hcode=new String[obj2.size];        //hcode is made, this is the hash table array containing the binary codes.
      	 
      	   String w,y;
      	  
        //in the following for loop, hash table is made.
      	   
		for(i=0;obj2.bin_codes[i]!=null;i++)  //this loop works till all binary codes are not stored in the hashtable
		{
			w=obj2.bin_codes[i];
			y=obj2.tokens[i];
			
			h=obj2.hash_code(w);    //hash_code function is called,which sets the hash value of the string 'w' to h.
		
			//get the value of h such that hcode[h] is empty.
			
			while((obj2.hcode[h]!=null)&&(!(obj2.hcode[h].equals(w))))
			 {
				 h++;
				 if(h==obj2.size)              //if h becomes equal to size of the array
					 h=h%obj2.size;                 //then take moduli with size2,otherwise it will be out of bounds.
			 }
			
			if(obj2.hcode[h]==null&&obj2.hcode[h]!=w)  //if hcode[h] is empty,
		         {
	                obj2.hcode[h]=w;   //then enter the binary code in hcode[] array.
		            obj2.htable[h]=y;  //also enter the token in htable[] array.
		          }
		     
	      }
		String str=new String();
		
		File bin_doc=new File(bin);
		
		//now i am reading my binary text file character by character.
		try{
			FileInputStream character =new FileInputStream(bin_doc);     //character is an object of class FileInputStream.
			char c;
		    //i am using printwriter for generating output file.
			PrintWriter output2=null;      //output2 is an object of class PrintWriter.
		
			try{
				//name of my output file will be "output.txt"
				
				output2=new PrintWriter("output.txt","UTF-8");
			
	            while(character.available()>0)      //this loop works if there is a character.
	            {
	                c=(char)character.read();       //that character is stored in c.
	                str=str+Character.toString(c);  //the character is converted to string and then concatenated with string 'str'.
	            
	                h=obj2.hash_code(str);          //hashcode of the string str is calculated.
	                
	                //we find if corresponding to str, we have a string in the array hcode[].
	                
	                while((obj2.hcode[h]!=null)&&(!(obj2.hcode[h].equals(str))))
	   			            {
	   				                h++; 
	   				                if(h==obj2.size)             //if h becomes equal to size of the array
	   					                 h=h%obj2.size;                   //then take h moduli with size,otherwise it will be out of bounds.
	   			              }
	               
	   		    	if(obj2.hcode[h]!=null)
	   		                  {         //if str=hcode[h] output,the corresponding token.
	   	                                output2.print(obj2.htable[h]);
	   	                                 str=new String();            //make new string.
	   		                   }
	   		     
	   	              }
	            
			}
			
			finally {
				if(output2!=null)
					output2.close();
			 }
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * this fuction calls the output function which does the decoding.
	 * 
	 * this function is called if d is entered in the console.
	 * that is we have to do ddecoding.
	 * 
	 * @throws FileNotFoundException
	 * if file is not found then file not found exception.
	 */
	
	
	public static void decoding() throws FileNotFoundException{

		 String encoded_document="filename1.txt";      //filename1 contains the encoded documment which is of the form of 0's and 1's only.
	     String encoded_table="filename2.txt";         //filename2 contains the encoded table in which corresponding to every token we have binary code.
	     
	     Scanner input=null;
	     
	     output(encoded_table,encoded_document);      //output function is called.
	     
	     
	}

}
