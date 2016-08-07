import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/** 
 * @author Aakarshan
 * this class is my main class that is this class contains the main function and creates encoded file and encoding table.
 */
public class huffman {

	/**
	 * stores the no. of tokens in the input file.
	 */
	public int size1=0;
	
	/**
	 * it is an object of class hashtable1.
	 */
	
	public hashtable1 ht;             //object of class hashtable
	
	/**
	 * it is an object of class decode.
	 */
	
	public decode hd;      //object of class huffman_decoding

	/**
	 * it stores the minimum frequency amongst the frequency of various tokens. 
	 */
	public int min1;
	
	/**
	 * it stores the second minimum frequency amongst the frequency of various tokens.
	 */
	
	public int min2;
	
	
	/**
	 * this array stores the token which is basically a string.
	 */
	private String[] token_coded;
	
	/**
	 * this array stores the binary code of the corresponding token in the array token_coded.
	 */
	private String[] code;
	
	/**
	 * it stores the index in which token and corresponding binary code have to be added in the arrays token_coded and code respectively.
	 */
	public int counter=0;
	
	/**
	 * it stores the size of the heap which contains various trees which is initially equal to zero.
	 */
	public int heap_size=0;
	
	/**
	 * 
	 */
	String []encoded;
	
	/**
	 * @author Aakarshan
	 * this is my node class which is used in making tree.
	 */
	//-----------------------------------node class starts.------------------------------------------------
private class Node{                                         
		
	    /**
	     * stores the token.
	     */
		private String data;
		
		/**
		 * stores the frequency of the corresponding data.
		 */
		private int freq;
		
		/**
		 * points to the parent of the node.
		 */
		private Node parent; 
		
		/**
		 * points to the left child of the node.
		 */
		private Node left;
		
		/**
		 * points to the right child of the node.
		 */
		private Node right;
		
		/*
		 * constructor of the class node.
		 */
		public Node(String d,int f,Node p,Node l,Node r) //constructor
		{
			data=d;
			freq=f;
			parent=p;
			left=l;
			right=r;
		}
		
		/**
		 * @return
		 * returns the token stored in that node.
		 */
		public String getData()
		{
			return data;
		}
		
		/**
		 * @return
		 * returns the frequency of the token which is stored in that node.
		 */
		public int getFreq()
		{
			return freq;
		}
		
		/**
		 * @return
		 * returns the parent of that node.
		 */
		public Node getParent()
		{
			return parent;
		}
		
		/**
		 * @return
		 * returns the left child of the node.
		 */
		public Node getLeft()
		{
			return left;
		}
		
		/**
		 * @return
		 * returns the right child of the node.
		 */
		public Node getRight()
		{
			return right;
		}
		
		/**
		 * sets the parent of that node.
		 * @param p
		 * it is the node which has to be set as the parent.
		 */
		public void setParent(Node p)
		{
			parent=p;
		}
		
		/**
		 * sets the left child of that node.
		 * @param l
		 * it is the node which has to be set as the left child.
		 */
		public void setLeft(Node l)
		{
			left=l;
		}
		
		/**
		 * sets the right child of the node.
		 * @param r
		 * it is the node which has to be set as the right child.
		 */
		public void setRight(Node r)
		{
			right=r;
		}
	}
//------------------------------------------------node class ends---------------------------------------------------------

    /**
     * this array initially stores all the tokens and their corresponding frequencies.
     */
    public Node storage[];
    
    /**
     * it is the heap which stores the various trees in min-order fashion that is the tree with the minimum frequency is at the top.
     */
    public Node heap[];
    
    /**
     * this is the root node of the tree,initially set to null.
     */
    private static Node root=null;
    
    /**
     * constructor of the class huffman.
     */
    public huffman()      //constructor   
    {
    	ht=new hashtable1();      // ht is the object of class hashtable1.
    	hd=new decode();          // hd is the object of class hashtable2.
    }
    
    /**
     * this function adds the root to the tree.
     * 
     * @param tok
     * token which has to be added in that node.
     * @param freq
     * frequency of that token which has to be added in that node.
     * @return
     * returns the root node of the tree.
     */
    
