var canvas = document.querySelector('canvas');

canvas.width = window.innerWidth ;
canvas.height = window.innerHeight ;

var c = canvas.getContext('2d');


var fromWhiteToBlack = ["#FFFFFF","#DDDDDD","#A9A9A9",
						"#6B6B6B","#313131","#000000"];
var borderColor = "#A0A0A0";

<!-- side a  -->
var xCord = 45
for(i= 0;i<6 ;i++){
	c.fillStyle = borderColor;
	c.fillRect(xCord,20,20,20);
	c.fillStyle = fromWhiteToBlack[i];
	c.fillRect(xCord+2,22,16,16);
	xCord+= 40;
}


<!-- inner side 1  -->
xCord = 245;
yCord = 45;
for(i=0 ;i<3;i++){

	c.fillStyle = borderColor;
	c.fillRect(xCord,yCord,20,20);
	c.fillStyle = fromWhiteToBlack[i];
	c.fillRect(xCord+2,yCord+2,16,16);
	yCord+=40;
	xCord-=40;
}


<!-- side а  -->

var yCord = 45
for(i= 0 ;i < 6 ;i++){
	c.fillStyle = borderColor;
	c.fillRect(20,yCord,20,20);
	c.fillStyle = fromWhiteToBlack[i];
	c.fillRect(22,yCord+2,16,16);
	yCord+= 40;
}


<!-- inner side 2  -->
var xAndY = 245;
for(i=0 ;i<3;i++){

	c.fillStyle = borderColor;
	c.fillRect(xAndY,xAndY,20,20);
	c.fillStyle = fromWhiteToBlack[5-i];
	c.fillRect(xAndY+2,xAndY+2,16,16);
	xAndY -= 40;
}


<!-- side c  -->
xCord = 45
for(i= 0 ;i<6 ;i++){

	c.fillStyle = borderColor;
	c.fillRect(xCord,270,20,20);
	c.fillStyle = fromWhiteToBlack[i];
	c.fillRect(xCord+2,272,16,16);
	xCord+= 40;
}



<!-- inner side 3  -->
yCord = 245;
xCord = 45;
for(i=0 ;i<3;i++){

	c.fillStyle = borderColor;
	c.fillRect(xCord,yCord,20,20);
	c.fillStyle = fromWhiteToBlack[5-i];
	c.fillRect(xCord+2,yCord+2,16,16);
	yCord-=40;
	xCord+=40;
}


<!-- side d  -->
yCord = 245
for(i= 0 ;i<6 ;i++){

	c.fillStyle = borderColor;
	c.fillRect(270,yCord,20,20);
	c.fillStyle = fromWhiteToBlack[5-i];
	c.fillRect(272,yCord+2,16,16);
	yCord-= 40;
}


<!-- inner side 4  -->
var xAndY = 45;
for(i=0 ;i<3;i++){

	c.fillStyle = borderColor;
	c.fillRect(xAndY,xAndY,20,20);
	c.fillStyle = fromWhiteToBlack[i];
	c.fillRect(xAndY+2,xAndY+2,16,16);
	xAndY += 40;
}

console.log(canvas);