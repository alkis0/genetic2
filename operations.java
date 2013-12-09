/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
 

/**
 * Στατική κλάση operations
 * Περιέχει όλες τις λειτουργίες του γενετικού 
 * αλγορίθμου.Για το ποιά συνάρτηση θα εφαρμοστούν οι 
 * λειτουργίες ορίζει η μεταβλητή fx.Ισχύει fx=1 για f(x)=x^2
 * και fx=2 για f(x)=-x*sin(|x|^1/2)-y*sin(|y|^1/2).
 *	
 *
 * 
 * 
 *
 * 
 */


public class operations{
	
	static int fx=2;
	
	
	/**
	 * Στατική μέθοδος createPopulation τύπου ArrayList<Chromosome>
	 * με ορίσματα την λίστα με τα χρωμοσώματα (chromosome_list),τα μέλη του πληθυσμού (members),
	 * και το μήκος χρωμοσώματος.Επιστρέφει μία λίστα με χρωμοσώματα που αποτελλεί τον αρχικό πληθυσμό, τα
	 * μέλη του οποίου παράγονται τυχαία.
	 * 
	 * 
	 * 
	 */
	public static ArrayList<Chromosome> createPopulation(ArrayList<Chromosome> chromosome_list,int members,int len){
		Random rand=new Random(System.currentTimeMillis());

		
		
		//Για την πρώτη συνάρτηση παράγονται μοναδικοί τυχαίου αριθμοί.
		if(fx==1){
			int k;

			for(int i=0;i<members;i++){
				//παραγωγή τυχαίου αριθμού έτσι ώστε να είναι μοναδικός
				k=(int)(rand.nextDouble()*8);
				test:{
					for(int j=0;j<chromosome_list.size();j++)
						if(chromosome_list.get(j).x==k){
							i--;
							break test;
						}
				//Δημιουργία χρωμοσώματος με τον αριθμό που παράγεται καθώς και το
				//μήκος του χρωμοσώματος.Έπειτα προσθήκη στη λίστα. 
				
				Chromosome x=new Chromosome(k,len);
				chromosome_list.add(x);					   
			   }
		   }

		}
		else if(fx==2){
			int k1=0,k2=0;
			int sign_selection=0;
			for(int i=0;i<members;i++){
				//if one then possitive number
				//else negative number
				sign_selection=(int)(rand.nextDouble()*2);
				if(sign_selection==1)
					k1=(int)(rand.nextDouble()*8);
				else
					k1=(int)(rand.nextDouble()*-8);
				sign_selection=(int)(rand.nextDouble()*2);
				if(sign_selection==1)
					k2=(int)(rand.nextDouble()*8);
				else
					k2=(int)(rand.nextDouble()*-8);
				
				Chromosome x=new Chromosome(k1,k2,len);
				chromosome_list.add(x);
		
			}
		
	
			
			
		}
		return chromosome_list;


	}


