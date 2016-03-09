var express= require('express');
var router = express.Router();
var body_parser = require('body-parser');
var path= require('path');
var gpio = require('onoff').Gpio;

var pump_control = new gpio(18,'out'); 
//펌프를 컨트롤한다. 펌프의 gpio는 18번이며, 출력을 담당한다.
//출력 value는 0또는 1이 될것이다. 
// 0 : 펌프 정지  1 : 펌프 작동

router.get('/:onoff/:minute', function (req, res,next) 
{

		var onoff = req.params.onoff;
		var minute = req.params.minute;
		
		minute = minute * 60 * 1000;
		console.log(minute);

		//들어오는 시간은 분단위로 넘어오기 때문에, 60을 곱해주어 초로 바꾸어주고, 
		//1000이 1초이므로, 1000을 곱해주어 환산해준다.
		//onoff는 들어온 minute 뒤에 자동으로 종료가 되어야한다.


		//setTimeout() 함수를 이용한다. timeout함수는 특정한 시간 후에, 한번 함수를 실행하고 종료된다.
		//setInterval()함수는 특정시간 후마다 특정 함수를 실행한다.


		if(onoff='1'){

			
			pump_control.writeSync(1); // pump on
			

			
			res.json({
				
				"result" : "1"

			}) //펌프를 작동시켰으므로, 클라이언트에게 1을 보내주어 작동시킴을 알린다.
			
			//타임아웃 함수를 사용하는 이유는, 지정시간 이후에 저절로 모터가 작동 정지되어야 하기 때문이다.
			setTimeout(  function(){
			
				pump_control.writeSync(0); //pump off	
				
			},minute);

		} //On 명령잉 들어오면, 요청 시간만큼 실행후에 종료시킨다.		
		

		//클라이언트 단에서도 타이머를 사용하며, 특정 시간이후에 혹시 모르니 한번더 오프 명령을 내려준다
		

		else next();


	}, function(req,res){

			pump_control.writeSync(0);	//pump off
		
			res.json({

				"result" : "1"
			})
		
			

		

	});
	





module.exports = router;
