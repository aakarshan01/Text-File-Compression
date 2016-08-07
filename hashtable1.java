/**
 * 
 * @author Aakarshan
 * this is the hashtable class and creates the hashtable.
 */
public class hashtable1 {

	/**
	 * size of the hashtable.
	 */
public int size2;

/**
 * this function sets the size of the hashtable and initializes table ans=d nwords array.
 * 
 * @param size
 * contains the no. of words in the input file.
 */
 public void setsize(int size)
 {
	 size2=2*size;
	
	 table=new String[size2];
	 nwords=new int[size2];       
	
	 for(int i=0;i<size2;i++)
	 {
		 table[i]=new String();
		 table[i]=null;
	 }
 }

 /**
  * this is the hashtable array which stores the tokens.
  */
 public String table[];
 
 /**
  * this is the array which stores the frequency of the tokens .
  */
 public int nwords[];
 
 /**
  * this function stores the hash function.
  * this function inputs a string, passes it into the hash function and returns the hash value. 
  * @param s
  * it is the string whose hash value have to be determined.
  * @return
  * returns the hash value of the string.
  */
 public int hashcode(String s)
 {
	 
	 int i,h=0;
	 for(i=0;i<s.length();i++)  
	 {
		 h=(h<<5)|(h>>>27);    //I have used my hash function as rotating bits.
		 h=h+(int)s.charAt(i); //my hash function rotates five bits from end to start.
	 }
	 
	 h=Math.abs(h);  //h is made positive since index cannot be negative.
	 h=h%(size2);    //take mod with the size of the hashtable.
	 
	 return h;     //returns the hash value of the string.
 }

 /**
  * this function takes a token as an argument and puts it into the hash table.
  * 
  * @param word
  * it is the token which we have to put in the hash table.
  */
 public void put(String word)
 {
	 int h=hashcode(word);           //hash value of the token is stored in h.
	 
	 //get the value of h such that table[h] is empty. 
	 while((table[h]!=null)&&(!(table[h].equals(word))))
	 {    
		 h++;
		 if(h==size2)               //if h becomes equal to size of the array
			 h=h%size2;                   //then take h moduli with size2,otherwise it will be out of bounds.
	 }
	 
	 if(table[h]==null&&table[h]!=word)  //if table[h] is null
	    {
         table[h]=word;                       //then enter the word in table[] array.
	     nwords[h]=1;                         //also set the frequency of nwords[h] to 1.
	    }
	 else
		 nwords[h]++;                 // if the token is already in the hash table, then just increase the frequency of the word.
 }
}