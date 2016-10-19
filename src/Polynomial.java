<<<<<<< HEAD
=======
<<<<<<< HEAD
<<<<<<< HEAD
Hello World at branch
=======
Hello World at branch
>>>>>>> C4
=======
Hello World at branch
>>>>>>> B2
>>>>>>> origin2/test
import java.util.*;
import java.util.regex.*;
import java.lang.*;
public class Polynomial {
	private String ch0,input;//原始
	private Element[] e;//化简
	private Map<String,Integer> map;//存值
	private Map<String,Integer> sim;//合并同类项
	//private char[] ch2;//后缀
	public Polynomial(String str){
		this.input=new String(str);
		this.ch0=new String(str);
		map=new HashMap<String,Integer>();
	}
	
	public void translation(){
		ch0=ch0.replace(" ", "");//去空格
		ch0=ch0.replace("\t", "");//去tab
		Pattern p=Pattern.compile("(([a-z]{1,})\\^([0-9]{1,}))");
		Matcher m=p.matcher(ch0);
		while(m.find()){//去乘方
			String a=new String(m.group(2)),temp=new String(m.group(2));
			for(int i=0;i<Integer.parseInt(m.group(3))-1;i++){
				temp=temp.concat("*"+a);
			}
			ch0=ch0.replace(m.group(1),temp);
		}
		
	}
	
	public boolean isLegal(){/*多项式是否合法*/
		this.translation();//转化为合法形式
		boolean flag=true;
		char[] temp=ch0.toCharArray();
		int i=temp.length-1;
		if(!((temp[0]>='0'&& temp[0]<='9')||(temp[0]>='a' && temp[0]<='z'))){
			/*首尾是否合法*/
			flag=false;
		}else if(!((temp[i]>='0'&& temp[i]<='9')||(temp[i]>='a' && temp[i]<='z'))){
			flag=false;
		}else{
			for(i=1;i<temp.length;i++){
				if(!((temp[i]>='0'&& temp[i]<='9')||(temp[i]>='a' && temp[i]<='z')||(temp[i]=='+')||(temp[i]=='*')||(temp[i]=='-'))){
					/*字符是否合法*/
					flag=false;
					break;
				}
			}
			if(flag){
				for(i=0;i<temp.length-1;i++){
					if(temp[i]>='0'&& temp[i]<='9'){
						if(!(temp[i+1]=='+' || temp[i+1]=='*' || (temp[i+1]=='-') || (temp[i+1]>='0'&& temp[i+1]<='9'))){
							flag=false;break;
						}
					}else if(temp[i]>='a' && temp[i]<='z'){
						if(!(temp[i+1]=='+' || temp[i+1]=='*' || (temp[i+1]=='-') || (temp[i+1]>='a' && temp[i+1]<='z'))){
							flag=false;break;
						}
					}else{
						if(temp[i+1]=='+' || temp[i+1]=='*' || (temp[i+1]=='-')){
							flag=false;break;
						}
					}
				}
			}
		}
		return flag;
	}
	
	public void Order(String order){/*程序入口*/
		if(this.isLegal()){
			this.expression();
			/*char[] temp=ch0.toCharArray();
			for(int i=0;i<temp.length;i++){
				if(temp[i]>='a' && temp[i]<='z'){
					map.put(Character.toString(temp[i]), -1);
				}
			}*/
			Pattern p1=Pattern.compile("!simplyfy\\s*|^!simplyfy\\s([a-z]{1,}=[0-9]{1,}\\s*)*\\s*");
			Pattern p2=Pattern.compile("!d/d\\s+[a-z]{1,}\\s*");
			Pattern p3=Pattern.compile("([a-z]{1,})=[0-9]{1,}");
			Pattern p4=Pattern.compile("\\s+([a-z]{1,})\\s*");
			Pattern p5=Pattern.compile("[a-z]{1,}");
			Matcher m1=p1.matcher(order),m2=p3.matcher(order),m3=p4.matcher(order),m5=p5.matcher(ch0);
			while(m5.find()){
				map.put(m5.group(), -1);
			}
			if(m1.matches()){
				boolean flag=false;
				int count=0;
				Set<String> set=new HashSet<String>();
				while(m2.find()){
					count++;
					if(map.containsKey(m2.group(1))){
						set.add(m2.group(1));
					}else{
						flag=false;
						break;
					}
				}if(set.size()!=count){
					flag=false;
				}else{
					flag=true;
				}
				if(flag){
					System.out.println(this.simplyfy(order));
				}else{
					System.out.println("Order is Wrong!");
				}
			}else if(p2.matcher(order).matches()){
				if(m3.find() && map.containsKey(m3.group(1))){
					System.out.println(this.derivative(order));
				}else{
					System.out.println("Order is Wrong!No Variable!");
				}
			}else{
				System.out.println("Order is Wrong!");
			}
		}else{
			System.out.println("Polynomial is wrong!");
		}
	}
	