    public Node addRoot(String tok,int freq)
    {
    	root=new Node(tok,freq,null,null,null);
    	return root;
    }
    
    
    /**
     * this function makes the tree.
     * this function is my most important function as it makes the final tree. 
     */
    
    
    public void makeTree()
    {
    	int i,j=0;
    	
    	for(i=0;i<ht.size2;i++)                      //this loop creates trees of height 1 and adds into storage[] array.
    	{
    		if(ht.table[i]!=null)                    //ht.table is the hashtable array which stores the tokens
    		      {
    			     storage[j]=addRoot(ht.table[i],ht.nwords[i]); //ht.nwords is the array in which frequency of a token at an index in table[] array is stored in the same index.
    		         j++;
    		      }
    	}
    
    	pq();   //pq() function is called which creates a priority queue in form of tree i.e. a heap.
    	
    	Node temp1;        //temporary node.
    	Node temp2;        //temporary node.
    	
    	while(heap_size>1)                 //this loop will work till all tokens are stored in one single tree.
    	{
    		
    		min1=heap[0].getFreq();        //since at heap[0],we will have the token with minimum frequency.
    		temp1=heap[0];
    	    downheap(0);                   //downheap function is called to maintain the heap-order property.
    		
    		min2=heap[0].getFreq();        //we will have token with second minimum frequency.
    		temp2=heap[0];
    		downheap(0);                   //downheap function is called to maintain the heap-order property.
    		
    		int parent_freq=min1+min2;     
    		
    		Node newest =new Node(null,parent_freq,null,temp1,temp2); 
    		//new node is created with frequency equal to sum of min1 and min2, and left child as the node temp1,and right child as the node temp2,and is inserted into the heap.
    		temp1.setParent(newest);
    		temp2.setParent(newest);
    		
            heap_size++;
            heap[heap_size-1]=newest;   //this new node is inserted into the heap.
            upheap(heap_size-1);        //upheap function is called to maintain the heap order property.
           
    	}
    	
    }
    
    /**
     * this function creates priority queue in form of a tree, basically a heap.
     * it is made in min-order fashion,which means that the node with minimumm frequency will be at top.
     */
    
    public void pq()
    {
    	int i;
    	heap[0]=storage[0];  
    	heap_size++;
    	i=1;
    	while(storage[i]!=null)     //loop works till all tokens are not added into the heap[].
    	{
    		heap[i]=storage[i];
    		upheap(i);              //upheap function is called so that the heap order property is maintained.
    		heap_size++;
    		i++;
    	}
    }
    
    /**
     * this function restores the heap-order property by travelling upwards till we get a node with less frequency.
     * 
     * @param i
     * index at which the upheap function has to be performed.
     * 
     */
    public void upheap(int i)
    {
    	int parent;
        Node temp;
    	
    	while(i!=0)   //till we travel up to the top.
    	{
    		parent=(i-1)/2;
    		if(heap[parent].getFreq()>heap[i].getFreq())   
    		{   
    			//if the frequency of parent node is more than swap the two nodes.
    			temp=heap[i];
                heap[i]=heap[parent];
    			heap[parent]=temp;			
    		}
    		
    		i=parent; //i becomes parent at each iteration.
    	}
    }
    
    /**
     * this function restores the heap-order property by travelling downword till we get a node with more frequency.
     * 
     * @param i
     * index at which the downheap function has to be performed.
     */
    
    public void downheap(int i)
    {
    	Node temp;
    	temp=heap[heap_size-1];
        heap[heap_size-1]=heap[0];
        heap[i]=temp;
        
        heap_size--;
    	
        Node tem;
    	int leftchild,rightchild,leastchild;
    	
    	leftchild=2*i+1;
    	
    	while(leftchild<heap_size&&heap[leftchild]!=null) //this loop works till node has no child.
    	{
    	
    		leftchild=2*i+1;
        	rightchild=2*i+2;
    		if(rightchild<heap_size&&heap[rightchild]!=null)
    		{	
    		     if(heap[leftchild].getFreq()<heap[rightchild].getFreq())
    	              	leastchild=leftchild;
    	         else
    		            leastchild=rightchild;
    		}
    		else
    			leastchild=leftchild;
    		
    		//least child will store if we have to swap the node with the left child or the right child.
    		
    		if(heap[i].getFreq()<heap[leastchild].getFreq())  //if frequency of leastchild is more than break the loop.
    		      break;
    		tem=heap[i];                                      //otherwise swap the two.
    		heap[i]=heap[leastchild];
    		heap[leastchild]=tem;
    		i=leastchild;                           //at each iteration i becomes the leastchild.
    		leftchild=2*i+1; 
    	}
    	
    }
    