	public static ArrayList<Chromosome> createNewPopulation(ArrayList<Chromosome> chromosome_list,int members,int len,Form f,Random x){
		Chromosome x1,x2;
		boolean found=true;
		double max=0;
		if(fx==1)
			while(found){
				found=false;
				for(int i=0;i<chromosome_list.size()-1;i++){
					x1=chromosome_list.get(i);
					x2=chromosome_list.get(i+1);
					if(x1.value>x2.value){
						found=true;
						Chromosome temp=x1;
						x1=x2;
						x2=temp;
						chromosome_list.set(i,x1);
						chromosome_list.set(i+1,x2); //
					}


				}
			}
		else
			while(found){
				found=false;
				for(int i=0;i<chromosome_list.size()-1;i++){
					x1=chromosome_list.get(i);
					x2=chromosome_list.get(i+1);
					if(x1.value>x2.value){
						found=true;
						Chromosome temp=x1;
						x1=x2;
						x2=temp;
						chromosome_list.set(i,x1);
						chromosome_list.set(i+1,x2); //
					}


				}
			}
			
		if(f.checkbox_escalate().getState() || f.checkbox_elitism().getState()){
			max=chromosome_list.get(members-1).value;
			System.out.println(max);
			if(f.checkbox_escalate().getState())
				for(int i=0;i<members;i++)
					if(chromosome_list.get(i).value<=.1*max){
						chromosome_list.get(i).selection_value=.1*max;
					}
				
		}
		
		
		f.display(chromosome_list,members,0);
		f.results().append("===============================\n");
		//x=new Random(System.currentTimeMillis());
		//0-0.999999

		//find total value of chromosomes(for average)
		int total=0;
		for(int i=0;i<chromosome_list.size();i++){
			if(f.checkbox_escalate().getState())
				total+=Math.abs(chromosome_list.get(i).selection_value);
			else
				total+=Math.abs(chromosome_list.get(i).value);
		}

		//percentage to bar system
		double bar=0;


		int pos=0;

		for(int i=0;i<members;i++){
			if(f.checkbox_escalate().getState()){
				chromosome_list.get(i).power=bar+Math.abs(chromosome_list.get(i).selection_value);
				bar+=Math.abs(chromosome_list.get(i).selection_value);
			
			}
			else{
				chromosome_list.get(i).power=bar+Math.abs(chromosome_list.get(i).value);
				bar+=Math.abs(chromosome_list.get(i).value);
			}
			//System.out.println("chromosome "+i+" power: "+chromosome_list.get(i).power);
		}



		
		ArrayList<Chromosome> selection=new ArrayList<Chromosome>();
		boolean ok=false;
		int c=0;
		for(int i=0;i<members;i++){
			double k=x.nextDouble()*total;
			//System.out.println("random choice: "+k);
			for(int j=0;j<members;j++){
				if(k<chromosome_list.get(j).power){
					//if(chromosome_list.get(j).value==max) 
					//	selection.add(i,new Chromosome(chromosome_list.get(j-1).x,len));
					//else
						if(fx==1)
							selection.add(c,new Chromosome(chromosome_list.get(j).x,len));//
						else
							selection.add(c,new Chromosome(chromosome_list.get(j).x,chromosome_list.get(j).y,len));//
						c++;
						break;
				}
				
			}

			//results.append(i+1+"\t"+selection.get(i).Binary_Form+"\t"+selection.get(i).value+"\n");

		}
	
		chromosome_list=selection;
		found=false;
		if(f.checkbox_elitism().getState()){
			for(int i=0;i<members;i++)
				if(chromosome_list.get(i).value==max){
					found=true;
					System.out.println("eleos");
					break;
				}
			if(!found){
				chromosome_list.add(members,new Chromosome((int)Math.sqrt(max),len));
				members++;
				f.members=members;
			}
		}
		
		
		f.display(chromosome_list,members,1);
		return chromosome_list;


	}

	public static void Mutation(ArrayList<Chromosome> chromosome_list,int members,int len,Form f,double chance){
		int mutation_number=(int)Math.ceil(members*chance);
		Random k=new Random(System.currentTimeMillis());

		f.results().append("\n");
		f.results().append("elements for mutation\n");
		f.results().append("===============================\n");

		for(int i=0;i<mutation_number;i++){
			double a=k.nextDouble()*members;//random selection through members for mutation

			int member=(int)a;
			f.results().append("member "+member+" selected: "+chromosome_list.get(member).Binary_Form+"\n");
			a=(k.nextDouble()*len);//if 8 bits a=0-7
			int pos=(int)a;
			
			char bf[]=chromosome_list.get(member).Binary_Form.toCharArray();
			
			if(fx==2){
				
					//with the current format
					//if len=16 and x=2 y=3
					//i have:
					//0b00000010;0b00000011
					//the random number from k.nextDouble()*len
					//is a number between 0-15 (since i have 16 bits)
					//if i have a number smaller than len/2 
					//example 16/2=8 i need to bypass the first two bits
					//and if i have a number between len/2 and len-1 (8-15)
					//i need to add 3+2 to bypass the next sign bits plus the ;
					
					if(pos<len/2)
						pos+=2;
					else
						pos+=5;
					
					
				
			}
			f.results().append("attribute selected: "+pos+"\n");
			System.out.println("member "+member+" selected: "+chromosome_list.get(member).Binary_Form+"\n");
			System.out.println("attr selected: "+pos);
			if(bf[pos]=='1') bf[pos]='0';
			else bf[pos]='1';
			
		
			chromosome_list.get(member).Binary_Form=new String(bf);
			if(fx==1)
				chromosome_list.get(member).toDecimal_evaluate_fx1(len);
			else
				chromosome_list.get(member).toDecimal_evaluate_fx2(len);
			System.out.println("member "+member+" became: "+chromosome_list.get(member).Binary_Form+"\n");
			

		}


		//f.display(chromosome_list, mutation_number, 1);
		//f.results().append("===============================\n");
		f.display(chromosome_list, members, 1);
	}




