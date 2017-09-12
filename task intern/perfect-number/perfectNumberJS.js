function perfectNumber(){

	var numberToCheck = prompt("Please enter the number \
		you want to check", "");
	var temp = 0;
	var i ;
    
    numberToCheck = parseInt(numberToCheck);
	for(i = 1 ; i<= numberToCheck/2 ; i++){
		if(numberToCheck % i === 0){
			temp += i ;
		} 
	}

	if(temp === numberToCheck && temp !== 0){
		document.getElementById("result")
		.innerHTML = "The number is perfect !";
	}else{
		document.getElementById("result")
		.innerHTML = "The number is not perfect !";
	}

}
