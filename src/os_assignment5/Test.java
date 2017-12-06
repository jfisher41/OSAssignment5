package os_assignment5;

public class Test {

	public static void main(String[] args) {
		DoubleLinkedList list = new DoubleLinkedList();
		PCB chart1 = new PCB();
		PCB chart2 = new PCB();
		PCB chart3 = new PCB();
		PCB chart4 = new PCB();
		PCB chart5 = new PCB();
		PCB chart6 = new PCB();
		PCB chart7 = new PCB();
		PCB chart8 = new PCB();
		PCB chart9 = new PCB();
		PCB chart10 = new PCB();
		
		PCB chart[] = {chart1, 
				chart2,
				chart3,
				chart4,
				chart5,
				chart6,
				chart7,
				chart8,
				chart9,
				chart10,};
		
		
		int i = 1;
		for(PCB item: chart){
			item.id = i;
			i++;
		}
		for(PCB item: chart){
			list.push(item);
		}
		
		list.printList("");
		
		for(int j = 0; j < 5; j++){
			list.pop();
		}
		list.printList("");
		
		list.push(chart[1]);
		list.printList("");
		
		list.push(chart[4]);
		list.printList("");
		
	}

}