	private void expression(){//将表达式转换为Element,存值
		String ch=ch0.replace("-", "+-");//在“-”前加入”+“
		String[] ch1=ch.split("\\+"),temp0;
		e=new Element[ch1.length];
		for(int i=0;i<ch1.length;i++){
			e[i]=new Element();
		}
		int temp1=1;
		Pattern p=Pattern.compile("[0-9]{1,}");
		for(int i=0;i<ch1.length;i++){
			if(ch1[i].charAt(0)=='-'){//设置正负
				e[i].setsym();ch1[i]=ch1[i].substring(1);
			}
			temp0=ch1[i].split("\\*");
			for(int j=0;j<temp0.length;j++){
				if(p.matcher(temp0[j]).matches()){
					temp1*=Integer.parseInt(String.valueOf(temp0[j]));
				}else{
					e[i].addMap(temp0[j]);
				}
			}e[i].setcoe(temp1);temp1=1;
		}
	}
	
	private String simplyfy(String order){//命令已合法
		String result=new String();
		Tuple temp=new Tuple();
		sim=new HashMap<String,Integer>();
		int num=0;
		Pattern p0=Pattern.compile("([a-z]{1,})=([0-9]{1,})");
		Matcher m=p0.matcher(order);
		while(m.find()){
			map.put(m.group(1), Integer.parseInt(m.group(2)));
		}
		for(int i=0;i<e.length;i++){//合并同类项
			temp=e[i].simplyfy(map);//单项求值/化简
			if(sim.containsKey(temp.str)){
				temp.index+=sim.get(temp.str);
				sim.put(temp.str, temp.index);
			}else{
				sim.put(temp.str, temp.index);
			}
		}for(String s:sim.keySet()){
			if(s.equals("1")){
				num=sim.get(s);
			}else{
				if(result==null || result.length()<=0){
					if(sim.get(s)==1){
						result=result.concat(s);
					}else if(sim.get(s)==-1){
						result=result.concat("-"+s);
					}else{
						if(sim.get(s)!=0)
							result=result.concat(Integer.toString(sim.get(s))+"*"+s);
					}
				}else{
					if(sim.get(s)==1){
						result=result.concat("+"+s);
					}else if(sim.get(s)==-1){
						result=result.concat("-"+s);
					}else{
						if(sim.get(s)!=0)
							result=result.concat("+"+Integer.toString(sim.get(s))+"*"+s);
					}
				}
			}
		}
		if(num==0){
			if(result==null || result.length()<=0){
				result="0";
			}else{
				if(result.charAt(0)=='+'){
					result=result.substring(1);
				}	
			}
		}else{
			if(result==null || result.length()<=0){
				result=Integer.toString(num);
			}else{
				result=Integer.toString(num).concat("+"+result);
			}
		}result=result.replace("+-", "-");
		return result;
	}
	
	private String derivative(String order){//命令已合法
		String result=new String();
		Tuple temp=new Tuple();
		sim=new HashMap<String,Integer>();
		int num=0;
		Pattern p0=Pattern.compile("\\s+([a-z]{1,})\\s*");
		Matcher m=p0.matcher(order);
		if(m.find()){
			for(int i=0;i<e.length;i++){
				temp=e[i].derivative(m.group(1));//System.out.println("Tuple str:"+temp.str+" index:"+temp.index);
				if(sim.containsKey(temp.str)){
					temp.index+=sim.get(temp.str);
					sim.put(temp.str, temp.index);
				}else{
					sim.put(temp.str, temp.index);
				}
			}for(String s:sim.keySet()){
				if(s.equals("1")){
					num=sim.get(s);
				}else{
					if(result==null || result.length()<=0){
						if(sim.get(s)==1){
							result=result.concat(s);
						}else if(sim.get(s)==-1){
							result=result.concat("-"+s);
						}else{
							if(sim.get(s)!=0)
								result=result.concat("+"+Integer.toString(sim.get(s))+"*"+s);
						}
					}else{
						if(sim.get(s)==1){
							result=result.concat("+"+s);
						}else if(sim.get(s)==-1){
							result=result.concat("-"+s);
						}else{
							if(sim.get(s)!=0)
								result=result.concat("+"+Integer.toString(sim.get(s))+"*"+s);
						}
					}
				}
			}
			if(num==0){
				if(result==null || result.length()<=0){
					result="0";
				}else{
					if(result.charAt(0)=='+'){
						result=result.substring(1);
					}
				}
			}else{
				if(result==null || result.length()<=0){
					result=Integer.toString(num);
				}else{
					result=Integer.toString(num).concat(result);
				}
			}
		}result=result.replace("+-", "-");
		return result;
	}
	