	public static void exchange(ArrayList<Chromosome> chromosome_list,int members,int len,Form f,double chance){

		Random x=new Random(System.currentTimeMillis());

		int exchange_pairs=(int)(Math.ceil(chance*members));
		//System.out.println("exchange pairs: "+exchange_pairs);
		int a_member,b_member;
		int pos1,pos2;
		Chromosome a,b;
		char b1[],b2[];
		int count=0;
		
		if(fx==1){
			for(int i=0;i<members-1;i++)
				if(	chromosome_list.get(i).x==chromosome_list.get(i+1).x)
					count++;
			if(count==members-1){
				//JOptionPane.showMessageDialog(null,"same members");
				return;
			}
		}
		
		for(int i=0;i<exchange_pairs;i++){
			if(fx==1)
				do{
					a_member=(int)(x.nextDouble()*members);
					b_member=(int)(x.nextDouble()*members);

				}while(a_member==b_member || chromosome_list.get(a_member).x==chromosome_list.get(b_member).x);
			else
				do{
					a_member=(int)(x.nextDouble()*members);
					b_member=(int)(x.nextDouble()*members);

				}while(a_member==b_member);
				
			f.results().append("selected members for "+i+" crossover: "+a_member+" "+b_member+"\n");

			//compute exchange elements posittions
			
			do{
				pos1=(int)(x.nextDouble()*len);
				pos2=(int)(x.nextDouble()*len);
			}while(pos1==pos2);
			a=chromosome_list.get(a_member);
			b=chromosome_list.get(b_member);
			
			
			
			b1=a.Binary_Form.toCharArray();
			b2=b.Binary_Form.toCharArray();
			
			pos1=8;
			if(fx==2){
				if(pos1<len/2){
					pos1+=2;
				}
				else{
					pos1+=5;
				}
				
				if(pos2<len/2)
					pos2+=2;
				
				else
					pos2+=5;
				
				
			}
			
			f.results().append(pos1+" "+pos2+"\n");
			f.results().append("before crossover\n");
			f.results().append(a.Binary_Form+"\n"+b.Binary_Form+"\n");
		
			
			char temp;
			temp=b1[pos1];
			b1[pos1]=b2[pos1];
			b2[pos1]=temp;

			temp=b1[pos2];
			b1[pos2]=b2[pos2];
			b2[pos2]=temp;


			a.Binary_Form=new String(b1);
			b.Binary_Form=new String(b2);
			System.out.println(a.Binary_Form);
			System.out.println(b.Binary_Form);
			
			if(fx==1){
				a.toDecimal_evaluate_fx1(len);
				b.toDecimal_evaluate_fx1(len);
			}
			else{
				a.toDecimal_evaluate_fx2(len);
				b.toDecimal_evaluate_fx2(len);
			}
				
			//System.out.println("val1 "+a.Binary_Form);
			//System.out.println("val2 "+b.Binary_Form);
		//	f.results().append("after exchange\n");
			f.results().append(a.Binary_Form+"\n"+b.Binary_Form+"\n");
			f.display(chromosome_list,members,1);
			
	
		}
	}


	public static void Inversion(ArrayList<Chromosome> chromosome_list,int members,int len,Form f,double chance){
		Random x=new Random(System.currentTimeMillis());
		//τα μέλη για αντιστροφή.Είναι ο πολλαπλασιασμός της πιθανότητας που πέρνουμε απο το
		//αντίστοιχο textbox στην φόρμα επί τα συνολικά μέρη
		int inversion_members=(int)(chance*members);
		//μεταβλητές αποθήκευσης τυχαίων θέσεων που θα εφαρμοστεί η αναστροφή
		int pos1;
		int pos2;
		int member;
		
		//επανάληψη πλήθους inversion_members
		for(int i=0;i<inversion_members;i++){
			//κάθε φορά στη μεταβλητή member επιλέγεται τυχαία ένα μέλος
			//από το συνολικό πληθυσμό
			member=(int)(x.nextDouble()*members);
			//εκτυπώσεις
			f.results().append("selected: "+member+"\n");
			f.results().append("before: "+chromosome_list.get(member).Binary_Form+"\n");
			//επανάληψη για επιλογή τυχαίων θέσεων για αναστροφή
			//έτσι ώστε να μην είναι ίδιες
			do{
				pos1=(int)(x.nextDouble()*len);
				pos2=(int)(x.nextDouble()*len);
			}while(pos1==pos2);
			f.results().append("pos: "+pos1+" "+pos2+"\n");
			//Σπάω το String που υπάρχει η δυαδική μορφή του
			//χρωμοσώματος που επιλέχτηκε σε πίνακα χαρακτήρων
			//για να κάνω τις αλλαγές
			char a[]=chromosome_list.get(member).Binary_Form.toCharArray();
			
			//εξετάζω τις περιπτώσεις
			if(a[pos1]=='1') a[pos1]='0';
			else a[pos1]='1';
			if(a[pos2]=='1') a[pos2]='0';
			else a[pos2]='1';

			//αποθήκευση του String που προκύπτει σαν νεα δυαδική μορφή
			//του χρωμοσώματος και υπολογισμός της νέας τιμής του
			chromosome_list.get(member).Binary_Form=new String(a);
			chromosome_list.get(member).toDecimal_evaluate_fx1(len);
			f.results().append("after: "+chromosome_list.get(member).Binary_Form+"\n");
			f.display(chromosome_list,members,1);
		}

	}

}