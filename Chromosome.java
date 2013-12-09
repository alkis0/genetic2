/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic2;

import javax.swing.JOptionPane;

public class Chromosome {
	String Binary_Form="";//binary

	int chrom_len;//default len
	int x;
	int y;
	double value;
	double selection_value;
	double power;
	double sum;



	//constructor for f(x)=x^2
	public Chromosome(int num,int len){
		x=num;
		chrom_len=len;
		value=(int)Math.pow((double)x,2); //
		selection_value=value;
		toBinary_fx1();


	}

	//constructor for f(x,y)=-x*sin(|x|^1/2)-y*sin(|y|^1/2)
	public Chromosome(int num1,int num2,int len){
		x=num1;
		y=num2;
		sum=x+y;
		chrom_len=len;
		double x_absolute=Math.abs((double)x);
		double y_absolute=Math.abs((double)y);
		
		double x_absolute_square=Math.sqrt(x_absolute);
		double y_absolute_square=Math.sqrt(y_absolute);
		
		value=-x*Math.sin(x_absolute_square)-y*Math.sin(y_absolute_square);
		selection_value=value;
		toBinary_fx2();
		System.out.println(value);
		
	}
	
	
	//binary code for chromosome (function fx=x^2)
	public void toBinary_fx1(){
		int i;
		String temp="";
		int n=x;
		while(n!=0){
			int remain=n%2;
			n/=2;
			if(remain==1)
				temp+="1";

			else
				temp+="0";

		}
		for(i=temp.length();i<chrom_len;i++)
			temp+="0";
		
		char s[]=temp.toCharArray();
		for(i=temp.length()-1;i>=0;i--)
			Binary_Form+=String.valueOf(s[i]);


	}
	
	//binary code for chromosome (fucntion f(x,y)=-x*sin(|x|^1/2)-y*sin(|y|^1/2))
	public void toBinary_fx2(){
		//binary form for 2 integers will be like this:
		//example x=2 y=3 and length=8
		//then Binary_Form=00000010;00000011
		
	
		int n1,n2;
		int signx,signy=signx=1;
		
		if(x>=0)
			n2=x;
		else {
			n2=-x;
			signx=-1;
		}
		if(y>=0)
			n1=y;
		else{
			n1=-y;
			signy=-1;
		}
		
		
		int i;
		String temp="";
		
		//y code
		while(n1!=0){
			int remain=n1%2;
			n1/=2;
			if(remain==1)
				temp+="1";
			else
				temp+="0";
			
		}
		
		
		for(i=temp.length();i<chrom_len/2;i++)
			temp+="0";
		if(signy==-1) temp+="b1";
		else temp+="b0";
		
		temp+=";";
		while(n2!=0){
			int remain=n2%2;
			n2/=2;
			if(remain==1)
				temp+="1";
			else
				temp+="0";
			
		}
		
		for(i=temp.length();i<chrom_len+3;i++)
			temp+="0";
		if(signx==-1) temp+="b1";
		else temp+="b0";
		
		char s[]=temp.toCharArray();
	
		
		for(i=temp.length()-1;i>=0;i--)
			Binary_Form+=String.valueOf(s[i]);
		
		System.out.println(Binary_Form);
	
	}
	
	
	//evaluate for f(x)=x^2
	public void toDecimal_evaluate_fx1(int len){
		int i;
		this.x=0;
		char c[]=Binary_Form.toCharArray();
		for(i=0;i<len;i++){
			//System.out.print(c[i]);
			if(c[i]=='1')
				this.x+=(int)Math.pow(2,len-1-i);
			else continue;

		}
		value=(int)Math.pow(x,2);
		selection_value=value;
		
	}
	
	
	//evaluate for f(x,y)=-x*sin(|x|^1/2)-ysin(|y|^1/2)
	public void toDecimal_evaluate_fx2(int len){
		int signx=1;
		int signy=1;
		if(x<0) signx=-1;
		if(y<0) signy=-1;
		
		x=y=0;
		int i=2;
		char c[]=Binary_Form.toCharArray();
		
		int l=len/2;
		//JOptionPane.showMessageDialog(null, l);
		int k=0;
		while(true){
			if(c[i]=='1'){
				x+=(int)(Math.pow(2,l-1-k));
			}
			else if(c[i]==';') break;
			i++;
			k++;
		}
			//JOptionPane.showMessageDialog(null, x);
		i+=3;
		
		k=0;
		
		for(i=i;i<len+5;i++){
			if(c[i]=='1'){
		
				y+=(int)(Math.pow(2,l-1-k));
			}
			k++;
		}
		//JOptionPane.showMessageDialog(null, y);
		
		x=signx*x;
		y=signy*y;
		
		double x_absolute=Math.abs((double)x);
		double y_absolute=Math.abs((double)y);
		
		double x_absolute_square=Math.sqrt(x_absolute);
		double y_absolute_square=Math.sqrt(y_absolute);
		
		selection_value=value=-x*Math.sin(x_absolute_square)-y*Math.sin(y_absolute_square);
		
				
	}
	
	
}
