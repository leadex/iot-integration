var chartTarget,
	chartLight;

$(function () {
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    // Create the charts
    chartTarget = Highcharts.stockChart('container1', {
        chart: {
        },

        rangeSelector: {
            buttons: [{
                count: 5,
                type: 'minute',
                text: '5M'
            }, {
                count: 1,
                type: 'hour',
                text: '1H'
            }, {
                type: 'all',
                text: 'All'
            }],
            inputEnabled: false,
            selected: 0
        },

        title: {
            text: 'Target temperature'
        },

        exporting: {
            enabled: false
        },

        series: [{
            name: 'Target temperature',
            data: (function () {}())
        }]
    });
	
	chartLight = Highcharts.stockChart('container2', {
        chart: {
        },

        rangeSelector: {
            buttons: [{
                count: 5,
                type: 'minute',
                text: '5M'
            }, {
                count: 1,
                type: 'hour',
                text: '1H'
            }, {
                type: 'all',
                text: 'All'
            }],
            inputEnabled: false,
            selected: 0
        },

        title: {
            text: 'Light level'
        },

        exporting: {
            enabled: false
        },

        series: [{
            name: 'Light level',
            data: (function () {}())
        }]
    });

});

// Create a client instance
client = new Paho.MQTT.Client("localhost", Number(9001), "webview_" + parseInt(Math.random() * 1000000, 10)); 

// set callback handlers
client.onConnectionLost = onConnectionLost;
client.onMessageArrived = onMessageArrived;

// connect the client
client.connect({onSuccess:onConnect});

// called when the client connects
function onConnect() {
  // Once a connection has been made, make a subscription and send a message.
  console.log("onConnect");
  client.subscribe("leadex/sensors/webui/+");
  // message = new Paho.MQTT.Message("Hello");
  // message.destinationName = "World";
  // client.send(message);
}

// called when the client loses its connection
function onConnectionLost(responseObject) {
  if (responseObject.errorCode !== 0) {
    console.log("onConnectionLost: " + responseObject.errorMessage);
	
	client.connect({onSuccess:onConnect});
  }
}

// called when a message arrives
function onMessageArrived(message) {
  console.log("onMessageArrived: " + message.payloadString);
  
  var payload = JSON.parse(message.payloadString);
  
  var x = Date.parse(payload.timestamp);//(new Date()).getTime(), // current time
	
	// Temperature
	var y = payload.metrics.Target;
  var series = chartTarget.series[0],
    shift = series.data.length > 50;
	  
  series.addPoint([x, y], true, shift);
  
  // Light
  y = payload.metrics.Light;
  series = chartLight.series[0],
    shift = series.data.length > 50;
	  
  series.addPoint([x, y], true, shift);
}
