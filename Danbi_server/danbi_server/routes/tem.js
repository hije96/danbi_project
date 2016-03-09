var express= require('express');
var router = express.Router();
var body_parser = require('body-parser');
var path= require('path');
var gpio = require('onoff').Gpio;
var logger = require('morgan');


var sensorLib = require('node-dht-sensor');
var tem;
var hum;

//온도/습도 센서 변수 설정

var sensor = { 


				sensors : [ {
							name : "indoor",
							type : 11,
							pin : 4
	

				}],

				read: function() {

							for (var a in this.sensors) {
						
								b = sensorLib.readSpec(this.sensors[a].type, this.sensors[a].pin);
								
								tem = b.temperature.toFixed(1);
								hum = b.humidity.toFixed(1);
							

							}

				}

};//센서값을 읽어들임. 핀 값 4번으로 읽어들인다.
				

router.get('/', function (req, res) 
{ //온습도 요청이 들어왔을 경우.


		sensor.read(); //센서값을 받아옴.
		console.log(tem);
		console.log(hum);
		//로그값 확인



		res.json({
			
				"tem" : tem,
				"hum" : hum
		
		})				
			
		//센서값을 리턴한다

});


module.exports = router;