	public static void main(String[] args){
		Scanner i=new Scanner(System.in);
		String str1=i.nextLine();
		while(true){
			Polynomial p=new Polynomial(str1);
			if(p.isLegal()){
				System.out.println(str1);
				String s=i.nextLine();
				while(s.charAt(0)=='!'){
					long startTime=System.nanoTime();
					p.Order(s);
					long endTime=System.nanoTime();
					System.out.println("程序开始时间： "+startTime+"ns");
					System.out.println("程序结束时间： "+endTime+"ns");
					System.out.println("程序运行时间： "+(endTime-startTime)+"ns");
					s=i.nextLine();
				}str1=s;
			}else{
				System.out.println("Polynomial is Wrong!");
				str1=i.nextLine();
			}
		}
}

}

class Tuple {
	public int index;
	public String str;
	public Tuple(int x,String y){
		index=x;
		str=new String(y);
	}
	public Tuple(){
		index=0;
		str=new String();
	}
}

class Element{
	private boolean sym;
	private int coe;
	private Map<String,Integer> m;//存储该乘法项包含的变量及其指数
	public Element(){
		this.coe=1;
		sym=false;
		m=new HashMap<String,Integer>();
	}
	public boolean getsym(){
		return sym;
	}
	public void setsym(){
		sym=true;
	}
	public int getcoe(){
		return this.coe;
	}
	public void setcoe(int num){
		this.coe=num;
	}
	public void print(){
		System.out.println("coe:"+coe);
		for(String str:m.keySet()){
			System.out.println("key:"+str+" index:"+Integer.toString(m.get(str)));
		}
	}
	public void addMap(String str){
		if(!m.containsKey(str)){
			m.put(str,1);
		}else{
			int d=m.get(str)+1;
			m.put(str,d);
		}
	}
	public Tuple simplyfy(Map<String,Integer> v){
		int temp=0;
		if(sym){
			temp=-1*this.coe;
		}else{
			temp=this.coe;
		}
		String result=new String();
		if(!m.isEmpty()){
			for(String str:m.keySet()){
				int index=m.get(str);
				if(v.get(str)!=-1){
					temp*=Math.pow(v.get(str), index);
				}else{
					if(index==1){
						result=result.concat("*"+str);
					}else{
						result=result.concat("*"+str+"^"+Integer.toString(index));
					}
				}
			}if(temp!=0){
				if(result==null || result.length()<=0){
					return new Tuple(temp,"1");
				}else{
					return new Tuple(temp,result.substring(1));
				}
					
			}else{
				return new Tuple(temp,"1");
			}
		}else{
			return new Tuple(temp,"1");
		}
	}

	public Tuple derivative(String str){
		int temp=0;
		if(sym){
			temp=-1*this.coe;
		}else{
			temp=this.coe;
		}
		String result=new String();
		if(!m.isEmpty() && m.containsKey(str)){
			for(String s:m.keySet()){
				if(!s.equals(str)){
					if(m.get(s)==1){
						result=result.concat("*"+s);
					}else{
						result=result.concat("*"+s+"^"+Integer.toString(m.get(s)));
					}
				}else{
					if(m.get(s)!=1){
						temp*=m.get(s);
						if(m.get(s)==2){
							result=result.concat("*"+s);
						}else{
							result=result.concat("*"+s+"^"+Integer.toString(m.get(s)-1));
						}
					}
				}
			}
		}else{
			return new Tuple(0,"1");
		}if(result==null || result.length()<=0){
			return new Tuple(temp,"1");
		}else{
			return new Tuple(temp,result.substring(1));
		}
	}
}
	
	
	
/*public void expression(String str){/*转化为后缀表达式
int i=0,j=0;
char[] temp=str.toCharArray();
ch2=new char[100];
Stack<Character> stack=new Stack<Character>();
for(i=0;i<temp.length;i++){
	if((temp[i]>='0'&& temp[i]<='9')||(temp[i]>='a' && temp[i]<='z')){
		ch2[j]=temp[i];j++;
	}else{
		if(stack.empty() || compare(temp[i],stack.peek())){
			stack.push(temp[i]);
		}else{
			while((!stack.empty()) && compare(stack.peek(),temp[i])){
				ch2[j]=stack.pop();j++;
			}stack.push(temp[i]);
		}
	}
}if(!stack.empty()){
	do{
		ch2[j]=stack.pop();j++;
	}while(!stack.empty());
}

} 


public boolean compare(char op1,char op2){
if(op1=='*' && op2=='+'){
	return true;
}else{
	return false;
}
}*/