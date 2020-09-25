import java.util.*;
public class Countdown {
	
	public static void main(String[] args) {
		int i=0, target, targetAdd, targetSub, valueAdd, valueSub, step, score, choise, listAddValue;
		List<Integer> list = new ArrayList<>();
		Random r = new Random();
		Scanner scan = new Scanner (System.in);
		System.out.print("COUNTDOWN CALCULATOR\n\t1.Random numbers\n\t2.Enter numbers\nMake your select(1 or 2):");
		choise = scan.nextInt();
		// Gerekli ilk deger atamalari.
		score=10;
		step=1;
		valueAdd = 1;
		valueSub = 1;
		target=0;
		
		if(choise==1) {
			for (i = 0; i < 5; i++){
			list.add(1+r.nextInt(9));
			}
			list.add(((1+r.nextInt(9)))*10);
			target = (r.nextInt(899)+100);
		}
		else if(choise==2) {
			
			i=0;
			for (i = 0; i < 7; i++) {// Kullanicinin girdigi verilerin dogruluk kontrolu.
				if(i<5) {
					System.out.print("Enter " + (i+1) +". number(1-9): ");
					listAddValue = scan.nextInt();
					while(listAddValue>9 || listAddValue<1) {
						System.out.println("Warning: This number should be between 1-9");
						System.out.print("Enter " + (i+1) +". number(1-9): ");
						listAddValue = scan.nextInt();
					}
					list.add(listAddValue);
				}
				else if(i==5){
					System.out.println("Warning: This number should be smaller than 100 and should be divisible into 10");
					System.out.print("Enter " + (i+1) +". number: ");
					listAddValue = scan.nextInt();
					while(( listAddValue>100 ) || ( listAddValue < 10 ) || ( listAddValue % 10 !=0 )) {
						System.out.println("Warning: This number should be smaller than 100 and should be divisible into 10");
						System.out.print("Enter " + (i+1) +". number: ");
						listAddValue = scan.nextInt();	
					}				
					list.add(listAddValue);
				}
				else {
					System.out.println("Warning: This number going to be your target and This number should be between 100 and 999");
					System.out.print("Enter your target: ");
					listAddValue = scan.nextInt();
					while(listAddValue>1000 || listAddValue<100) {
						System.out.println("Warning: This number going to be your target and This number should be between 100 and 999");
						System.out.print("Enter your target: ");
						listAddValue = scan.nextInt();	
					}
					target = listAddValue;
				}
			}
			
		}
		// Eger girilen sayilar ile hedefe ulasilamazsa hedeften buyuk ve kucuk sayilarin kontrolu icin olusturulan degisken atamalari.
		targetAdd = target;
		targetSub = target;
	    System.out.println("List :"+list+ "\nTarget: "+target);
	    while(true) {
	    	for (Integer integer : list) {
	    		List<Integer> runList = new ArrayList<>(list);
	            runList.remove(integer);
	            Result result = getOperations(runList, integer, target); // Listedeki elemanlari ve hedefi fonksiyona gonderme
	            if (result.success) {
	            	System.out.println("Achievable Target: " + target+ "\nScore: " + (score-(step/2)) + "\nSteps:\n" + result.output);
	                return;
	            }
	        }
	    	
	    	if(step==21) { //Hedefin +10 veya -10 yakinini bulamadiysa programin sonlanmasi icin bir sart.
	    		System.out.println("We couldn't reach target.");
	    		return;
	    	}
	    	//Hedefe erisilemediyse hedefe yakin olan sayilari bulmak icin olusturulmus iki sart.
	    	if(step % 2 == 0) {
	    		targetAdd += 1;
	    		target = targetAdd;
	    		step++;
	    	}
	    	else {
	    		targetSub -= 1;
	    		target = targetSub;
	    		step++;
	    	}
	    }
		
		
	}
	public static class Result
    {
        public String output; // Islem adimlarini tutacak degisken
        public boolean success; // Islemin basarisini kontrol edecek degisken
    }
	
	public static Result getOperations(List<Integer> numbers, int midNumber, int target)
    {
        Result midResult = new Result();
        if (midNumber == target) {
            midResult.success = true;
            midResult.output = "";
            return midResult;
        }
        for (Integer number : numbers) { // Gelen sayilarla tek tek islem yapilip hedefe ulasilmaya calisiliyor.
            List<Integer> newList = new ArrayList<Integer>(numbers);
            newList.remove(number);
            if (newList.isEmpty()) {
            	if( midNumber >= number ) {
            		if (midNumber - number == target) {
                        midResult.success = true;
                        midResult.output = midNumber + "-" + number + " = " + (midNumber-number);
                        return midResult;
                    }
            	}
            	else if(number > midNumber){
            		if (number - midNumber == target) {
                        midResult.success = true;
                        midResult.output = number + "-" + midNumber + " = " + (number-midNumber);
                        return midResult;
                    }
            	}
                else if (midNumber + number == target) {
                    midResult.success = true;
                    midResult.output = midNumber + "+" + number + " = " + (midNumber+number);
                    return midResult;
                }
                else if (midNumber * number == target) {
                    midResult.success = true;
                    midResult.output = midNumber + "*" + number + " = " + (midNumber*number);
                    return midResult;
                }
                else if( midNumber % number == 0 && midNumber / number == target ) {
                        midResult.success = true;
                        midResult.output = midNumber + "/" + number + " = " + (midNumber/number);
                        return midResult;
            	}
            	else if(number % midNumber == 0 && number / midNumber == target){
                        midResult.success = true;
                        midResult.output = number + "/" + midNumber + " = " + (number/midNumber);
                        return midResult;
            	}
                midResult.success = false;
                return midResult;
            } else{
            	int a = 0;
            	if(midNumber >= number) {
            		midResult = getOperations(newList, midNumber - number, target);
            	}
            	else {
            		midResult = getOperations(newList, number - midNumber, target);
            	}
                if (midResult.success) {
                    if(midNumber>=number) {
                    	midResult.output = midNumber + "-" + number + " = " + (midNumber-number) + "\n" + midResult.output;
                    }
                    else{
                    	midResult.output = number + "-" + midNumber + " = " + (number-midNumber) + "\n" + midResult.output;
                    }
                    return midResult;
                }
                midResult = getOperations(newList, midNumber + number, target);
                if (midResult.success) {
                	midResult.output = midNumber + "+" + number + " = " + (midNumber+number) + "\n" + midResult.output;
                    return midResult;
                }
                midResult = getOperations(newList, midNumber * number, target);
                if (midResult.success) {
                	midResult.output = midNumber + "*" + number + " = " + (midNumber*number) + "\n" + midResult.output;
                    return midResult;
                }
                if(midNumber % number == 0) {
                    midResult = getOperations(newList, midNumber / number, target); 
                    a=0;
                }
                else if (number % midNumber == 0) {
                    midResult = getOperations(newList, number / midNumber, target);    
                    a=1;
                }
                if (midResult.success && a==1) {
                	midResult.output = number + "/" + midNumber + " = " + (number/midNumber) + "\n" + midResult.output;
                    return midResult;
                }
                if(midResult.success && a==0) {
                	midResult.output = midNumber + "/" + number + " = " + (midNumber/number) + "\n" + midResult.output;
                    return midResult;
                }
            }
        }
        return midResult;
    }
}