    /**'
     * it is a recursive function which creates binary code of a token.
     * 
     * @param tree
     * contains all the tokens and their corresponding frequency.
     * @param s
     * it is a string initially null which adds 1 if we go to left child of node and 0 if we go to right child of the node.
     */
   
	public void encode(Node tree,String s)
	{
		
		int i,h;
		if(tree.getLeft()==null)          //if the tree does not have left child.
		{
			token_coded[counter]=tree.getData();     //token is added in this array.
			
			h=ht.hashcode(tree.getData());      //hashcode function is called which is in my class hashtable1.
			 while((ht.table[h]!=null)&&(!(ht.table[h].equals(tree.getData()))))
			 {
				 h++;
				 if(h==ht.size2)
					 h=h%ht.size2;
			 }
			
			 if(ht.table[h]!=null&&(ht.table[h].equals(tree.getData())))
				 encoded[h]=s;                            //binary code is stored in the same index as the token is stored in the ht.table[] array.
			 
			 
			code[counter]=s;
            counter++;
		}
		
		else
		{
		    encode(tree.getLeft(),s+"1");               //left=1       goes to the left subtree.
		    encode(tree.getRight(),s+"0");              //right=0      goes to the right subtree.
		}
		
	}

	/**
	 * it is my main function.
	 * 
	 * @param args
	 * if input is given via command line argument, then it is stored in args.
	 * 
	 * @throws IOException
	 * problem while reading the file.
	 * 
	 */
	public static void main(String[] args) throws IOException {

		 
		 String word;
		 int i,j,n=1;
		 huffman obj1=new huffman();   //obj1 is the object of class huffman.
		 
		 
		 String []input1 = null;
		 
		 int nwords=0,p=0;
		 
		 Scanner input_st=new Scanner(System.in);
		 String input_main=input_st.nextLine();
		 Scanner input2=null;
		 
		 if(input_main.charAt(0)=='d')   //if the first characcter is d it means we have to do decoding.
		      obj1.hd.decoding();                                      //goes to decoding function which is in the class decode.
		 
		 else                            //otherwise do encoding.
		 {
			 String input_string=input_main.substring(2,input_main.length());
             //input_string contains the input file name.
			 
			 String strg=new String();
			 File cbc=new File(input_string);
			 
			 int x=0;
			 
			 //I am reading my input file character by character.
			 
			 String strg1=new String();
			 int print=0,print7=0;
		     try{
			     	FileInputStream character =new FileInputStream(cbc);   //character is an object of class FileInputStream.
			        FileInputStream char1=new FileInputStream(cbc);       //char1 is an object of class FileInputStream.
			         
			     	char d,e,f='a';
			        int state9=0,size9=0,next=0,state7=0,size7=0;
			      
			         while(character.available()>0)     //this loop works till we have a next character in the input file.
		               {
		                     d=(char)character.read();  //reads the character and is stored in d.
		                     
		                     //if character is alphanumeric,then join the characters to form a token.
		                     
		                     if((d>=65&&d<=90)||(d>=97&&d<=122)||(d<=57&&d>=48))
		                     {
		                    	 print=1;
		                    	 if(state9==0)
		                    		 {  
		                    		    obj1.size1++;
		                    		    strg=new String();
    		                    	    strg=strg+Character.toString(d);   //converts and then concatenates the character to string.
    		                    	    state9=1;
   		                    		 }
		                    	 else
		                    	    {
		                    		 strg=strg+Character.toString(d);
		                    	    }
		                     }
		                     //otherwise  we have a token as single character.
		                     else
		                    	 {
		                    	   obj1.size1++;
		                    	   strg=new String();        //new string is made.
		                    	   strg=Character.toString(d);        
		                    	   state9=0;
		                    	   print=1;
		                    	 }
		                    
		   	              }
			         //above while loop is to determine the size1.
			         
		       obj1.size1=obj1.size1*2;
		        
		       //since we have size1,now we initialize all the arrays with size1.
               		        
		        obj1.ht.setsize(obj1.size1);
		        obj1.token_coded=new String[obj1.size1];
		        obj1.code=new String[obj1.size1];
		        input1=new String[obj1.size1];
		        obj1.storage=new Node[obj1.size1];
		        obj1.heap=new Node[obj1.size1];
		        
		        //again we read character by character,this time storing the tokens in the input1[] array.
		        //we also put the tokens in the hashtable array.
		        
		        while(char1.available()>0)
	               {
	                     e=(char)char1.read();
	                   
	                     //if character is alphanumeric,then join the characters to form a token.
	                     
	                     if((e>=65&&e<=90)||(e>=97&&e<=122)||(e<=57&&e>=48))
	                     {
	                    	 print7=1;
	                    	 if(state7==0)
	                    		 {  
	                    		    size7++;
	                    		    strg1=new String();            //new string is made.
		                    	    strg1=strg1+Character.toString(e);  //converts and then concatenates the character to string.
		                    	    state7=1;
		                    		 }
	                    	 else
	                    	    {
	                    		 strg1=strg1+Character.toString(e);
	                    		 if(char1.available()<=0)    //that is we reach at the last character of the input file.
	                    		 {
	                    			 obj1.ht.put(strg1);  //then put,the token in the hashtable.
		          	                   input1[x]=strg1;   //also store the token in the input1[] array.
		          	                   x++;
	                    		 }
	                    	    }
	                     }
	                     
	                     else    //that is the character to be read is not an alphanumeric.
	                    	 {
	                    	   if(print7==1)
	                    	   {
	                    		   
	                    		   obj1.ht.put(strg1);    //first store the token in hashtable.
	          	                   input1[x]=strg1;       //also store the token in input1[] array.
	          	                   x++;  
	                    	   }
	                    	   size7++;
	                    	   strg1=new String();        //make a new string.
	                    	   strg1=Character.toString(e);  //convert the character to string then store it.
	                    	   state7=0;
	                    	   print7=0;
	                    	   
	                    	   obj1.ht.put(strg1);          //put it into the hashtable.
          	                   input1[x]=strg1;             //also store in input1[] array.
          	                   x++;  
	                    	 }
	                    
	   	              }
		       
				}
			catch(IOException e){
				e.printStackTrace();
			}
	  
		  
		     obj1.makeTree();     //make tree function is  called.
				
			 obj1.encoded=new String[2*obj1.size1];      //initialize encoded array.
			 String s=new String();
			 
			 obj1.encode(obj1.heap[0],s);               //encode function is called,which creates binary code of each token.
			
		    PrintWriter output=null;    //i have used printwriter for output. 
			try{
				
				//filename2 will be the name of my output text file and it contains the encoding table.
				
	        	 output=new PrintWriter("filename2.txt","UTF-8");  
			 
	        	 
			 for(i=0;i<obj1.size1&&obj1.token_coded[i]!=null;i++)            //outputs the encoding table
			 {
				 output.println("^"+obj1.token_coded[i]+"^"+obj1.code[i]+"^");    //i have used '^' to seperate tokens and their binary codes.
			 }
			 
			 } 
			 
			 finally {
				if(output!=null)
					output.close();
			 }
			
			PrintWriter output1=null; //output1 is an object of class PrintWriter.
			try{
				//filename1 will be the name of my output text file and it contains the encoded file which contains 0's and 1's only.
				
	        	 output1=new PrintWriter("filename1.txt","UTF-8");
	        	 
	        	//outputs the encoded file containg 0's and 1's only.
			     
	        	 for(j=0;j<obj1.size1&&input1[j]!=null;j++)
			            {
			               int h;
				           h=obj1.ht.hashcode(input1[j]);
	           		    //find h till table[h] is not empty.
				           while((obj1.ht.table[h]!=null)&&(!(obj1.ht.table[h].equals(input1[j]))))
				              {
					             h++;
					             if(h==obj1.ht.size2)
					            	 h=h%obj1.ht.size2;
				              }
				           if(obj1.ht.table[h]!=null)       //if it is not empty then print the binary codes.
				            	 output1.print(obj1.encoded[h]);
			      }
			}
			finally {
			     if(output1!=null)
			 	     output1.close();
			 		}
		
		 }
	}//-------------------------------main ends--------------------------------------------
	
}//-----------------------------huffman class ends------------------------------